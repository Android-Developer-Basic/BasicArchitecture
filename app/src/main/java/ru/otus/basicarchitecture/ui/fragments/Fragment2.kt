package ru.otus.basicarchitecture.ui.fragments

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodels.Fragment2ViewModel

@AndroidEntryPoint
class Fragment2 : Fragment() {
    private val viewModel: Fragment2ViewModel by viewModels()

    private lateinit var countryEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var addressEditText: EditText
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

        countryEditText = view.findViewById(R.id.country)
        cityEditText = view.findViewById(R.id.city)
        addressEditText = view.findViewById(R.id.address)
        nextBtn = view.findViewById(R.id.fragment2Btn)
        toast = view.findViewById(R.id.toast)

        viewModel.country.observe(viewLifecycleOwner) {
            if (it == null || it.isBlank()) {
                countryEditText.error = "Поле не должно быть пустым"
            } else {
                countryEditText.error = null
            }
        }

        viewModel.city.observe(viewLifecycleOwner) {
            if (it == null || it.isBlank()) {
                cityEditText.error = "Поле не должно быть пустым"
            } else {
                cityEditText.error = null
            }
        }

        viewModel.address.observe(viewLifecycleOwner) {
            if (it == null || it.isBlank()) {
                addressEditText.error = "Поле не должно быть пустым"
            } else {
                addressEditText.error = null
            }
        }

        viewModel.isFormValid.observe(viewLifecycleOwner) { isValid ->
            nextBtn.isEnabled = isValid
        }

        countryEditText.addTextChangedListener { viewModel.country.value = it.toString() }
        cityEditText.addTextChangedListener { viewModel.city.value = it.toString() }
        addressEditText.addTextChangedListener { viewModel.address.value = it.toString() }

        nextBtn.setOnClickListener {
            viewModel.country.value = countryEditText.text.toString()
            viewModel.city.value = cityEditText.text.toString()
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