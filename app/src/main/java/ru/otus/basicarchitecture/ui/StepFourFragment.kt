package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class StepFourFragment : Fragment(R.layout.fragment_step_four) {

    private val viewModel: StepFourViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvName).text = viewModel.name
        view.findViewById<TextView>(R.id.tvSurname).text = viewModel.surname
        view.findViewById<TextView>(R.id.tvBirthDate).text = viewModel.birthDate
        view.findViewById<TextView>(R.id.tvAddress).text = viewModel.fullAddress

        val chipGroup = view.findViewById<ChipGroup>(R.id.cgInterests)
        viewModel.interests.forEach { interest ->
            val chip = Chip(requireContext()).apply {
                text = interest
                isCheckable = false
                setChipBackgroundColorResource(android.R.color.transparent)
                setChipStrokeColorResource(android.R.color.darker_gray)
                chipStrokeWidth = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics
                )
            }
            chipGroup.addView(chip)
        }
    }
}