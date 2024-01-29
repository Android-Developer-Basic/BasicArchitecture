package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.data.Address
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
    private lateinit var recycler: RecyclerView

    private lateinit var textWatcher: TextWatcher

    private val recyclerAdapter = AddressAdapter {
        addressViewModelInstance.setAddress(it)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressField = view.findViewById(R.id.addressTextEdit)
        recycler = view.findViewById(R.id.recycler)
        addressHintField = view.findViewById(R.id.addressAutoCompleteTextEdit)
        nextButton = view.findViewById(R.id.addressNextButton)
        nextButton.isEnabled = false

        textWatcher = addressField.addTextChangedListener {
            addressViewModelInstance.searchAddress(it.toString())
        }

        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
        }



        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addressViewModelInstance.viewState.collect { it ->
                    addressField.removeTextChangedListener(textWatcher)
                    addressField.setTextKeepState(it.address)
                    addressField.addTextChangedListener(textWatcher)

                    recyclerAdapter.submitList(it.addressList)

                    val adapter = ArrayAdapter<Address>(
                        requireContext(), android.R.layout.simple_dropdown_item_1line, emptyList()
                    )
                    addressHintField.setAdapter(adapter)
                    addressHintField.addTextChangedListener {
                        addressViewModelInstance.searchAddress(it.toString())
                    }

                    nextButton.isEnabled = it.accessNextButton
                }
            }
        }

        recycler.adapter = recyclerAdapter
    }
}
