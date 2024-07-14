package ru.otus.basicarchitecture.Ui.Fragment4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.Fragment3.InterestsViewHolder


class Fragment4Adapter : RecyclerView.Adapter<InterestsViewHolder>() {

    var listInterestsInfo: List<String> = listOf()
        set(value)  {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewHolder {
        val layout = R.layout.interests_disabled
        val view  = LayoutInflater.from(parent.context).inflate(layout, parent,false)
        return InterestsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listInterestsInfo.size
    }

    override fun onBindViewHolder(holder: InterestsViewHolder, position: Int) {
        val item = listInterestsInfo[position]
        holder.textView.text = item
    }
}