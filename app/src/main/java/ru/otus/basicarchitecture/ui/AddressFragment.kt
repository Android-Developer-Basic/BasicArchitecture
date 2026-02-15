package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressLayout = view.findViewById<TextInputLayout>(R.id.address_layout)

        val addressTextView =
            view.findViewById<AppCompatAutoCompleteTextView>(R.id.address_text_view)

        val nextBtn = view.findViewById<Button>(R.id.next_btn)

        addressTextView.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                addressLayout.hint = ""
            } else {
                addressLayout.hint = "Address"
            }
        }

        lifecycleScope.launch {
            viewModel.hints.collect { hintsList ->
                val suggestions = hintsList.map { it.value }
                val autoCompleteAdapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        suggestions
                    )
                addressTextView.setAdapter(autoCompleteAdapter)
            }
        }


        addressTextView.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val input = s.toString()
                    viewModel.getHints(input)
                }

                override fun afterTextChanged(s: Editable?) {}

            }
        )

        nextBtn.setOnClickListener {
            viewModel.updateUserAddress(
                address = addressTextView.text.toString()
            )

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    InterestsFragment()
                )
                .addToBackStack("")
                .commit()
        }
    }

}