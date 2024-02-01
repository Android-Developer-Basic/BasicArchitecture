package ru.otus.basicarchitecture.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.address_info.AddressInfoViewModel
import ru.otus.basicarchitecture.databinding.FragmentSummaryBinding
import ru.otus.basicarchitecture.interests.InterestsViewModel
import ru.otus.basicarchitecture.personal_info.PersonalInfoViewModel

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private var binding: FragmentSummaryBinding? = null
    private inline fun withBinding(block: FragmentSummaryBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    textViewName.text = viewModel.personalInfoState.value.name
                    textViewSurname.text = viewModel.personalInfoState.value.surname
                    textViewBirthDate.text = viewModel.personalInfoState.value.dateOfBirth

                    textViewAddress.text = viewModel.getCommonAddress()

                    viewModel.interestInformation.value.selectedInterest.forEach {
                        Chip(context).apply {
                            text = it
                            isClickable = false
                            isCheckable = false
                            chipGroupInterests.addView(this)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}