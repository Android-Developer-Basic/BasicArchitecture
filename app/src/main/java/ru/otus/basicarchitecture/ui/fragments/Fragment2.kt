package ru.otus.basicarchitecture.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodels.Fragment2ViewModel

@AndroidEntryPoint
class Fragment2 : Fragment() {
    private val viewModel: Fragment2ViewModel by viewModels()

    private lateinit var addressEditText: AutoCompleteTextView
    private lateinit var nextBtn: Button

    private lateinit var toast: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment2, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressEditText = view.findViewById(R.id.address)
        nextBtn = view.findViewById(R.id.fragment2Btn)
        toast = view.findViewById(R.id.toast)

        viewModel.isFormValid.observe(viewLifecycleOwner) { isValid ->
            nextBtn.isEnabled = isValid
        }

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
            viewModel.address.value = it.toString()
            viewModel.fetchAddressSuggestions(it.toString())
        }

        nextBtn.setOnClickListener {
            viewModel.address.value = addressEditText.text.toString()
            if (nextBtn.isEnabled) {
                viewModel.saveData()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Fragment3())
                    .addToBackStack(null)
                    .commit()
            } else {
                Snackbar.make(toast, "Валидация не пройдена", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
