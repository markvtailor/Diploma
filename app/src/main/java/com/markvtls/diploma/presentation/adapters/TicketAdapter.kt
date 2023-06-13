package com.markvtls.diploma.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.diploma.domain.model.Ticket
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
class TicketAdapter : ListAdapter<Ticket, TicketAdapter.TicketViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketViewHolder {
        return TicketViewHolder(
            ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TicketViewHolder(
        private val binding: ItemTicketBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: Ticket) {
            with(binding) {
                ticketFrom.text = ticket.locationFrom
                ticketTo.text = ticket.locationTo
                docksFrom.text = ticket.locationFromSecondary
                docksTo.text = ticket.locationToSecondary

                val formatter =  DateTimeFormatter.ofPattern("dd.MM.yyyy")
                validUntil.text = "Истекает ${ticket.expireDate.format(formatter)}"
                price.text = "${ticket.sum} P"
            }
        }
    }


    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<Ticket>() {
            override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem == newItem
            }

        }
    }
}*/
