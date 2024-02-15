package ru.otus.basicarchitecture.presentation.ThirdScreen

import androidx.recyclerview.widget.DiffUtil

class InterestsDiffUtil(
    private val oldList: List<ModelInterestsForView>,
    private val newList: List<ModelInterestsForView>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}