package ru.otus.basicarchitecture.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding
import ru.otus.basicarchitecture.databinding.FragmentResultBinding
import ru.otus.basicarchitecture.viewmodel.InterestsViewModel
import ru.otus.basicarchitecture.viewmodel.NameViewModel
import java.util.stream.Collectors

class InterestsFragment : Fragment() {
    companion object {
        fun newInstance() = InterestsFragment()
    }
    private lateinit var binding: FragmentInterestsBinding
    private val viewModel: InterestsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterestsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener() {
            val tags = binding.tagGroup
            var interests = tags.checkedChipIds.stream()
                .map { id -> tags.findViewById<Chip>(id) }
                .map { chip -> chip.text.toString() }
                .collect(Collectors.toList())

            viewModel.saveData(interests)

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, ResultFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
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