package ru.otus.basicarchitecture.ui.address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R


@AndroidEntryPoint
class AddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddressFragment()
    }

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressTextInput = view.findViewById<TextInputLayout>(R.id.addressTextInput)
        val navController = findNavController()

        view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView).apply {
            threshold = 4
            addTextChangedListener(object :
                TextWatcher {
                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (s.length > 3) {
                        viewModel.getDataNotice(s.toString())
                        val adapter = activity?.let {
                            ArrayAdapter(
                                it.baseContext,
                                android.R.layout.simple_dropdown_item_1line,
                                viewModel.addressDataArray
                            )
                        }
                        setAdapter(adapter)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {}
            });
        }

        view.findViewById<Button>(R.id.toInterests).setOnClickListener {
            viewModel.setAddress(addressTextInput.editText?.text.toString())

            navController.navigate(R.id.action_to_interestsFragment)
        }
    }
}