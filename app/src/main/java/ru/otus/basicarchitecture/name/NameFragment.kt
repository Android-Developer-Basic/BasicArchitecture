package ru.otus.basicarchitecture.name

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class NameFragment : Fragment(R.layout.fragment_name), View.OnFocusChangeListener {
    private val nameViewModelInstance: NameFragmentModel by viewModels()
    private lateinit var nextButton: Button
    private lateinit var nameField: TextInputEditText
    private lateinit var birthdayField: TextInputEditText
    private lateinit var surnameField: TextInputEditText
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var calendar: Calendar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameField = view.findViewById(R.id.nameTextEdit)
        surnameField = view.findViewById(R.id.surnameTextEdit)
        birthdayField = view.findViewById(R.id.birthdayTextEdit)
        nextButton = view.findViewById(R.id.nameNextButton)

        nameViewModelInstance.nameFragmentState.observe(viewLifecycleOwner) { state ->
            nameField.setText(state.name)
            surnameField.setText(state.surname)
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
            birthdayField.setText(sdf.format(state.date))
            nextButton.isEnabled = state.accessNextButton
        }

        //nameField.imeOptions = EditorInfo.IME_ACTION_DONE
        //surnameField.imeOptions = EditorInfo.IME_ACTION_DONE

        nameField.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // Clear focus from the TextInputEditText
                v.clearFocus()
                // Hide the keyboard
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                // Return false to let the system handle the action
                false
            }
        }

        surnameField.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Clear focus from the TextInputEditText
                v.clearFocus()
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                // Return false to let the system handle the action
                false
            }
        }

        nameField.onFocusChangeListener = this
        surnameField.onFocusChangeListener = this

        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select your birthday")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        birthdayField.setOnClickListener { onCalendarOpen() }
        nextButton.setOnClickListener { goToNextScreen() }
        nextButton.isEnabled = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onCalendarOpen() {
        datePicker.show(this.parentFragmentManager, "Calendar")
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
            val date = Date(it)
            birthdayField.setText(sdf.format(date))
            nameViewModelInstance.onSetAge(date)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            nameViewModelInstance.checkTextFields(
                nameField.text,
                surnameField.text,
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun goToNextScreen() {
        nameViewModelInstance.onNextButtonClicked()
        findNavController().navigate(R.id.action_nameFragment_to_addressFragment)
    }

}

