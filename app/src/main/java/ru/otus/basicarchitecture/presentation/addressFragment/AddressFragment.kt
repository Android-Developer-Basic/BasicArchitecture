package ru.otus.basicarchitecture.presentation.addressFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import ru.otus.basicarchitecture.presentation.interestsFragment.InterestsFragment

@AndroidEntryPoint
class AddressFragment : Fragment() {
    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonNext.setOnClickListener {
            val country = binding.editTextCountry.text.toString()
            val city = binding.editTextCity.text.toString()
            val address = binding.editTextAddress.text.toString()

            viewModel.country = country
            viewModel.city = city
            viewModel.address = address

            if (viewModel.saveData()) {
                navigateToNextFragment()
            }
        }
    }

    private fun navigateToNextFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, InterestsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}