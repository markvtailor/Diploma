package com.markvtls.diploma_aug.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.markvtls.diploma_aug.databinding.FragmentMainBinding
import com.markvtls.diploma_aug.presentation.adapters.ImagesAdapter
import com.markvtls.diploma_aug.presentation.testList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {


    private val userViewModel: UserViewModel by viewModels({requireActivity()})
    private val imagesViewModel: ImagesViewModel by viewModels({requireActivity()})

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var adapter: ImagesAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTicketsList()

        viewLifecycleOwner.lifecycleScope.launch {
                userViewModel.userEmail.collect { email ->
                    userViewModel.userPhone.collect { phone ->
                        if (email.isNotEmpty()) imagesViewModel.loadUserImages(email) else if (phone.isNotEmpty()) imagesViewModel.loadUserImages(phone)
                    }
                }
        }

        adapter?.submitList(testList)

       /* imagesViewModel.currentImages.observe(viewLifecycleOwner) {
            val list = it.toMutableList()
            println(it)
            adapter?.submitList(list)
        }*/




    }

    private fun initTicketsList() {
        val ticketsList = binding.imagesList
        adapter = ImagesAdapter(requireContext(), ::toOrderForm,true)
        ticketsList.layoutManager = LinearLayoutManager(requireContext())
        ticketsList.adapter = adapter
    }



    private fun toOrderForm(authorName: String, imageName: String, imagePrice: String, imageUri: String) {
        imagesViewModel.lastImageAuthor = authorName
        imagesViewModel.lastImageName = imageName
        imagesViewModel.lastImagePrice = imagePrice.toInt()
        imagesViewModel.lastImageUri = imageUri

        val action = MainFragmentDirections.actionMainFragmentToOrderFormFragment()
        findNavController().navigate(action)


    }




}