package com.markvtls.diploma.presentation.fragments.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.markvtls.diploma.R
import com.markvtls.diploma.databinding.FragmentOrderFormBinding
import com.markvtls.diploma.presentation.PaymentType
import com.markvtls.diploma.presentation.fragments.TicketsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFormFragment : Fragment() {

    private var _binding: FragmentOrderFormBinding? = null
    private val binding get() = _binding!!
    private val ticketsViewModel: TicketsViewModel by viewModels({requireActivity()})


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentOrderFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textFrom.editText?.setText(ticketsViewModel.lastTicketStartPoint)
        binding.textTo.editText?.setText(ticketsViewModel.lastTicketDestination)

        with(binding) {

            orderSum.text = countTotalSum().toString()

            addAdult.setOnClickListener {
                if (passengersSwitch.isChecked) increaseAdults()
            }

            removeAdult.setOnClickListener {
                if (passengersSwitch.isChecked) decreaseAdults()
            }

            addKid.setOnClickListener {
                if (passengersSwitch.isChecked) increaseKids()
            }

            removeKid.setOnClickListener {
                if (passengersSwitch.isChecked) decreaseKids()
            }

            carHeavyCheckbox.setOnCheckedChangeListener { compoundButton, b ->
                if (carsSwitch.isChecked) {
                    ticketsViewModel.lastTicketHeavyCar = carHeavyCheckbox.isChecked
                    binding.orderSum.text = countTotalSum().toString()
                }
            }

            carCargoCheckbox.setOnCheckedChangeListener { compoundButton, b ->
                if (carsSwitch.isChecked) {
                    ticketsViewModel.lastTicketCargoCar = carCargoCheckbox.isChecked
                    binding.orderSum.text = countTotalSum().toString()
                }

            }

            carsSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    carCargoCheckbox.isClickable = true
                    carHeavyCheckbox.isClickable = true
                    ticketsViewModel.lastTicketCar = true
                    binding.orderSum.text = countTotalSum().toString()
                } else {
                    carCargoCheckbox.isClickable = false
                    carHeavyCheckbox.isClickable = false
                    carHeavyCheckbox.isChecked = false
                    carCargoCheckbox.isChecked = false
                    ticketsViewModel.lastTicketCargoCar = false
                    ticketsViewModel.lastTicketHeavyCar = false
                    ticketsViewModel.lastTicketCar = false
                    binding.orderSum.text = countTotalSum().toString()
                }
            }

            passengersSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    addKid.isClickable = true
                    addAdult.isClickable =  true
                    removeKid.isClickable = true
                    removeAdult.isClickable = true
                    binding.orderSum.text = countTotalSum().toString()
                } else {
                    addKid.isClickable = false
                    addAdult.isClickable =  false
                    removeKid.isClickable = false
                    removeAdult.isClickable = false
                    adultCount.text = "0"
                    kidCount.text = "0"
                    ticketsViewModel.lastTicketKidsCount = 0
                    ticketsViewModel.lastTicketAdultsCount = 0
                    binding.orderSum.text = countTotalSum().toString()
                }
            }

            paymentType.setOnClickListener {
                val modalBottomSheet = PaymentDialogFragment()
                modalBottomSheet.show(childFragmentManager, PaymentDialogFragment.TAG)
            }

            ticketsViewModel.lastTicketPaymentType.observe(viewLifecycleOwner) {
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


    private fun decreaseAdults() {
        if (ticketsViewModel.lastTicketAdultsCount > 0) {
            ticketsViewModel.lastTicketAdultsCount = ticketsViewModel.lastTicketAdultsCount.dec()
            binding.adultCount.text = ticketsViewModel.lastTicketAdultsCount.toString()
            binding.orderSum.text = countTotalSum().toString()
        }
    }

    private fun increaseAdults() {
        ticketsViewModel.lastTicketAdultsCount = ticketsViewModel.lastTicketAdultsCount.inc()
        binding.adultCount.text = ticketsViewModel.lastTicketAdultsCount.toString()
        binding.orderSum.text = countTotalSum().toString()
    }

    private fun decreaseKids() {
        if (ticketsViewModel.lastTicketKidsCount > 0) {
            ticketsViewModel.lastTicketKidsCount = ticketsViewModel.lastTicketKidsCount.dec()
            binding.kidCount.text = ticketsViewModel.lastTicketKidsCount.toString()
            binding.orderSum.text = countTotalSum().toString()
        }
    }

    private fun increaseKids() {
        ticketsViewModel.lastTicketKidsCount = ticketsViewModel.lastTicketKidsCount.inc()
        binding.kidCount.text = ticketsViewModel.lastTicketKidsCount.toString()
        binding.orderSum.text = countTotalSum().toString()

    }


    private fun countTotalSum(): Int {
        var kidsPrice = ticketsViewModel.lastTicketKidsCount * 60
        var adultsPrice = ticketsViewModel.lastTicketAdultsCount * 120

        var carHeavyPrice =
            if (binding.carsSwitch.isChecked && !binding.carHeavyCheckbox.isChecked) {
                1250
            } else if (binding.carsSwitch.isChecked && binding.carHeavyCheckbox.isChecked) {
                2500
            } else 0

        var carCargoPrice = if (binding.carCargoCheckbox.isChecked) 1000 else 0

        val totalSum = kidsPrice + adultsPrice + carHeavyPrice + carCargoPrice

        return totalSum

    }

    private fun confirmOrder() {
        if (countTotalSum() > 0) {
            ticketsViewModel.lastTicketSum = countTotalSum()
            val action = OrderFormFragmentDirections.actionOrderFormFragmentToOrderResultFragment()
            findNavController().navigate(action)
        }
    }

    private fun resetOrder() {
        with(ticketsViewModel) {
            lastTicketStartPoint = ""
            lastTicketDestination = ""
            lastTicketType = ""
            lastTicketAdultsCount = 0
            lastTicketKidsCount = 0
            lastTicketHeavyCar = false
            lastTicketCargoCar = false
            ticketsViewModel.lastTicketCar = false
            lastTicketSum = 0
        }
        findNavController().navigate(R.id.action_global_mainFragment)
    }

}