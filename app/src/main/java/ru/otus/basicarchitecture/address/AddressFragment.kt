package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
@AndroidEntryPoint
class AddressFragment : Fragment(R.layout.fragment_adress), View.OnFocusChangeListener {
    private val addressViewModelInstance : AddressFragmentModel by viewModels()
    private lateinit var nextButton: Button
    private lateinit var countryField: TextInputEditText
    private lateinit var cityField: TextInputEditText
    private lateinit var addressField: TextInputEditText
    private lateinit var addressHintField: MaterialAutoCompleteTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryField = view.findViewById(R.id.countryTextEdit)
        cityField = view.findViewById(R.id.cityTextEdit)
        addressField  = view.findViewById(R.id.addressTextEdit)
        nextButton = view.findViewById(R.id.addressNextButton)

        addressViewModelInstance.addressFragmentState.observe(viewLifecycleOwner) {state ->
            countryField.setTextKeepState(state.country)
            cityField.setTextKeepState(state.city)
            addressField.setTextKeepState(state.address)
        }

        countryField.onFocusChangeListener = this
        cityField.onFocusChangeListener = this
        addressField.onFocusChangeListener = this

        nextButton.setOnClickListener{ goToNextScreen() }
        nextButton.isEnabled = addressViewModelInstance.isButtonEnabled()

        addressHintField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Handle text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Handle text changes
            }

            override fun afterTextChanged(editable: Editable?) {
                /*addressViewModelInstance.getAddressSuggestions(editable.toString()).collectLatest { suggestions ->
                    // Update the adapter with the new suggestions
                }*/
            }
        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            addressViewModelInstance.checkTextFields(
                countryField.text,
                cityField.text,
                addressField.text,
            )
            nextButton.isEnabled = addressViewModelInstance.isButtonEnabled()
        }
    }

    private fun goToNextScreen() {
        addressViewModelInstance.onNextButtonClicked()
        findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
    }
}
