package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class StepThreeFragment : Fragment(R.layout.fragment_step_three) {
    private val viewModel: StepThreeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroupInterests)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        viewModel.allInterests.forEach { interest ->
            val chip = Chip(requireContext()).apply {
                text = interest
                isCheckable = true

                setOnCheckedChangeListener { _, isChecked ->
                    viewModel.onInterestChanged(interest, isChecked)
                }
            }
            chipGroup.addView(chip)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isNextButtonEnabled.collect { isEnabled ->
                    btnNext.isEnabled = isEnabled
                }
            }
        }

        btnNext.setOnClickListener {
            viewModel.onNextClicked()
            findNavController().navigate(R.id.action_stepThree_to_stepFour)
        }
    }
}