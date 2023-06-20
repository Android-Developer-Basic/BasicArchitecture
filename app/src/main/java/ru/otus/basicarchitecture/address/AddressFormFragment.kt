package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.interests.InterestsFragment
import ru.otus.basicarchitecture.R
import javax.inject.Inject

@AndroidEntryPoint
class AddressFormFragment : Fragment(R.layout.fragment_address_form) {
    @Inject
    lateinit var viewModelFactory: AddressFormViewModel.Factory
    private val viewModel: AddressFormViewModel by viewModels { viewModelFactory }
    private lateinit var addressView: TextInputLayout
    private lateinit var addressesView: RecyclerView
    private val addressesAdapter = AddressesAdapter().apply {
        onAdressClick = {
            addressView.editText?.setText(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressView = view.findViewById(R.id.address)
        addressView.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        addressesView = view.findViewById(R.id.recyclerView)
        addressesView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        addressesView.adapter = addressesAdapter

        view.findViewById<Button>(R.id.next).setOnClickListener { next() }

        viewModel.addresses.observe(viewLifecycleOwner) {
            addressesAdapter.addresses = it
        }
    }

    private fun next() {
        viewModel.save(
            addressView.editText!!.text.toString()
        )
        parentFragmentManager.beginTransaction()
            .replace(R.id.content, InterestsFragment::class.java, null)
            .commit()
    }
}