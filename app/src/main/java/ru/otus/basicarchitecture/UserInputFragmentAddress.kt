package ru.otus.basicarchitecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserInput2Binding

@AndroidEntryPoint
class UserInputFragmentAddress : Fragment(R.layout.fragment_user_input2) {
    private val viewModel: UserInputAddressViewModel by viewModels()
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

        viewModel.suggestions.observe(viewLifecycleOwner) { suggestions ->
            val adapter = CustomArrayAdapter(requireContext(), suggestions.map { it.value })
            binding.myAutoCompleteTextView.setAdapter(adapter)
        }

        binding.myAutoCompleteTextView.addTextChangedListener { text ->
            viewModel.search(text.toString())
            viewModel.viewState.value = viewModel.viewState.value!!.copy(address = text.toString())
        }

        binding.nextButton2.setOnClickListener {
            viewModel.validateAndSaveAddress()
            val result = viewModel.validateState.value
            var outText = ""
            when (result) {
                is ValidateState.LoseFiled -> outText = "Пустое поле " + result.filed
                ValidateState.Ok -> {
                    findNavController().navigate(R.id.action_userInputFragment2_to_userInputFragment3)
                }
                else -> outText = "Ошибка"
            }
            if (outText.isNotEmpty())
                Toast.makeText(context, outText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
