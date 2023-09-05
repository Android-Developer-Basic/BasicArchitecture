package ru.otus.basicarchitecture

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter
import ru.otus.basicarchitecture.databinding.FragmentResultsBinding
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment(R.layout.fragment_results) {
    private lateinit var binding: FragmentResultsBinding
    @Inject
    lateinit var wizardCache: WizardCache
    private lateinit var listOfInterests : List<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameTextView.text = wizardCache.name
        binding.surnameTextView.text = wizardCache.surname

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.birthdayTextView.text = wizardCache.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
        listOfInterests = wizardCache.interests
        listOfInterests.forEach { tag ->
            val chip = Chip(binding.checkedChipGroup.context)
            chip.text= tag
            chip.isClickable = false
            chip.isCheckable = false
            chip.textSize = 25F
            binding.checkedChipGroup.addView(chip)
        }

        binding.addressTextView.text = wizardCache.address
    }
}


