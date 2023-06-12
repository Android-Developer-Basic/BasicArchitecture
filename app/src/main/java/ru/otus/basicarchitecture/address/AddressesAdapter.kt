package ru.otus.basicarchitecture.address

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import ru.otus.basicarchitecture.data.Address

class AddressesAdapter: Adapter<AddressViewHolder>() {
    var addresses: List<Address> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onAdressClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddressViewHolder(parent)

    override fun getItemCount() = addresses.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addresses[position].value
        holder.bind(address) {
            onAdressClick?.invoke(address)
        }
    }
}