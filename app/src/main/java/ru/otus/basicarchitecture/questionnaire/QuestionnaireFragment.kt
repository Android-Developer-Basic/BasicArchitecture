package ru.otus.basicarchitecture.questionnaire

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {
    private val questionnaireViewModelInstance: QuestionnaireFragmentModel by viewModels()
    private lateinit var nameField: MaterialTextView
    private lateinit var surnameField: MaterialTextView
    private lateinit var birthdayField: MaterialTextView
    private lateinit var addressField: MaterialTextView
    private lateinit var chipGroup: ChipGroup
    private lateinit var listOfInterests: Set<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipGroup = view.findViewById(R.id.checkedChipGroup)
        nameField = view.findViewById(R.id.nameTextView)
        surnameField = view.findViewById(R.id.surnameTextView)
        birthdayField = view.findViewById(R.id.birthdayTextView)
        addressField = view.findViewById(R.id.addressTextView)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                questionnaireViewModelInstance.viewState.collect {
                    nameField.setTextKeepState(it.name)
                    surnameField.setTextKeepState(it.surname)
                    birthdayField.setTextKeepState(it.birthday)
                    addressField.setTextKeepState(it.address)
                    listOfInterests = it.selectedInterests
                    listOfInterests.forEachIndexed { index, tagName ->
                        chipGroup.addView(context?.let { createTagChip(it, tagName, index) })
                    }
                }
            }
        }
    }

    private fun createTagChip(context: Context, chipName: String, index: Int): Chip {
        return Chip(context).apply {
            text = chipName
            id = index
        }

    }
}