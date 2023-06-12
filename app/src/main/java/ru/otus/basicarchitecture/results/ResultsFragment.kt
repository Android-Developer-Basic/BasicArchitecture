package ru.otus.basicarchitecture.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.widget.TagsView
import javax.inject.Inject

@AndroidEntryPoint
class ResultsFragment : Fragment(R.layout.fragment_results) {
    @Inject
    lateinit var viewModel: ResultsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.results.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.name).text = it.name
            view.findViewById<TextView>(R.id.surname).text = it.surname
            view.findViewById<TextView>(R.id.birthdate).text = it.birthdate
            view.findViewById<TextView>(R.id.address).text = it.address
            view.findViewById<TagsView>(R.id.interests).tags = it.interests
        }
    }
}