package com.markvtls.diploma_aug.presentation.fragments.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.markvtls.diploma_aug.databinding.FragmentPaymentDialogBinding
import com.markvtls.diploma_aug.presentation.PaymentType
import com.markvtls.diploma_aug.presentation.fragments.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPaymentDialogBinding? =  null
    private val binding get() = _binding!!

    private val imagesViewModel: ImagesViewModel by viewModels({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mastercardCheck.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    imagesViewModel.lastImagePaymentType.value = PaymentType.MASTERCARD
                    visaCheck.isChecked = false
                    gpayCheck.isChecked = false
                    cardCheck.isChecked = false
                }
            }

            visaCheck.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    imagesViewModel.lastImagePaymentType.value = PaymentType.VISA
                    mastercardCheck.isChecked = false
                    gpayCheck.isChecked = false
                    cardCheck.isChecked = false
                }
            }

            gpayCheck.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    imagesViewModel.lastImagePaymentType.value = PaymentType.GPAY
                    mastercardCheck.isChecked = false
                    visaCheck.isChecked = false
                    cardCheck.isChecked = false
                }
            }

            cardCheck.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (isChecked) {
                        imagesViewModel.lastImagePaymentType.value = PaymentType.NEW
                        mastercardCheck.isChecked = false
                        visaCheck.isChecked = false
                        gpayCheck.isChecked = false
                    }
                }


        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}