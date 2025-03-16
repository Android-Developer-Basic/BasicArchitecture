package ru.otus.basicarchitecture.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.utils.Utils
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class PersonalDataFragment : Fragment() {

    @Inject
    lateinit var viewModel: PersonalDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_personal_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextInputEditText>(R.id.name)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.name.value = s.toString()
                }
            })

        view.findViewById<TextInputEditText>(R.id.surname)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.surname.value = s.toString()
                }
            })



        view.findViewById<TextView>(R.id.dateInput).setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                viewModel.saveBirthDate(it)
                view.findViewById<TextView>(R.id.dateInput).text =
                    Utils.dateFormatter.format(Date(it))
            }

            datePicker.show(parentFragmentManager, null)
        }

        val nextButton = view.findViewById<Button>(R.id.nextToAddress)

        viewModel.isDateValid.observe(viewLifecycleOwner) {
            if(viewModel.isDateValid.value == false) {
                Toast.makeText(
                    requireActivity(),
                    "You must be over 18 to proceed",
                    Toast.LENGTH_LONG
                ).show()
            }
            nextButton.isEnabled = viewModel.isDateValid.value == true
        }


        nextButton.setOnClickListener {
            viewModel.saveToCash()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddressFragment())
                .addToBackStack(null)
                .commit()

        }
    }
}