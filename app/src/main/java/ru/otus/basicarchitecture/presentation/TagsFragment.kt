package ru.otus.basicarchitecture.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import javax.inject.Inject

@AndroidEntryPoint
class TagsFragment : Fragment() {

    @Inject
    lateinit var viewModel: TagsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tags, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tags.forEach { tag ->
            Chip(context).apply {
                text = tag
                isClickable = true
                isCheckable = true
                isChecked = viewModel.interests.value?.contains(tag) ?: false
                setOnCheckedChangeListener { _, _ ->
                    viewModel.selectInterest(tag)
                }
                view.findViewById<ChipGroup>(R.id.tags).addView(this)
            }
        }

        view.findViewById<Button>(R.id.nextToSummary).setOnClickListener {
            viewModel.saveToCash()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SummaryFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}