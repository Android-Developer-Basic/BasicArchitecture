package ru.otus.basicarchitecture.interests

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.results.ResultsFragment
import ru.otus.basicarchitecture.widget.TagsView
import javax.inject.Inject

@AndroidEntryPoint
class InterestsFragment : Fragment(R.layout.fragment_interests) {
    @Inject
    lateinit var viewModel: InterestsViewModel

    private lateinit var tagsView: TagsView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tagsView = view.findViewById(R.id.tags)
        tagsView.onTagClick = { tag ->
            viewModel.addOrRemoveInterest(tag)
        }
        view.findViewById<Button>(R.id.next).setOnClickListener {
            viewModel.save()
            parentFragmentManager.beginTransaction()
                .replace(R.id.content, ResultsFragment::class.java, null)
                .commit()
        }

        viewModel.interests.observe(viewLifecycleOwner) { interests ->
            tagsView.tags = interests
        }
        viewModel.selectedInterests.observe(viewLifecycleOwner) { interests ->
            tagsView.selectedTags = interests
        }
    }
}