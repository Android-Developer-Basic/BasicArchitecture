package ru.otus.basicarchitecture.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : Fragment() {

    @Inject
    lateinit var viewModel: AddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialAutoCompleteTextView>(R.id.address)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.loadSuggestions(s.toString())
                    viewModel.address.value = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

        viewModel.addressSuggestions.observe(viewLifecycleOwner) { suggestions ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                suggestions
            )
            val address = view.findViewById<MaterialAutoCompleteTextView>(R.id.address)
            address.setAdapter(adapter)

            if (suggestions.isNotEmpty()) {
                address.showDropDown()
            }
        }

        view.findViewById<Button>(R.id.nextToTags).setOnClickListener {
            viewModel.saveToCash()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TagsFragment())
                .addToBackStack(null)
                .commit()

        }
    }
}