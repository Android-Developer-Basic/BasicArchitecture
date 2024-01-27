package ru.otus.basicarchitecture.address.suggestions

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R

class SuggestionsItemViewHolder(
    view: View,
    private val onItemClicked: (String) -> Unit = {}
) : RecyclerView.ViewHolder(view) {

    private val textView: TextView by lazy { itemView.findViewById(R.id.value) }

    fun bind(item: SuggestionsItem) {
        textView.text = item.value
        itemView.setOnClickListener {
            onItemClicked(item.value)
        }
    }

}