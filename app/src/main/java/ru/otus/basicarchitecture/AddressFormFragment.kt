package ru.otus.basicarchitecture

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressFormFragment : Fragment(R.layout.fragment_address_form) {
    @Inject
    lateinit var viewModel: AddressFormViewModel

    private lateinit var countryView: TextInputLayout
    private lateinit var cityView: TextInputLayout
    private lateinit var addressView: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryView = view.findViewById(R.id.country)
        cityView = view.findViewById(R.id.city)
        addressView = view.findViewById(R.id.address)

        view.findViewById<Button>(R.id.next).setOnClickListener { next() }
    }

    private fun next() {
        viewModel.save(
            countryView.editText!!.text.toString(),
            cityView.editText!!.text.toString(),
            addressView.editText!!.text.toString()
        )
        parentFragmentManager.beginTransaction()
            .replace(R.id.content, InterestsFragment::class.java, null)
            .commit()
    }
}