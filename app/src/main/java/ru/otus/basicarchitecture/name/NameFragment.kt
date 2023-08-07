package ru.otus.basicarchitecture.name

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import ru.otus.basicarchitecture.R
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class NameFragment : Fragment(R.layout.fragment_name), View.OnFocusChangeListener {
    private val nameViewModelInstance : NameFragmentModel by viewModels()
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
        birthdayField  = view.findViewById(R.id.birthdayTextEdit)
        nextButton = view.findViewById(R.id.nameNextButton)

        nameField.onFocusChangeListener = this
        surnameField.onFocusChangeListener = this

        datePicker =MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select your birthday")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        birthdayField.setOnClickListener { onCalendarOpen() }
        nextButton.setOnClickListener { goToNextScreen() }
        nextButton.isEnabled = nameViewModelInstance.isButtonEnabled()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onCalendarOpen(){
        datePicker.show(this.parentFragmentManager, "Calendar")
        datePicker.addOnPositiveButtonClickListener {
            calendar.timeInMillis = it
            val sdf =  SimpleDateFormat("dd.MM.yyyy", Locale.US)
            birthdayField.setText(sdf.format(calendar.time))
            nameViewModelInstance.onSetAge(calendar.time)
            nextButton.isEnabled = nameViewModelInstance.isButtonEnabled()
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            nameViewModelInstance.checkTextFields(
                nameField.text,
                surnameField.text,
            )
            nextButton.isEnabled = nameViewModelInstance.isButtonEnabled()
        }
    }

    private fun goToNextScreen() {
        nameViewModelInstance.onNextButtonClicked()
        findNavController().navigate(R.id.action_nameFragment_to_addressFragment)
    }

}

