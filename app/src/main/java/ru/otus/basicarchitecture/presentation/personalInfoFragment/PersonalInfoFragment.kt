package ru.otus.basicarchitecture.presentation.personalInfoFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentPersonalInfoBinding
import ru.otus.basicarchitecture.presentation.addressFragment.AddressFragment

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonalInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonNext.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val surname = binding.editTextSurname.text.toString()
            val dateOfBirth = binding.editTextDateOfBirth.text.toString()

            viewModel.name = name
            viewModel.surname = surname
            viewModel.dateOfBirth = dateOfBirth

            if (viewModel.validateAndProceed()) {
                navigateToNextFragment()
            } else {
                Toast.makeText(
                    context,
                    "You must be 18 years or older to continue.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigateToNextFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AddressFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}