package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class StepTwoFragment : Fragment(R.layout.fragment_step_two) {
    private val viewModel: StepTwoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCountry = view.findViewById<EditText>(R.id.etCountry)
        val etCity = view.findViewById<EditText>(R.id.etCity)
        val etStreetHouse = view.findViewById<EditText>(R.id.etAddress)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validate(
                    etCountry.text.toString(),
                    etCity.text.toString(),
                    etStreetHouse.text.toString()
                )
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etCountry.addTextChangedListener(watcher)
        etCity.addTextChangedListener(watcher)
        etStreetHouse.addTextChangedListener(watcher)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isNextButtonEnabled.collect { isEnabled ->
                    btnNext.isEnabled = isEnabled
                }
            }
        }

        btnNext.setOnClickListener {
            viewModel.onNextClicked(
                etCountry.text.toString(),
                etCity.text.toString(),
                etStreetHouse.text.toString()
            )
            findNavController().navigate(R.id.action_stepTwo_to_stepThree)
        }
    }
}