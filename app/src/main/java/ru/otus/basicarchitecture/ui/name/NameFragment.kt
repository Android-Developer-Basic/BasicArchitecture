package ru.otus.basicarchitecture.ui.name

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentNameBinding
import ru.otus.basicarchitecture.navigate
import ru.otus.basicarchitecture.utils.DateTimeConverter.currentDateTimeMillis
import ru.otus.basicarchitecture.utils.DateTimeConverter.formatDate
import java.util.Date

@AndroidEntryPoint
class NameFragment : Fragment() {
    private lateinit var binding: FragmentNameBinding
    private val viewModel by viewModels<NameFragmentViewModel>()
    private var birth: Date ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.ageState.observe(viewLifecycleOwner){ birth ->
            binding.buttonBirth.text = formatDate.format(birth)
            binding.buttonNext.isEnabled = viewModel.checkIsAdult(birth)
        }

        binding.buttonBirth.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Birth")
                .setSelection(
                    currentDateTimeMillis()
                )
                .build().apply {
                    addOnPositiveButtonClickListener {
                        birth = Date(it)
                        viewModel.onSetAge(Date(it))
                        binding.buttonBirth.text = birth?.toString()
                    }
                    addOnNegativeButtonClickListener {

                    }
                }.show(parentFragmentManager, "Birth")
        }

        binding.buttonNext.setOnClickListener {
            when{
                binding.editTextName.text.toString().trim().isEmpty() -> {
                    binding.editTextName.requestFocus()
                    binding.editTextName.error = getString(R.string.error_text_field)
                }
                binding.editTextName.text.toString().trim().isEmpty() -> {
                    binding.editTextName.requestFocus()
                    binding.editTextName.error = getString(R.string.error_text_field)
                }
                birth == null -> {
                    Toast.makeText(requireContext(), "Заполните поле возраста.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.saveDataToStorage(NameModel(
                        binding.editTextName.text.toString(),
                        binding.editTextName.text.toString(),
                        birth!!
                    ))
                    navigate(R.id.actionNameFragmentToLocationFragment)
                }
            }
        }
    }
}