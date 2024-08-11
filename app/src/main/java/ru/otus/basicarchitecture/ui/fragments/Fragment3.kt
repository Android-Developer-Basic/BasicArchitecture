package ru.otus.basicarchitecture.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayout
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.model.Tag
import ru.otus.basicarchitecture.viewmodels.Fragment3ViewModel

class Fragment3 : Fragment() {
    private lateinit var viewModel: Fragment3ViewModel
    private lateinit var tagFlexboxLayout: FlexboxLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(Fragment3ViewModel::class.java)
        tagFlexboxLayout = view.findViewById(R.id.tagFlexboxLayout)

        viewModel.tags.observe(viewLifecycleOwner) { tags ->
            setupTagCloud(tags)
        }

        viewModel.selectedTags.observe(viewLifecycleOwner) { selectedTags ->
            updateSelectedTags(selectedTags)
        }
    }

    private fun setupTagCloud(tags: List<Tag>) {
        tagFlexboxLayout.removeAllViews()
        for (tag in tags) {
            val tagButton = Button(context).apply {
                text = tag.name
                setOnClickListener {
                    viewModel.toggleTagSelection(tag)
                }
            }
            tagFlexboxLayout.addView(tagButton)
        }
    }

    private fun updateSelectedTags(selectedTags: Set<Tag>) {
        for (i in 0 until tagFlexboxLayout.childCount) {
            val button = tagFlexboxLayout.getChildAt(i) as Button
            button.isSelected = selectedTags.any { it.name == button.text }
        }
    }
}