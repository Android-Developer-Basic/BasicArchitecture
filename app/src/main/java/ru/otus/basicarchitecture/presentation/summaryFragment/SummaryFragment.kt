package ru.otus.basicarchitecture.presentation.summaryFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentSummaryBinding

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем данные из ViewModel в UI
        binding.textViewName.text = viewModel.getName()
        binding.textViewSurname.text = viewModel.getSurname()
        binding.textViewDateOfBirth.text = viewModel.getDateOfBirth()
        binding.textViewAddress.text = viewModel.getFullAddress()
        binding.textViewInterests.text = viewModel.getInterestsAsString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
