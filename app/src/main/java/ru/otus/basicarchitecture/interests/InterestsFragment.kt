package ru.otus.basicarchitecture.interests

import android.content.Context
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
class InterestsFragment : Fragment(R.layout.fragment_interests) {
    private val interestsViewModelInstance: InterestsFragmentModel by viewModels()
    private lateinit var nextButton: Button
    private lateinit var chipGroup: ChipGroup
    private lateinit var listOfSelectInterests: Set<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton = view.findViewById(R.id.interestsNextButton)
        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_interestsFragment_to_questionnaireFragment)
        }
        nextButton.isEnabled = false
        chipGroup = view.findViewById(R.id.chipGroup)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                interestsViewModelInstance.viewState.collect {
                    listOfSelectInterests = it.selectedInterests
                    nextButton.isEnabled = it.checkInterest

                    interestsViewModelInstance.listOfInterests.forEachIndexed { index, tagName ->
                        val isChecked = listOfSelectInterests.contains(tagName)
                        chipGroup.findViewById<Chip>(index)?.let { chip ->
                            chip.isChecked = isChecked
                        }
                    }
                }
            }
        }

        /*chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            nextButton.isEnabled = interestsViewModelInstance.checkInterest(checkedIds.size)
        }*/




        interestsViewModelInstance.listOfInterests.forEachIndexed { index, tagName ->
            chipGroup.addView(context?.let { createTagChip(it, tagName, index) })
        }
    }

    private fun createTagChip(context: Context, chipName: String, index: Int): Chip {
        return Chip(context).apply {
            text = chipName
            id = index
            isCheckable = true
            isChecked = false
            setOnClickListener {
                // Your code here
                if (isChecked) {
                    // Chip is checked
                    // Remove chipName from listOfSelectInterests
                    interestsViewModelInstance.setInterest(chipName)
                } else {
                    // Chip is not checked
                    // Add chipName to listOfSelectInterests
                    interestsViewModelInstance.removeInterest(chipName)
                }
            }
        }

    }
}
