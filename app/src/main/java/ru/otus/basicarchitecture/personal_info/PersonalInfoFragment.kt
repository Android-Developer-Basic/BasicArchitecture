package ru.otus.basicarchitecture.personal_info

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.PersonalInfoFragmentBinding
import java.util.Calendar

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

    private var binding: PersonalInfoFragmentBinding? = null
    private inline fun withBinding(block: PersonalInfoFragmentBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: PersonalInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PersonalInfoFragmentBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            buttonMoveToFragmentAddress.setOnClickListener {
                findNavController().navigate(R.id.action_personalInfoFragment_to_addressInfoFragment)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {

                        if (it.isValidName) {
                            personalInfoName.setTextKeepState(it.name)
                        }
                        else if(it.name.isNotEmpty()) {
                            personalInfoName.error = resources.getString(R.string.name_error)
                        }

                        if (it.isValidSurname) {
                            personalInfoSurname.setTextKeepState(it.surname)
                        }
                        else if(it.surname.isNotEmpty()) {
                            personalInfoSurname.error = resources.getString(R.string.name_error)
                        }

                        // TODO Я не понимаю почему. Но даже после ввода валидной даты. Ошибка продолжает показываться. И не сбрасывается
                        if (it.isValidDateOfBirth) {
                            personalInfoDateOfBirth.setTextKeepState(it.dateOfBirth)
                        }
                        else if(it.dateOfBirth.isNotEmpty()) {
                            personalInfoDateOfBirth.error = resources.getString(R.string.date_of_birth_error)
                        }

                        buttonMoveToFragmentAddress.isEnabled = it.isValidName && it.isValidSurname && it.isValidDateOfBirth
                    }
                }
            }

            personalInfoName.setTextKeepState(viewModel.viewState.value.name)
            personalInfoSurname.setTextKeepState(viewModel.viewState.value.surname)
            personalInfoDateOfBirth.setTextKeepState(viewModel.viewState.value.dateOfBirth)

            personalInfoName.addTextChangedListener {
                viewModel.setName(it.toString())
            }

            personalInfoSurname.addTextChangedListener {
                viewModel.setSurname(it.toString())
            }

            personalInfoDateOfBirth.inputType = InputType.TYPE_NULL
            personalInfoDateOfBirth.keyListener = null
            personalInfoDateOfBirth.setOnClickListener{ showDatePickerDialog() }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = context?.let {
            DatePickerDialog(
                it,
                {
                    view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        viewModel.setDateOfBirth(year,month, dayOfMonth)
                },
                year,
                month,
                day
            )
        }

        datePickerDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}