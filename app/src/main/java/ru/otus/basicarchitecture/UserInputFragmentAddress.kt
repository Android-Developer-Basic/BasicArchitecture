package ru.otus.basicarchitecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserInput2Binding

@AndroidEntryPoint
class UserInputFragmentAddress : Fragment(R.layout.fragment_user_input2) {
    private val viewModel: UserInputViewModel by viewModels()
    private var _binding: FragmentUserInput2Binding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInput2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryEditText.addTextChangedListener { text ->
            viewModel.country.value = text.toString()
        }
        binding.cityEditText.addTextChangedListener { text ->
            viewModel.city.value = text.toString()
        }
        binding.addressEditText.addTextChangedListener { text ->
            viewModel.address.value = text.toString()
        }

        binding.nextButton2.setOnClickListener {
            viewModel.validateAndSaveAddress()
            val result = viewModel._state.value
            var outText = "";
            when (result) {
                is ValidateState.BedFiled -> {}
                is ValidateState.LoseFiled -> outText = "Пустое поле " + result.filed
                ValidateState.Not18 -> {}
                ValidateState.Ok -> {
                    findNavController().navigate(R.id.action_userInputFragment2_to_userInputFragment3)
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
