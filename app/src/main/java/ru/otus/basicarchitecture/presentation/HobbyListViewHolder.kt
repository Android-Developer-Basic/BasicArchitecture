package ru.otus.basicarchitecture.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.domain.models.HobbyItem

class HobbyListViewHolder(
    itemView: View
) :RecyclerView.ViewHolder(itemView) {

    fun bind(item: HobbyItem){

        itemView.findViewById<TextView>(R.id.hobbyItemTextView).text = item.hobby
    }

}