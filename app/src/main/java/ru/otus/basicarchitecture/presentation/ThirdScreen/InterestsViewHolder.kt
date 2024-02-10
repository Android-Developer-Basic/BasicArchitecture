package ru.otus.basicarchitecture.presentation.ThirdScreen

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R

class InterestsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.findViewById<TextView>(R.id.textInterests)
}