package ru.otus.basicarchitecture.presentation.interestsFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding
import ru.otus.basicarchitecture.presentation.summaryFragment.SummaryFragment

@AndroidEntryPoint
class InterestsFragment : Fragment() {

    private var _binding: FragmentInterestsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTagCloud()
        setupListeners()
    }

    private fun setupTagCloud() {
        val interests = viewModel.getInterests()
        interests.forEach { interest ->
            val chip = Chip(requireContext()).apply {
                text = interest
                isCheckable = true
                isChecked = viewModel.selectedInterests.contains(interest)
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        viewModel.selectedInterests.add(interest)
                    } else {
                        viewModel.selectedInterests.remove(interest)
                    }
                }
            }
            binding.chipGroupInterests.addView(chip)
        }
    }

    private fun setupListeners() {
        binding.buttonNext.setOnClickListener {
            if (viewModel.saveData()) {
                navigateToNextFragment()
            }
        }
    }

    private fun navigateToNextFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SummaryFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}