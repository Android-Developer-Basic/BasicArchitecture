package ru.otus.basicarchitecture.interests

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
@AndroidEntryPoint
class InterestsFragment : Fragment(R.layout.fragment_interests) {
    private val interestsViewModelInstance : InterestsFragmentModel by viewModels()
    private lateinit var nextButton: Button
    private lateinit var chipGroup: ChipGroup
    private lateinit var listOfInterests : List<String?>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton = view.findViewById(R.id.interestsNextButton)
        nextButton.setOnClickListener { goToNextScreen() }
        nextButton.isEnabled = false
        chipGroup = view.findViewById(R.id.chipGroup)

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            nextButton.isEnabled =  interestsViewModelInstance.checkInterest(checkedIds.size)
        }

        listOfInterests = interestsViewModelInstance.listOfInterests
        listOfInterests.forEachIndexed { index, tagName ->
            chipGroup.addView(context?.let { createTagChip(it, tagName?:"", index) })
        }
    }

    private fun createTagChip(context: Context, chipName: String, index: Int): Chip {
        return Chip(context).apply {
            text = chipName
            id = index
            isCheckable = true
        }

    }

    private fun goToNextScreen() {
        interestsViewModelInstance.onNextButtonClicked(chipGroup.checkedChipIds)
        findNavController().navigate(R.id.action_interestsFragment_to_questionnaireFragment)
    }
}
