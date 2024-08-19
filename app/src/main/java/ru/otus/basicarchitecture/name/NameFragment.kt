package ru.otus.basicarchitecture.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.address.AddressFragment
import ru.otus.basicarchitecture.databinding.FragmentNameBinding
import javax.inject.Inject

@AndroidEntryPoint
class NameFragment: Fragment() {

    private lateinit var binding: FragmentNameBinding

    @Inject
    lateinit var viewModel: NameFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) {
            it?.also {
                AlertDialog.Builder(requireActivity())
                    .setMessage(it)
                    .setPositiveButton("Ok")  {_, _ ->}
                    .show()
            }
            binding.buttonNext.isEnabled = viewModel.error.value == null
        }

        binding.name.addTextChangedListener { viewModel.name.value = it.toString() }
        binding.surname.addTextChangedListener { viewModel.surname.value = it.toString() }
        binding.date.addTextChangedListener(DateMask(viewModel))

        binding.buttonNext.setOnClickListener(){
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