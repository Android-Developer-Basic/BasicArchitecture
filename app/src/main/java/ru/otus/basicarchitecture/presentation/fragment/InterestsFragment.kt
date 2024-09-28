package ru.otus.basicarchitecture.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding
import ru.otus.basicarchitecture.presentation.viewModel.InterestsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class InterestsFragment : Fragment() {

    private lateinit var binding: FragmentInterestsBinding

    @Inject
    lateinit var viewModel: InterestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInterestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.interests.value?.forEach { chip ->
            Chip(context).apply {
                text = chip
                isClickable = true
                isCheckable = true
                isChecked = viewModel.selectedInterests.value?.contains(chip) ?: false
                setOnCheckedChangeListener { _, _ ->
                    viewModel.addOrRemoveInterest(chip)
                }
                binding.chipGroup.addView(this)
            }
        }

        binding.buttonNextOnInterests.setOnClickListener {
            viewModel.save()

            parentFragmentManager.beginTransaction()
                .replace(R.id.container, SummaryFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}