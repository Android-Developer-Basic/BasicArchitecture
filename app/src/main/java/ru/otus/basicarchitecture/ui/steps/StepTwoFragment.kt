package ru.otus.basicarchitecture.ui.steps

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.ui.NoFilterAdapter
import ru.otus.basicarchitecture.ui.StepTwoViewModel

@AndroidEntryPoint
class StepTwoFragment : Fragment(R.layout.fragment_step_two) {

    private val viewModel: StepTwoViewModel by viewModels()
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actvAddress = view.findViewById<AutoCompleteTextView>(R.id.actvAddress)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        val suggestionsList = mutableListOf<String>()
        val adapter = NoFilterAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, suggestionsList
        )
        actvAddress.setAdapter(adapter)

        actvAddress.addTextChangedListener { text ->
            val query = text.toString()

            viewModel.onAddressInputChanged(query)

            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(500)
                if (query.length >= 3) {
                    viewModel.getSuggestions(query)
                }
            }
        }

        actvAddress.setOnItemClickListener { parent, _, position, _ ->
            val selectedAddress = parent.getItemAtPosition(position) as String
            viewModel.onAddressInputChanged(selectedAddress)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.suggestions.collect { list ->
                        adapter.clear()
                        adapter.addAll(list)
                        adapter.notifyDataSetChanged()

                        if (list.isNotEmpty()) {
                            actvAddress.showDropDown()
                        }
                    }
                }

                launch {
                    viewModel.isNextButtonEnabled.collect { isEnabled ->
                        btnNext.isEnabled = isEnabled
                    }
                }
            }
        }

        btnNext.setOnClickListener {
            viewModel.onNextClicked(actvAddress.text.toString())
            findNavController().navigate(R.id.action_stepTwo_to_stepThree)
        }
    }
}