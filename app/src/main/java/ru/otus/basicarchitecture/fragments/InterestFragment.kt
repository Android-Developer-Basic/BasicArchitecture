package ru.otus.basicarchitecture.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodel.InterestViewModel
import javax.inject.Inject

@AndroidEntryPoint
class InterestFragment @Inject constructor(): Fragment() {

    companion object {
        fun newInstance() = InterestFragment()
    }

    private val viewModel: InterestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_interest, container, false)
    }

    private lateinit var tagFlexboxLayout: FlexboxLayout
    private lateinit var nextBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tagFlexboxLayout = view.findViewById(R.id.tagFlexboxLayout)
        nextBtn = view.findViewById(R.id.button_to_summary)

        viewModel.selectedInterests.observe(viewLifecycleOwner) { selectedInterests ->
            updateTags(selectedInterests)
        }

        nextBtn.setOnClickListener {
            viewModel.saveSelectedInterests()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, SummaryFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        viewModel.interests.forEach { interest ->
            val tagView = createTagView(interest)
            tagView.setOnClickListener {
                viewModel.toggleInterest(interest)
            }
            tagFlexboxLayout.addView(tagView)
        }
    }

    private fun createTagView(interest: String): TextView {
        val tagView = LayoutInflater.from(context).inflate(R.layout.tag, tagFlexboxLayout, false) as TextView
        tagView.text = interest
        return tagView
    }

    private fun updateTags(selectedInterests: List<String>) {
        for (i in 0 until tagFlexboxLayout.childCount) {
            val tagView = tagFlexboxLayout.getChildAt(i) as TextView
            val interest = tagView.text.toString()
            tagView.isSelected = selectedInterests.contains(interest)
        }
    }
}