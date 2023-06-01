package ru.otus.basicarchitecture.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentResultBinding
import ru.otus.basicarchitecture.WizardCache
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {
    companion object {
        fun newInstance() = ResultFragment()
    }

    @Inject
    lateinit var wizardCache: WizardCache

    private lateinit var binding: FragmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultName.text = wizardCache.name
        binding.resultSurname.text = wizardCache.surname
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.date.text = wizardCache.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }

        binding.interests.text = wizardCache.interests.toString()
        binding.address.text = "${wizardCache.country}, ${wizardCache.city}, ${wizardCache.address}";
    }


}