package ru.otus.basicarchitecture.Ui.Fragment3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.Fragment3.InterestsDiffUtil
import ru.otus.basicarchitecture.presentation.Fragment3.InterestsViewHolder
import ru.otus.basicarchitecture.presentation.Fragment3.ModelInterestsForView

class InterestsAdapter: RecyclerView.Adapter<InterestsViewHolder>() {

    var onClickListener: ((ModelInterestsForView) -> Unit)? = null

    var interestsList = listOf<ModelInterestsForView>()
        set(value){
            val callback = InterestsDiffUtil(interestsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewHolder {

        val layout = when (viewType) {
            DISABLED_VIEW -> R.layout.interests_disabled
            ENABLED_VIEW -> R.layout.interests_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return InterestsViewHolder(view)
    }

    override fun onBindViewHolder(holder: InterestsViewHolder, position: Int) {
        val item = interestsList[position]
        holder.view.setOnClickListener {
            onClickListener?.invoke(item)
        }
        holder.textView.text = item.interests
    }
    override fun getItemViewType(position: Int): Int {
        val item = interestsList[position]
        return if (item.enabled) ENABLED_VIEW else DISABLED_VIEW
    }

    override fun getItemCount(): Int {
        return interestsList.size
    }

    companion object {
        val ENABLED_VIEW = 100
        val DISABLED_VIEW = 101
    }

}