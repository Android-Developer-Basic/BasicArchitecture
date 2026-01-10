package ru.otus.basicarchitecture.ui.steps

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.ui.StepOneViewModel

@AndroidEntryPoint
class StepOneFragment : Fragment(R.layout.fragment_step_one) {
    private val viewModel: StepOneViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etSurname = view.findViewById<EditText>(R.id.etSurname)
        val etBirthDate = view.findViewById<EditText>(R.id.etBirthDate)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        val inputWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etBirthDate.hasFocus() || etName.hasFocus() || etSurname.hasFocus()) {
                    viewModel.onInputChanged(
                        etName.text.toString(),
                        etSurname.text.toString(),
                        etBirthDate.text.toString()
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etName.addTextChangedListener(inputWatcher)
        etSurname.addTextChangedListener(inputWatcher)

        etBirthDate.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val str = s.toString().replace(".", "")
                val formatted = StringBuilder()
                for (i in str.indices) {
                    formatted.append(str[i])
                    if ((i == 1 || i == 3) && i < str.length - 1) formatted.append(".")
                }

                val finalStr =
                    if (formatted.length > 10) formatted.substring(0, 10) else formatted.toString()

                if (s.toString() != finalStr) {
                    etBirthDate.setText(finalStr)
                    etBirthDate.setSelection(finalStr.length)
                }

                viewModel.onInputChanged(
                    etName.text.toString(), etSurname.text.toString(), finalStr
                )

                isFormatting = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isNextButtonEnabled.collect { isEnabled ->
                        btnNext.isEnabled = isEnabled
                    }
                }
                launch {
                    viewModel.errorEvents.collect { message ->
                        etBirthDate.error = message
                    }
                }
            }
        }

        btnNext.setOnClickListener {
            viewModel.onNextClicked()
            findNavController().navigate(R.id.action_stepOne_to_stepTwo)
        }
    }
}