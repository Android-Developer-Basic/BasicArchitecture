package ru.otus.basicarchitecture.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.PersonalDataFragmentBinding

@AndroidEntryPoint
class PersonalDataFragment : Fragment(R.layout.personal_data_fragment) {

    private lateinit var binding: PersonalDataFragmentBinding
    private val viewModel: PersonalDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PersonalDataFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.nextButtonState.observe(viewLifecycleOwner) {
            binding.nextButton.isEnabled = it
        }
        binding.dateField.addTextChangedListener {
            viewModel.updateNextButtonStatus(it?.toString())
        }
        viewModel.fillFieldsFromCache(binding)
        binding.nextButton.setOnClickListener {
            viewModel.saveFieldsToCache(binding)
            findNavController().navigate(R.id.personalDataNext)
        }
    }

}