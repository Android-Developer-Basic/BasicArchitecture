package ru.otus.basicarchitecture.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.SummaryFragmentBinding

@AndroidEntryPoint
class SummaryFragment : Fragment(R.layout.summary_fragment) {

    private lateinit var binding: SummaryFragmentBinding
    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SummaryFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedInterestsState.observe(viewLifecycleOwner) { selectedInterests ->
            binding.interestsTitle.visibility =
                if (selectedInterests.isEmpty()) View.GONE else View.VISIBLE
            selectedInterests.forEach { tag ->
                Chip(binding.interests.context).apply {
                    text = tag
                    isClickable = false
                    binding.interests.addView(this)
                }
            }
        }
        viewModel.fillFieldsFromCache(binding)
    }

}