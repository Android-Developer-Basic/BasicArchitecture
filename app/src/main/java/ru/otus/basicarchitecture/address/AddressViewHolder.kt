package ru.otus.basicarchitecture.address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.otus.basicarchitecture.R

class AddressViewHolder(parent: ViewGroup) :
    ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.address_item, parent, false)) {
    private val textView: TextView = itemView.findViewById(R.id.address)

    fun bind(address: String, onClick: () -> Unit) {
        textView.text = address
        itemView.isClickable = true
        itemView.setOnClickListener {
            onClick()
        }
    }
}