package com.markvtls.diploma_aug.presentation.fragments.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.markvtls.diploma_aug.databinding.FragmentOrderResultBinding
import com.markvtls.diploma_aug.presentation.fragments.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderResultFragment : Fragment() {

    private var _binding: FragmentOrderResultBinding? = null
    private val binding get() = _binding!!

    private val imagesViewModel: ImagesViewModel by viewModels({requireActivity()})


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagesViewModel.saveUserOrder()



        with(binding) {

            Glide.with(requireContext())
                .load(imagesViewModel.lastImageUri.toUri())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(image)

            price.text = imagesViewModel.lastImagePrice.toString()
            imageNameSize.text = "${imagesViewModel.lastImageName} x ${imagesViewModel.lastImageSize}"

            goBackToMainButton.setOnClickListener { backToMain() }
        }
    }

    private fun backToMain() {
        with(imagesViewModel) {
            lastImageName  = ""
            lastImageAuthor = ""
            lastImageSize  = "Small"
            lastImageUri = ""
            lastImagePrice = 0
        }
        val action = OrderResultFragmentDirections.actionOrderResultFragmentToMainFragment()
        findNavController().navigate(action)
    }


}