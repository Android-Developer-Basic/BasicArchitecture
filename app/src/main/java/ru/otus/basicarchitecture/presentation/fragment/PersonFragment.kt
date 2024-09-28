package ru.otus.basicarchitecture.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentPersonBinding
import ru.otus.basicarchitecture.domain.helper.DateValidation
import ru.otus.basicarchitecture.presentation.viewModel.PersonViewModel
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding

    @Inject
    lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name.addTextChangedListener { viewModel.name.value = it.toString() }
        binding.surname.addTextChangedListener { viewModel.surname.value = it.toString() }
        binding.date.addTextChangedListener(DateValidation(viewModel))

        viewModel.error.observe(viewLifecycleOwner) {
            it?.also {
                AlertDialog.Builder(requireActivity())
                    .setMessage(it)
                    .show()
            }
            binding.buttonNextOnPerson.isEnabled = viewModel.error.value == null
        }

        binding.buttonNextOnPerson.setOnClickListener {
            if (viewModel.validateDateOfBirth(binding.date.text.toString())) {
                viewModel.save()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, AddressFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}