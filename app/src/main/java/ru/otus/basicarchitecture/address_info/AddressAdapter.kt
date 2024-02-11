package ru.otus.basicarchitecture.address_info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.services.Suggestion

class AddressAdapter(private val onClick: (String) -> Unit) : ListAdapter<Suggestion, AddressAdapter.Holder>(AddressDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_address, parent, false)
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var address: String = ""

        init {
            itemView.setOnClickListener {
                onClick(address)
            }
        }

        fun bind(suggestion: Suggestion) {
            this.address = suggestion.value.toString()
            (itemView as TextView).text = suggestion.value
        }
    }
}

object AddressDiffCallback : DiffUtil.ItemCallback<Suggestion>() {
    override fun areItemsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
        return oldItem == newItem
    }
}