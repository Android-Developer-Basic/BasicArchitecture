package ru.otus.basicarchitecture.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentSummaryBinding
import ru.otus.basicarchitecture.presentation.viewModel.SummaryViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding

    @Inject
    lateinit var viewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameInfo.text = viewModel.name
        binding.surnameInfo.text = viewModel.surname
        binding.birthDateInfo.text = viewModel.dateOfBirth
        binding.addressInfo.text = viewModel.fullAddress

        viewModel.interests.forEach {
            Chip(context).apply {
                text = it
                isClickable = false
                isCheckable = false
                binding.chipGroupInfo.addView(this)
            }
        }
    }
}