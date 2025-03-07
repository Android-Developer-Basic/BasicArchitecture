package ru.otus.basicarchitecture.ui.authorization

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorizationFragment()
    }

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextInput = view.findViewById<TextInputLayout>(R.id.nameTextInput)
        val surnameTextInput = view.findViewById<TextInputLayout>(R.id.surnameTextInput)

        val navController = findNavController()
        val toAddress = view.findViewById<Button>(R.id.toAddress)
        anAdultObserve(toAddress)

        toAddress.setOnClickListener {
            viewModel.setName(nameTextInput.editText?.text.toString())
            viewModel.setSurname(surnameTextInput.editText?.text.toString())
            navController.navigate(R.id.action_to_addressFragment)
        }

        val dateEdtText = view.findViewById<TextInputEditText>(R.id.idEdtDate)
        onDateEditText(dateEdtText, view)
    }

    private fun onDateEditText(dateEdtText: TextInputEditText, view: View) {
        dateEdtText.setOnClickListener {
            val calendar = Calendar.getInstance()

            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            viewModel.setCurrentDate(currentDay, currentMonth, currentYear)

            val datePickerDialog = DatePickerDialog(
                view.context,
                { _, year, month, day ->
                    val date = (day.toString() + "-" + (month + 1) + "-" + year)
                    dateEdtText.setText(date)
                    viewModel.ageVerification(day, month, year)
                },
                currentYear,
                currentMonth,
                currentDay
            )
            datePickerDialog.show()
        }
    }

    private fun anAdultObserve(toAddress: Button) {

        viewModel.anAdult.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(
                    requireActivity(),
                    "You must be over 18 ears",
                    Toast.LENGTH_SHORT
                ).show()
            }
            toAddress.isEnabled = it
        }
    }
}