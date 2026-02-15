package ru.otus.basicarchitecture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class InterestsFragment : Fragment() {

    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.fragment_interests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group)

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.updateInterestsList(checkedIds)
        }

        val nextBtn = view.findViewById<Button>(R.id.next_btn)

        nextBtn.setOnClickListener {
            viewModel.updateUserInterests()

            parentFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ResultFragment()
            )
                .addToBackStack("")
                .commit()
        }
    }
}