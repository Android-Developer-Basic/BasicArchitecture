package ru.otus.basicarchitecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding
import androidx.navigation.fragment.findNavController
import ru.otus.basicarchitecture.viewmodel.InterestsViewModel
import com.google.android.material.chip.Chip
import java.util.stream.Collectors
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class InterestsFragment : Fragment(R.layout.fragment_interests) {

    private lateinit var binding: FragmentInterestsBinding
    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterestsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener() {
            val tags = binding.tagGroup
            var interests = tags.checkedChipIds.stream()
                .map { id -> tags.findViewById<Chip>(id) }
                .map { chip -> chip.text.toString() }
                .collect(Collectors.toList())

            viewModel.saveData(interests)

            findNavController().navigate(R.id.action_interestsFragment_to_resultFragment)
        }

        viewModel.loadData()

        viewModel.getResultInterestsLive().observe(viewLifecycleOwner) {
            val tags = binding.tagGroup
            it?.forEach { tag ->
                val chip = Chip(tags.context)
                chip.text= tag
                chip.isClickable = true
                chip.isCheckable = true
                chip.textSize = 25F
                tags.addView(chip)
            }
        }

    }
    }