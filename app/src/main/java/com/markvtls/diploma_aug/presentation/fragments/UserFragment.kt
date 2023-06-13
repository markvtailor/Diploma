package com.markvtls.diploma_aug.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.markvtls.diploma_aug.R
import com.markvtls.diploma_aug.databinding.FragmentUserBinding
import com.markvtls.diploma_aug.domain.model.ImageModel
import com.markvtls.diploma_aug.presentation.adapters.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val imagesViewModel: ImagesViewModel by viewModels({requireActivity()})
    private val userViewModel: UserViewModel by viewModels({requireActivity()})

    private var adapter: ImagesAdapter? = null

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

        loadNewImages()

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if ((binding.buttonCurrent as MaterialButton).isChecked) {
                loadNewImages()
            } else {
                loadDeliveredImages()
            }

        }



        binding.logout.setOnClickListener {
            userViewModel._isLogged.value = false
            findNavController().navigate(R.id.action_global_mainFragment)
        }
    }


    private fun initTicketsList() {
        val ticketsList = binding.userTicketsList
        adapter = ImagesAdapter(requireContext(), ::toOrderForm,false)
        ticketsList.layoutManager = LinearLayoutManager(requireContext())
        ticketsList.adapter = adapter
    }

    private fun loadNewImages() {
        imagesViewModel.currentImages.observe(viewLifecycleOwner) {
            val new = mutableListOf<ImageModel>()

            it.forEach {
                if (!it.isDelivered) {
                    new.add(it)
                }
            }
            adapter?.submitList(new)
        }
    }

    private fun loadDeliveredImages() {
        imagesViewModel.currentImages.observe(viewLifecycleOwner) {
            val delivered = mutableListOf<ImageModel>()

            it.forEach {
                if (it.isDelivered) {
                    delivered.add(it)
                }
            }
            adapter?.submitList(delivered)
        }
    }

    private fun toOrderForm(authorName: String, imageName: String, imagePrice: String, imageUri: String) {
        imagesViewModel.lastImageAuthor = authorName
        imagesViewModel.lastImageName = imageName
        imagesViewModel.lastImagePrice = imagePrice.toInt()
        imagesViewModel.lastImageUri = imageUri


    }
}