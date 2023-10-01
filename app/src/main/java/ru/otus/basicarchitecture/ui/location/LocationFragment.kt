package ru.otus.basicarchitecture.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentLocationBinding
import ru.otus.basicarchitecture.navigate

@AndroidEntryPoint
class LocationFragment  : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private val viewModel by viewModels<LocationFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locationState.observe(viewLifecycleOwner){ state ->
            binding.editTextCountry.setText(state.country)
            binding.editTextAddress.setText(state.address)
            binding.editTextCity.setText(state.city)
        }

        binding.buttonNext.setOnClickListener {
            when{
                binding.editTextCountry.text.toString().trim().isEmpty() -> {
                    binding.editTextCountry.requestFocus()
                    binding.editTextCountry.error = getString(R.string.error_text_field)
                }
                binding.editTextCity.text.toString().trim().isEmpty() -> {
                    binding.editTextCity.requestFocus()
                    binding.editTextCity.error = getString(R.string.error_text_field)
                }
                binding.editTextAddress.text.toString().trim().isEmpty() -> {
                    binding.editTextAddress.requestFocus()
                    binding.editTextAddress.error = getString(R.string.error_text_field)
                }
                else -> {
                    viewModel.saveDataToStorage(LocationModel(
                        binding.editTextCountry.text.toString(),
                        binding.editTextCity.text.toString(),
                        binding.editTextAddress.text.toString()
                    ))
                    navigate(R.id.actionLocationFragmentToInterestsFragment)
                }
            }
        }
    }
}