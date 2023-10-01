package ru.otus.basicarchitecture.ui.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.createTagChip
import ru.otus.basicarchitecture.databinding.FragmentResultBinding
import ru.otus.basicarchitecture.ui.interests.InterestsFragmentViewModel
import ru.otus.basicarchitecture.utils.DateTimeConverter.formatDate

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val viewModel by viewModels<ResultFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestWizardCache()

        viewModel.resultState.observe(viewLifecycleOwner){ cache ->
            binding.nameTextView.text = cache.name
            binding.surnameTextView.text = cache.surname
            binding.textViewBurth.text = formatDate.format(cache.birth)
            binding.addressTextView.text = "${cache.country}; ${cache.city}; ${cache.address}"
            cache.selectedInterests.forEachIndexed { index, tag ->
                binding.chipGroup.addView(createTagChip(requireContext(), tag, index))
            }
        }
    }
}