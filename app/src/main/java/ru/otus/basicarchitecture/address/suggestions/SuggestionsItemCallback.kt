package ru.otus.basicarchitecture.address.suggestions

import androidx.recyclerview.widget.DiffUtil

class SuggestionsItemCallback : DiffUtil.ItemCallback<SuggestionsItem>() {

    override fun areItemsTheSame(p0: SuggestionsItem, p1: SuggestionsItem): Boolean = false

    override fun areContentsTheSame(p0: SuggestionsItem, p1: SuggestionsItem): Boolean = false
}