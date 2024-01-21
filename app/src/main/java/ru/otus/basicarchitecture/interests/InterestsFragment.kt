package ru.otus.basicarchitecture.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.InterestsFragmentBinding

@AndroidEntryPoint
class InterestsFragment : Fragment(R.layout.interests_fragment_content) {

    private lateinit var binding: InterestsFragmentBinding
    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InterestsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.interestsGroupState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is InterestsViewModel.InterestsGroupState.Content -> {
                    binding.interestsLoading.loadingGroup.isVisible = false
                    binding.interestsContent.contentGroup.isVisible = true
                    binding.interestsError.errorGroup.isVisible = false
                }
                InterestsViewModel.InterestsGroupState.Loading -> {
                    binding.interestsLoading.loadingGroup.isVisible = true
                    binding.interestsContent.contentGroup.isVisible = false
                    binding.interestsError.errorGroup.isVisible = false
                }
                InterestsViewModel.InterestsGroupState.Error -> {
                    binding.interestsLoading.loadingGroup.isVisible = false
                    binding.interestsContent.contentGroup.isVisible = false
                    binding.interestsError.errorGroup.isVisible = true
                }
                InterestsViewModel.InterestsGroupState.NotSet -> {
                    binding.interestsLoading.loadingGroup.isVisible = false
                    binding.interestsContent.contentGroup.isVisible = false
                    binding.interestsError.errorGroup.isVisible = false
                }
            }
        }
        viewModel.interestsState.distinctUntilChanged().observe(viewLifecycleOwner) { interests ->
            interests.forEach { tag ->
                Chip(binding.interestsContent.tags.context).apply {
                    text = tag
                    isClickable = true
                    isCheckable = true
                    setOnCheckedChangeListener { chip, state ->
                        viewModel.processInterestClick(chip.text.toString(), state)
                    }
                    binding.interestsContent.tags.addView(this)
                }
            }
        }
        viewModel.selectedInterestsState.distinctUntilChanged().observe(viewLifecycleOwner) { selectedInterests ->
            binding.interestsContent.tags.forEach { tag ->
                (tag as? Chip)
                    ?.takeIf { (it.isChecked xor selectedInterests.contains(it.text)) }
                    ?.apply { toggle() }
            }
        }
        viewModel.fillInterestsFromCache()
        binding.interestsContent.nextButton.setOnClickListener {
            viewModel.saveInterestsToCache()
            findNavController().navigate(R.id.interestsNext)
        }
    }

}