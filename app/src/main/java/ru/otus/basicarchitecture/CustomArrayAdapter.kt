package ru.otus.basicarchitecture

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class CustomArrayAdapter(
    context: Context,
    private val items: List<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, items) {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()

                if (constraint.isNullOrEmpty()) {
                    results.values = items
                    results.count = items.size
                } else {
                    val searchText = constraint.toString().lowercase().cleanForSearch()
                    val matchedItems = items.filter {
                        it.lowercase().cleanForSearch().contains(searchText)
                    }
                    results.values = matchedItems
                    results.count = matchedItems.size
                }

                return results
            }

            private fun String.cleanForSearch(): String {
                return this.replace("г ", "")
                    .replace(", ", " ")
                    .replace(" пер", "")
                    .replace(" ул", "")
                    .replace(" д", "")
                    .replace(" б-р", "")
                    .replace(" р-н", "")
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return resultValue as? String ?: ""
            }
        }
    }
}
