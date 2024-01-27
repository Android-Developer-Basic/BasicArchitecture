package ru.otus.basicarchitecture.address.suggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.otus.basicarchitecture.R

class SuggestionsAdapter(
    private val onItemClicked: (String) -> Unit
) : ListAdapter<SuggestionsItem, SuggestionsItemViewHolder>(SuggestionsItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsItemViewHolder =
        SuggestionsItemViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.suggestion_item, parent, false),
            onItemClicked = onItemClicked)

    override fun onBindViewHolder(holder: SuggestionsItemViewHolder, position: Int) =
        holder.bind(getItem(position))


}