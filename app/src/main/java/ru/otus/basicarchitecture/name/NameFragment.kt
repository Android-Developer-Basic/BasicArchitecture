package ru.otus.basicarchitecture.name


import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class NameFragment : Fragment(R.layout.fragment_name) {
    private val nameViewModelInstance: NameFragmentModel by viewModels()

    private lateinit var nameField: TextInputEditText
    private lateinit var surnameField: TextInputEditText
    private lateinit var birthdayField: TextInputEditText
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var calendar: Calendar
    private lateinit var nextButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameField = view.findViewById(R.id.nameTextEdit)
        surnameField = view.findViewById(R.id.surnameTextEdit)
        birthdayField = view.findViewById(R.id.birthdayTextEdit)
        nextButton = view.findViewById(R.id.nameNextButton)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                nameViewModelInstance.viewState.collect {
                    nameField.setTextKeepState(it.name)
                    surnameField.setTextKeepState(it.surname)
                    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                    birthdayField.setTextKeepState(sdf.format(it.date))
                    nextButton.isEnabled = it.accessNextButton
                }
            }
        }

        nameField.addTextChangedListener {
            nameViewModelInstance.setName(it.toString())
        }

        surnameField.addTextChangedListener {
            nameViewModelInstance.setSurname(it.toString())
        }

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
            nameViewModelInstance.setBirthday(date)
        }
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_nameFragment_to_addressFragment)
    }

}

