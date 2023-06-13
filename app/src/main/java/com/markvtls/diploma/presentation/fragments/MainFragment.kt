package com.markvtls.diploma.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.markvtls.diploma.R
import com.markvtls.diploma.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {


    private val userViewModel: UserViewModel by viewModels({requireActivity()})
    private val ticketsViewModel: TicketsViewModel by viewModels({requireActivity()})

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

   // private var adapter: TicketAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*initTicketsList()

        viewLifecycleOwner.lifecycleScope.launch {
                userViewModel.userEmail.collect { email ->
                    userViewModel.userPhone.collect { phone ->
                        if (email.isNotEmpty()) ticketsViewModel.loadUserTickets(email) else if (phone.isNotEmpty()) ticketsViewModel.loadUserTickets(phone)
                    }
                }
        }

        ticketsViewModel.currentTickets.observe(viewLifecycleOwner) {
            val list = it.toMutableList()
            println(it)
            //list.addAll(ticketsViewModel.newTickets)
            adapter?.submitList(list)
        }

        binding.cardParom.setOnClickListener {
            chooseType(it.id)
        }
        binding.cardAirship.setOnClickListener {
            chooseType(it.id)
        }
        binding.cardPassengers.setOnClickListener {
            chooseType(it.id)
        }

        binding.proceedButton.setOnClickListener {
            if (binding.textTo.editText != null && binding.textFrom != null) {
                formOrder()
                if (ticketsViewModel.lastTicketType.isNotEmpty() && ticketsViewModel.lastTicketDestination.isNotEmpty() && ticketsViewModel.lastTicketStartPoint.isNotEmpty()) {
                    val action = MainFragmentDirections.actionMainFragmentToOrderFormFragment()
                    findNavController().navigate(action)
                }
            }
        }*/
    }
/*
    private fun initTicketsList() {
        val ticketsList = binding.userTicketsList
        adapter = TicketAdapter()
        ticketsList.layoutManager = LinearLayoutManager(requireContext())
        ticketsList.adapter = adapter
    }

    private fun chooseType(id: Int) {
        if (binding.cardParom.id == id) {
            binding.cardParom.strokeColor = resources.getColor(R.color.gray_blue)
            binding.cardAirship.strokeColor = resources.getColor(R.color.white)
            binding.cardPassengers.strokeColor = resources.getColor(R.color.white)
            ticketsViewModel.lastTicketType = "Паром"
        }

        if (binding.cardPassengers.id == id) {
            binding.cardParom.strokeColor = resources.getColor(R.color.white)
            binding.cardAirship.strokeColor = resources.getColor(R.color.white)
            binding.cardPassengers.strokeColor = resources.getColor(R.color.gray_blue)
            ticketsViewModel.lastTicketType = "Пассажирский"
        }

        if (binding.cardAirship.id == id) {
            binding.cardParom.strokeColor = resources.getColor(R.color.white)
            binding.cardAirship.strokeColor = resources.getColor(R.color.gray_blue)
            binding.cardPassengers.strokeColor = resources.getColor(R.color.white)
            ticketsViewModel.lastTicketType = "Подушка"
        }
    }

    private fun formOrder() {
        val startPoint =  binding.textFrom.editText?.text.toString()
        val endPoint = binding.textTo.editText?.text.toString()

        if (startPoint.isNotBlank() && startPoint.isNotEmpty()) ticketsViewModel.lastTicketStartPoint = startPoint
        if (endPoint.isNotBlank() && endPoint.isNotEmpty()) ticketsViewModel.lastTicketDestination = endPoint

    }*/
}