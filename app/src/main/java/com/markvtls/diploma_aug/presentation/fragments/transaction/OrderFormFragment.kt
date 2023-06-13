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
import com.google.android.material.button.MaterialButton
import com.markvtls.diploma_aug.R
import com.markvtls.diploma_aug.databinding.FragmentOrderFormBinding
import com.markvtls.diploma_aug.presentation.PaymentType
import com.markvtls.diploma_aug.presentation.fragments.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFormFragment : Fragment() {

    private var _binding: FragmentOrderFormBinding? = null
    private val binding get() = _binding!!
    private val imagesViewModel: ImagesViewModel by viewModels({requireActivity()})


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentOrderFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {


            binding.imageAuthor.text = imagesViewModel.lastImageAuthor
            binding.imageName.text = imagesViewModel.lastImageName

            Glide.with(requireContext())
                .load(imagesViewModel.lastImageUri.toUri())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.image)

            binding.previewButton.setOnClickListener {
                val action = OrderFormFragmentDirections.actionOrderFormFragmentToRealityFragment()
                findNavController().navigate(action)
            }

            binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->

                if ((binding.buttonSmall as MaterialButton).isChecked) {
                    imagesViewModel.lastImageSize = "Small"
                } else if ((binding.buttonAverage as MaterialButton).isChecked) {
                    imagesViewModel.lastImageSize = "Average"
                } else if ((binding.buttonLarge as MaterialButton).isChecked) {
                    imagesViewModel.lastImageSize = "Large"
                }
            }

            binding.orderSum.text = imagesViewModel.lastImagePrice.toString()

            paymentType.setOnClickListener {
                val modalBottomSheet = PaymentDialogFragment()
                modalBottomSheet.show(childFragmentManager, PaymentDialogFragment.TAG)
            }

            imagesViewModel.lastImagePaymentType.observe(viewLifecycleOwner) {
                when(it) {
                    PaymentType.MASTERCARD -> {
                        paymentIcon.setImageDrawable(resources.getDrawable(R.drawable.mastercard))
                        paymentInfo.text = "MasterCard * 1234"
                    }
                    PaymentType.VISA -> {
                        paymentIcon.setImageDrawable(resources.getDrawable(R.drawable.visa))
                        paymentInfo.text = "Visa * 4321"
                    }
                    PaymentType.GPAY -> {
                        paymentIcon.setImageDrawable(resources.getDrawable(R.drawable.gpay))
                        paymentInfo.text = "Google Pay"
                    }
                    PaymentType.NEW -> {
                        paymentIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_card))
                        paymentInfo.text = "Новая карта"
                    }
                }
            }

            backButton.setOnClickListener { resetOrder() }
            confirmationButton.setOnClickListener { confirmOrder() }

        }
    }




    private fun confirmOrder() {
            val action = OrderFormFragmentDirections.actionOrderFormFragmentToOrderResultFragment()
            findNavController().navigate(action)
    }

    private fun resetOrder() {
        with(imagesViewModel) {
            lastImageName  = ""
            lastImageAuthor = ""
            lastImageSize  = "Small"
            lastImageUri = ""
            lastImagePrice = 0
        }
        findNavController().navigate(R.id.action_global_mainFragment)
    }

}