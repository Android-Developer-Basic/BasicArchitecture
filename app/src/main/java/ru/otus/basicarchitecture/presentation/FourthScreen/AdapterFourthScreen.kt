package ru.otus.basicarchitecture.presentation.FourthScreen


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.ThirdScreen.InterestsViewHolder

class AdapterFourthScreen : RecyclerView.Adapter<InterestsViewHolder>() {

    var listInterestsInfo: List<String> = listOf()
        set(value)  {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewHolder {
        val layout = R.layout.interests_forth_screen
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