package ru.otus.basicarchitecture.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import ru.otus.basicarchitecture.presentation.viewModel.AddressViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private lateinit var binding: FragmentAddressBinding

    @Inject
    lateinit var viewModel: AddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.load(s.toString())
            }
        })

        viewModel.addressSuggestions.observe(viewLifecycleOwner) { suggestions ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                suggestions
            )
            binding.address.setAdapter(adapter)
            if (suggestions.isNotEmpty()) {
                binding.address.showDropDown()
            }
        }

        binding.buttonNextOnAddress.setOnClickListener {
            viewModel.save(binding.address.text.toString())

            parentFragmentManager.beginTransaction()
                .replace(R.id.container, InterestsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}