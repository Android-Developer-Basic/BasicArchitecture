package ru.otus.basicarchitecture.personal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.address.AddressFormFragment
import javax.inject.Inject

@AndroidEntryPoint
class PersonalInfoFormFragment : Fragment(R.layout.fragment_personal_info_form) {
    private lateinit var nameView: TextInputLayout
    private lateinit var surnameView: TextInputLayout
    private lateinit var dateView: TextInputLayout

    @Inject
    lateinit var viewModel: PersonalInfoFormViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameView = view.findViewById(R.id.name)
        surnameView = view.findViewById(R.id.surname)
        dateView = view.findViewById(R.id.date)
        dateView.editText?.addTextChangedListener(DateFormatterTextWatcher())

        view.findViewById<Button>(R.id.next).setOnClickListener { next() }

        viewModel.error.observe(viewLifecycleOwner) {
            it?.also {
                AlertDialog.Builder(requireActivity())
                    .setMessage(it)
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
                viewModel.clearError()
            }
        }
    }

    private fun next() {
        if (viewModel.validateBirthdate(dateView.editText!!.text.toString())) {
            viewModel.save(
                nameView.editText!!.text.toString(),
                surnameView.editText!!.text.toString(),
                dateView.editText!!.text.toString()
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.content, AddressFormFragment::class.java, null)
                .commit()
        }
    }
}