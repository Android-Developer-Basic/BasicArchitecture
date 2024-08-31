package ru.otus.basicarchitecture.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodel.AddressViewModel

@AndroidEntryPoint
class AddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddressFragment()
    }

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    private lateinit var addressEditText: AutoCompleteTextView
    private lateinit var nextBtn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressEditText = view.findViewById(R.id.address)
        nextBtn = view.findViewById(R.id.button_to_interest)


        viewModel.addressSuggestions.observe(viewLifecycleOwner) { suggestions ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                suggestions
            )
            addressEditText.setAdapter(adapter)
            if (suggestions.isNotEmpty()) {
                addressEditText.showDropDown()
            }
        }

        addressEditText.addTextChangedListener {
            if(it != null && it.length > 5) {
                viewModel.updateAddress(it.toString())
                viewModel.fetchAddressSuggestions(it.toString())
            }
        }

        nextBtn.setOnClickListener {
            if (nextBtn.isEnabled) {
                viewModel.saveData()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag, InterestFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(context, "Валидация не пройдена", Toast.LENGTH_SHORT).show()
            }
        }
    }
}