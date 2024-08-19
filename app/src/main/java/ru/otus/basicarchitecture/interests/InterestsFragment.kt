package ru.otus.basicarchitecture.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding
import ru.otus.basicarchitecture.info.InfoFragment
import javax.inject.Inject


@AndroidEntryPoint
class InterestsFragment: Fragment() {

    private lateinit var binding: FragmentInterestsBinding

    @Inject
    lateinit var viewModel: InterestsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInterestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            viewModel.save()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, InfoFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }

       viewModel.interestsList.forEach { chip ->
           Chip(context).apply {
               text = chip
               isClickable = true
               isCheckable = true
               isChecked = viewModel.selectedInterests.value?.contains(chip) ?: false
               setOnCheckedChangeListener{ _, isChecked ->
                   viewModel.addOrRemoveInterest(chip)
               }
               binding.chipGroup.addView(this)
           }
       }
    }
}