package ru.otus.basicarchitecture.ui.fragments

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodels.Fragment1ViewModel
import ru.otus.basicarchitecture.viewmodels.ViewState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@AndroidEntryPoint
class Fragment1 : Fragment() {

    private val _state = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _state

    private val viewModel: Fragment1ViewModel by viewModels()

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var birthDateEditText: EditText
    private lateinit var nextBtn: Button

    private lateinit var toast: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment1, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameEditText = view.findViewById(R.id.name)
        lastNameEditText = view.findViewById(R.id.surname)
        birthDateEditText = view.findViewById(R.id.birthdate)
        nextBtn = view.findViewById(R.id.fragment1Btn)
        toast = view.findViewById(R.id.toast)

        viewModel.firstName.observe(viewLifecycleOwner) {
            if (it == null || it.isBlank()) {
                firstNameEditText.error = "Поле не должно быть пустым"
            } else {
                firstNameEditText.error = null
            }
        }

        viewModel.lastName.observe(viewLifecycleOwner) {
            if (it == null || it.isBlank()) {
                lastNameEditText.error = "Поле не должно быть пустым"
            } else {
                lastNameEditText.error = null
            }
        }

        viewModel.birthDate.observe(viewLifecycleOwner) {
            try {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val dob = LocalDate.parse(it, formatter)
                val years = ChronoUnit.YEARS.between(dob, LocalDate.now())
                if (years < 18) {
                    birthDateEditText.error = "Вам не исполнилось 18"
                } else {
                    birthDateEditText.error = null
                }
            } catch (e: Exception) {
                birthDateEditText.error = "Неверный формат даты"
            }
        }

        viewModel.isFormValid.observe(viewLifecycleOwner) { isValid ->
            nextBtn.isEnabled = isValid
        }

        firstNameEditText.addTextChangedListener { viewModel.firstName.value = it.toString() }
        lastNameEditText.addTextChangedListener { viewModel.lastName.value = it.toString() }
        birthDateEditText.addTextChangedListener { viewModel.birthDate.value = it.toString() }

        nextBtn.setOnClickListener {
            // Сохраняем данные из EditText в LiveData
            viewModel.firstName.value = firstNameEditText.text.toString()
            viewModel.lastName.value = lastNameEditText.text.toString()
            viewModel.birthDate.value = birthDateEditText.text.toString()

            // Выполняем валидацию и сохраняем данные в кэш
            if (nextBtn.isEnabled) {
                viewModel.saveData()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Fragment2())
                    .addToBackStack(null)
                    .commit()
            } else {
                Snackbar.make(toast, "Валидация не пройдена", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}