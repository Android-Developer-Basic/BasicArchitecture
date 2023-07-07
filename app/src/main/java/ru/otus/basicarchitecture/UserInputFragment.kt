package ru.otus.basicarchitecture

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserInputBinding

@AndroidEntryPoint
class UserInputFragment : Fragment(R.layout.fragment_user_input) {
    private val viewModel: UserInputViewModel by viewModels()
    private var _binding: FragmentUserInputBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        binding.dateOfBirthEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.nextButton.isEnabled = true
                if (start != s.length && (s.length == 2 || s.length == 5)) {
                    binding.dateOfBirthEditText.setText("${s}.")
                    binding.dateOfBirthEditText.setSelection(s.length + 1)
                }
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.dateOfBirth.value = binding.dateOfBirthEditText.text.toString()
            }
        })

        binding.firstNameEditText.addTextChangedListener { text ->
            viewModel.firstName.value = text.toString()
        }
        binding.lastNameEditText.addTextChangedListener { text ->
            viewModel.lastName.value = text.toString()
        }

        binding.nextButton.setOnClickListener {
            viewModel.validateAndSaveFirst()
            val result = viewModel._state.value
            var outText = "";
            when (result) {
                    is ValidateState.BedFiled -> outText = "Некорректное поле " + result.filed
                    is ValidateState.LoseFiled -> outText = "Пустое поле " + result.filed
                    ValidateState.Not18 -> {
                        outText = "Нет 18 лет"
                        it.isEnabled = false
                    }
                    ValidateState.Ok -> {
                        findNavController().navigate(R.id.action_userInputFragment_to_userInputFragment2)
                    }
                    null ->  outText = "Ошибка"
                }
            Toast.makeText(context, outText, Toast.LENGTH_SHORT).show()
            }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
