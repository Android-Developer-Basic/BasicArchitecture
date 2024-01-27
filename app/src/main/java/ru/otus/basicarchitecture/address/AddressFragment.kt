package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.address.suggestions.SuggestionsAdapter
import ru.otus.basicarchitecture.databinding.AddressFragmentBinding
import ru.otus.basicarchitecture.interests.InterestsViewModel

@AndroidEntryPoint
class AddressFragment : Fragment(R.layout.address_fragment) {

    private lateinit var binding: AddressFragmentBinding
    private val viewModel: AddressViewModel by viewModels()
    private val adapter: SuggestionsAdapter = SuggestionsAdapter(
        onItemClicked = {
            binding.addressField.setText(it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddressFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.suggestionsContent.suggestions.adapter = adapter
        binding.addressField.addTextChangedListener {
            viewModel.loadSuggestions(input = it?.toString() ?: "")
        }
        viewModel.suggestionsGroupState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AddressViewModel.SuggestionsGroupState.Content -> {
                    binding.suggestionsLoading.loadingGroup.isVisible = false
                    binding.suggestionsContent.contentGroup.isVisible = true
                    binding.suggestionsError.errorGroup.isVisible = false
                }
                AddressViewModel.SuggestionsGroupState.Loading -> {
                    binding.suggestionsLoading.loadingGroup.isVisible = true
                    binding.suggestionsContent.contentGroup.isVisible = false
                    binding.suggestionsError.errorGroup.isVisible = false
                }
                AddressViewModel.SuggestionsGroupState.Error -> {
                    binding.suggestionsLoading.loadingGroup.isVisible = false
                    binding.suggestionsContent.contentGroup.isVisible = false
                    binding.suggestionsError.errorGroup.isVisible = true
                }
                AddressViewModel.SuggestionsGroupState.NotSet -> {
                    binding.suggestionsLoading.loadingGroup.isVisible = false
                    binding.suggestionsContent.contentGroup.isVisible = false
                    binding.suggestionsError.errorGroup.isVisible = false
                }
            }
        }
        viewModel.suggestionsState.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.fillFieldsFromCache(binding)
        binding.nextButton.setOnClickListener {
            viewModel.saveFieldsToCache(binding)
            findNavController().navigate(R.id.addressNext)
        }
    }

}