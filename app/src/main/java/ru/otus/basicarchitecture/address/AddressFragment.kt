package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class AddressFragment : Fragment(R.layout.fragment_adress) {
    private val addressViewModelInstance: AddressFragmentModel by viewModels()
    private lateinit var nextButton: Button
    private lateinit var addressField: TextInputEditText
    private lateinit var addressHintField: MaterialAutoCompleteTextView

    private lateinit var textWatcher: TextWatcher

    private val adapter = AddressAdapter {
        addressViewModelInstance.setAddress(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressField = view.findViewById(R.id.addressTextEdit)
        addressHintField = view.findViewById(R.id.addressAutoCompleteTextEdit)
        nextButton = view.findViewById(R.id.addressNextButton)

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addressViewModelInstance.viewState.collect {
                    addressField.removeTextChangedListener(textWatcher)
                    addressField.setTextKeepState(it.address)
                    addressField.addTextChangedListener(textWatcher)

                    addressHintField.hint = it.addressList.toString()
                }
            }
        }

        addressViewModelInstance.addressFragmentState.observe(viewLifecycleOwner) { state ->
            addressField.setTextKeepState(state.address)
        }


        nextButton.setOnClickListener { goToNextScreen() }
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

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
    }
}
