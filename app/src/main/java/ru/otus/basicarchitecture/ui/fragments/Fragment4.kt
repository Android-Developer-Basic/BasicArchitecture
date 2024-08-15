package ru.otus.basicarchitecture.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.model.Tag
import ru.otus.basicarchitecture.viewmodels.Fragment3ViewModel

@AndroidEntryPoint
class Fragment4 : Fragment() {
    private val viewModel: Fragment3ViewModel by viewModels()

    private lateinit var tagFlexboxLayout: FlexboxLayout
    private lateinit var nextBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tagFlexboxLayout = view.findViewById(R.id.tagFlexboxLayout)
        nextBtn = view.findViewById(R.id.fragment3Btn)

        viewModel.selectedInterests.observe(viewLifecycleOwner) { selectedInterests ->
            updateTags(selectedInterests)
        }

        nextBtn.setOnClickListener {
            viewModel.saveSelectedInterests()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Fragment4())
                .addToBackStack(null)
                .commit()
        }

        // Добавить теги в FlexboxLayout
        viewModel.interests.forEach { interest ->
            val tagView = createTagView(interest)
            tagView.setOnClickListener {
                viewModel.toggleInterest(interest)
            }
            tagFlexboxLayout.addView(tagView)
        }
    }

    private fun createTagView(interest: String): TextView {
        val tagView = LayoutInflater.from(context).inflate(R.layout.tag_item, tagFlexboxLayout, false) as TextView
        tagView.text = interest
        return tagView
    }

    private fun updateTags(selectedInterests: Set<String>) {
        for (i in 0 until tagFlexboxLayout.childCount) {
            val tagView = tagFlexboxLayout.getChildAt(i) as TextView
            val interest = tagView.text.toString()
            tagView.isSelected = selectedInterests.contains(interest)
        }
    }
}