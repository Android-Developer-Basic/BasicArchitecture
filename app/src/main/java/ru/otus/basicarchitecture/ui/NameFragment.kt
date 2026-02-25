package ru.otus.basicarchitecture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class NameFragment : Fragment() {

    private val viewModel: NameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.fragment_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameLayout = view.findViewById<TextInputLayout>(R.id.name_text_layout)
        val surnameLayout = view.findViewById<TextInputLayout>(R.id.surname_text_layout)
        val ageLayout = view.findViewById<TextInputLayout>(R.id.age_layout)

        val nameTextView = view.findViewById<TextView>(R.id.name_text_view)
        val surnameTextView = view.findViewById<TextView>(R.id.surname_text_view)
        val ageTextView = view.findViewById<TextView>(R.id.age_text_view)

        val nextBtn = view.findViewById<Button>(R.id.next_btn)


        nameTextView.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                nameLayout.hint = ""
            } else {
                nameLayout.hint = "Name"
            }
        }

        surnameTextView.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                surnameLayout.hint = ""
            } else {
                surnameLayout.hint = "Surname"
            }
        }

        ageTextView.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                ageLayout.hint = ""

                val age = text.toString().toIntOrNull()

                age?.let {
                    if (age < 18) {
                        nextBtn.isEnabled = false
                        Toast.makeText(
                            requireActivity(),
                            "Возраст меньше 18",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        nextBtn.isEnabled = true
                    }
                }

            } else {
                ageLayout.hint = "Age"
            }
        }

        nextBtn.setOnClickListener {
            val age = ageTextView.text.toString().toIntOrNull()

            if (!viewModel.isAgeValid(age)) {
                nextBtn.isEnabled = false

                Toast.makeText(
                    requireActivity(),
                    "Возраст меньше 18",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                nextBtn.isEnabled = true

                viewModel.updateUserInfo(
                    name = nameTextView.text.toString(),
                    surname = surnameTextView.text.toString(),
                    age = ageTextView.text.toString().toIntOrNull()
                )

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        AddressFragment()
                    )
                    .addToBackStack("")
                    .commit()
            }
        }
    }
}