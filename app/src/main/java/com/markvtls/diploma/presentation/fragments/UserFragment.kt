package com.markvtls.diploma.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.markvtls.diploma.R
import com.markvtls.diploma.databinding.FragmentUserBinding
import com.markvtls.diploma.domain.model.Ticket
import com.markvtls.diploma.presentation.adapters.TicketAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val ticketsViewModel: TicketsViewModel by viewModels({requireActivity()})
    private val userViewModel: UserViewModel by viewModels({requireActivity()})

    private var adapter: TicketAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTicketsList()

        val user = Firebase.auth.currentUser


        userViewModel.isLogged.observe(viewLifecycleOwner) { isLogged ->
            if (isLogged) {
                if (!user?.phoneNumber.isNullOrEmpty()) binding.header.text = user?.phoneNumber
                if (!user?.email.isNullOrEmpty()) binding.header.text = user?.email
            }
        }

        println(user?.phoneNumber.isNullOrEmpty())
        if (!user?.phoneNumber.isNullOrEmpty()) binding.header.text = user?.phoneNumber else if (!user?.email.isNullOrEmpty()) binding.header.text = user?.email

        loadActiveTickets()

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if ((binding.buttonCurrent as MaterialButton).isChecked) {
                loadActiveTickets()
            } else {
                loadOldTickets()
            }

        }



        binding.logout.setOnClickListener {
            userViewModel._isLogged.value = false
            findNavController().navigate(R.id.action_global_mainFragment)
        }
    }


    private fun initTicketsList() {
        val ticketsList = binding.userTicketsList
        adapter = TicketAdapter()
        ticketsList.layoutManager = LinearLayoutManager(requireContext())
        ticketsList.adapter = adapter
    }

    private fun loadActiveTickets() {
        ticketsViewModel.currentTickets.observe(viewLifecycleOwner) {
            val activeTickets = mutableListOf<Ticket>()

            it.forEach {
                if (LocalDate.now().isBefore(it.expireDate)) {
                    activeTickets.add(it)
                }
            }
            adapter?.submitList(activeTickets)
        }
    }

    private fun loadOldTickets() {
        ticketsViewModel.currentTickets.observe(viewLifecycleOwner) {
            val oldTickets = mutableListOf<Ticket>()

            it.forEach {
                if (LocalDate.now().isAfter(it.expireDate)) {
                    oldTickets.add(it)
                }
            }
            adapter?.submitList(oldTickets)
        }
    }
}