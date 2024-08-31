package ru.otus.basicarchitecture.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._ru_otus_basicarchitecture_viewmodel_PersonViewModel_HiltModules_BindsModule
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodel.PersonViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment @Inject constructor() : Fragment() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var birthDateEditText: EditText
    private lateinit var nextBtn: Button


    private val viewModel: PersonViewModel by viewModels()

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameEditText = view.findViewById(R.id.name)
        lastNameEditText = view.findViewById(R.id.surname)
        birthDateEditText = view.findViewById(R.id.birthdate)
        nextBtn = view.findViewById(R.id.button_to_address)


        viewModel.state.observe(viewLifecycleOwner) {
            if (it == null || it.name.isBlank()) {
                firstNameEditText.error = "Поле не должно быть пустым"
            } else {
                firstNameEditText.error = null
            }

            if (it == null || it.surname.isBlank()) {
                lastNameEditText.error = "Поле не должно быть пустым"
            } else {
                lastNameEditText.error = null
            }

            try {
                val dob = LocalDate.parse(it.dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                val years = Period.between(dob, LocalDate.now()).years
                if (years < 18) {
                    birthDateEditText.error = "Вам не исполнилось 18"
                } else {
                    birthDateEditText.error = null
                }
            } catch (e: Exception) {
                birthDateEditText.error = "Неверный формат даты"
            }
        }



        viewModel.state.observe(viewLifecycleOwner){
            if(firstNameEditText.text.toString()!= it.name) {
                firstNameEditText.setText(it.name)
            }
            if(lastNameEditText.text.toString()!= it.surname) {
                lastNameEditText.setText(it.surname)
            }
            if(birthDateEditText.text.toString()!= it.dateOfBirth) {
                birthDateEditText.setText(it.dateOfBirth)
            }
        }


        firstNameEditText.addTextChangedListener { viewModel.updateName(it.toString()) }
        lastNameEditText.addTextChangedListener { viewModel.updateSurname(it.toString()) }
        birthDateEditText.addTextChangedListener { viewModel.updateDateOfBirth(it.toString()) }

        nextBtn.setOnClickListener {

            if (viewModel.validateForm()) {
                viewModel.saveData()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag, AddressFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            } else {
               Toast.makeText(context, "Валидация не пройдена", Toast.LENGTH_SHORT).show()
            }
        }
    }
}