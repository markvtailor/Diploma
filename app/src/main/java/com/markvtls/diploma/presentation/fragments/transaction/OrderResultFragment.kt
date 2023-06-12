package com.markvtls.diploma.presentation.fragments.transaction

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.*
import com.markvtls.diploma.R
import com.markvtls.diploma.databinding.FragmentOrderResultBinding
import com.markvtls.diploma.presentation.fragments.TicketsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class OrderResultFragment : Fragment() {

    private var _binding: FragmentOrderResultBinding? = null
    private val binding get() = _binding!!

    private val ticketsViewModel: TicketsViewModel by viewModels({requireActivity()})


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketsViewModel.saveUserTicket()

        binding.qrcode.setImageDrawable(getQrCodeBitmap())


        with(binding) {

            if (ticketsViewModel.lastTicketAdultsCount == 0) {
                adultsResult.visibility = View.GONE
                adultsSum.visibility = View.GONE
            }
            if (ticketsViewModel.lastTicketKidsCount == 0) {
                kidsResult.visibility = View.GONE
                kidsSum.visibility = View.GONE
            }

            println(!ticketsViewModel.lastTicketCar)

            if (!ticketsViewModel.lastTicketCar) {
                carsHeavyResult.visibility = View.GONE
                carsHeavySum.visibility = View.GONE
            }
            if (!ticketsViewModel.lastTicketCargoCar) {
                carsCargoResult.visibility = View.GONE
                carsCargoSum.visibility = View.GONE
            }

            ticketTo.text = ticketsViewModel.lastTicketDestination
            ticketFrom.text = ticketsViewModel.lastTicketStartPoint
            adultsResult.text = "${ticketsViewModel.lastTicketAdultsCount} x Взрослые"
            kidsResult.text = "${ticketsViewModel.lastTicketKidsCount} x Дети от 5 до 10 лет"
            if (ticketsViewModel.lastTicketHeavyCar) carsHeavyResult.text = "Легковые автомобили 3,5т+"
            adultsSum.text = "${ticketsViewModel.lastTicketAdultsCount * 120}"
            kidsSum.text = "${ticketsViewModel.lastTicketKidsCount * 60}"
            carsHeavySum.text = if (ticketsViewModel.lastTicketHeavyCar) 2500.toString() else 1250.toString()
            carsCargoSum.text = "1000"
            totalSum.text = ticketsViewModel.lastTicketSum.toString()


            val formatter =  DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            orderDate.text = "Дата покупки: ${LocalDateTime.now().format(formatter)}"

            goBackToMainButton.setOnClickListener { backToMain() }
        }
    }

    private fun backToMain() {
        with(ticketsViewModel) {
            lastTicketStartPoint = ""
            lastTicketDestination = ""
            lastTicketType = ""
            lastTicketAdultsCount = 0
            lastTicketKidsCount = 0
            lastTicketHeavyCar = false
            lastTicketCargoCar = false
            ticketsViewModel.lastTicketCar = true
            lastTicketSum = 0
        }
        val action = OrderResultFragmentDirections.actionOrderResultFragmentToMainFragment()
        findNavController().navigate(action)
    }


    private fun getQrCodeBitmap(): Drawable {
        val data = QrData.Text(
            "Куда: ${ticketsViewModel.lastTicketDestination} \n" +
                    "Откуда: ${ticketsViewModel.lastTicketStartPoint} \n" +
                    "Детей: ${ticketsViewModel.lastTicketKidsCount} \n" +
                    "Взрослых: ${ticketsViewModel.lastTicketAdultsCount} \n" +
                    "Сумма: ${ticketsViewModel.lastTicketSum}"
        )

        val options = QrVectorOptions.Builder()
            .setPadding(.3f)

            .setBackground(
                QrVectorBackground(
                    color = QrVectorColor.Solid(Color.WHITE)
                )
            )
            .setColors(
                QrVectorColors(
                    dark = QrVectorColor
                        .Solid(Color.BLACK),
                    ball = QrVectorColor.Solid(
                        ContextCompat.getColor(requireContext(), R.color.black)
                    )
                )
            )
            .setShapes(
                QrVectorShapes(
                    darkPixel = QrVectorPixelShape
                        .RoundCorners(.5f),
                    ball = QrVectorBallShape
                        .RoundCorners(.25f),
                    frame = QrVectorFrameShape
                        .RoundCorners(.25f),
                )
            )
            .build()

        return QrCodeDrawable(data, options, Charsets.UTF_8)

    }
}