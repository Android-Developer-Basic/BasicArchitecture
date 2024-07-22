package ru.otus.basicarchitecture.Ui.Fragment2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.Core.Model.DTO.Suggestion
import ru.otus.basicarchitecture.databinding.AddressLayoutBinding


interface OnItemClickListener {
    fun onItemClick(item: Suggestion?)
}

class Fragment2Adapter(private val listener: OnItemClickListener): RecyclerView.Adapter<Fragment2Adapter.AddressItemViewHolder>() {
    private var items: MutableList<Suggestion> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AddressLayoutBinding.inflate(inflater, parent, false)
        return AddressItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(list: List<Suggestion>) {
        items.clear()
        addList(list)
    }

    fun addList(list: List<Suggestion>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class AddressItemViewHolder(private val binding: AddressLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Suggestion) {
            binding.name.text = item.value
        }
    }

}