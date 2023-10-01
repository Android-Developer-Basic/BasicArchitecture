package ru.otus.basicarchitecture.ui.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.createTagChip
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding
import ru.otus.basicarchitecture.navigate

@AndroidEntryPoint
class InterestsFragment() : Fragment(){
    private lateinit var binding: FragmentInterestsBinding
    private lateinit var listOfInterests : List<String?>
    private val viewModel by viewModels<InterestsFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInterestsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.isEnabled = false
        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            binding.buttonNext.isEnabled = checkedIds.isNotEmpty()
        }

        listOfInterests = viewModel.listOfInterests
        listOfInterests.forEachIndexed { index, tagName ->
            binding.chipGroup.addView(context?.let { createTagChip(it, tagName?:"", index) })
        }

        binding.buttonNext.setOnClickListener {
            viewModel.saveDataToStorage(binding.chipGroup.checkedChipIds)
            navigate(R.id.actionInterestsFragmentToResultFragment)
        }
    }
}