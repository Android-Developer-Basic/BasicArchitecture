package ru.otus.basicarchitecture.address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.data.Address
import ru.otus.basicarchitecture.R

class AddressAdapter(private val onClick: (String) -> Unit) : ListAdapter<Address, AddressAdapter.Holder>(AddressDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.vh_address, parent, false)
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

        fun bind(address: Address) {
            this.address = address.address
            (itemView as TextView).text = address.address
        }
    }
}

object AddressDiffCallback : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }
}