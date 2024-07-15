package ru.otus.basicarchitecture.Ui.Fragment1

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.Core.Model.ViewModelFactory
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.Fragment1Binding
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class Fragment1: Fragment() {
    private lateinit var binding: Fragment1Binding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private  val MESSAGE_TOAST_INCORRECT_AGE ="Возраст пользователя не может быть меньше 18"

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[Fragment1ViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).component
            .fragment1SubComponent()
            .build()
            .inject(this)
    }

    override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun setupView() {
        binding.birthInputLayout.apply {
            startIconDrawable = ContextCompat.getDrawable(context, R.drawable.calendar)
            startIconContentDescription = context.getString(R.string.DateOfBirthHint)
        }
        binding.birthEditText.apply {
            inputType = InputType.TYPE_NULL
            //    isEnabled = false
            isCursorVisible = false
            keyListener = null

            onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showDatePicker(view.context)
                }else{
                    viewModel.validateEmptyValue()
                }
                this.clearFocus()
            }

        }
        setFocusListener()

        binding.nextButton.let { button ->
            viewModel.enabledButtonLiveData.observe(viewLifecycleOwner) {
                button.isEnabled = it
            }
        }

        binding.nextButton.setOnClickListener {
            viewModel.setData(
                binding.firstNameEditText.text.toString(),
                binding.surNameEditText.text.toString(),
                binding.birthEditText.text.toString()
            ) { openSecondFragment() }
        }
    }


    private fun setFocusListener(){
        val lossFocus = View.OnFocusChangeListener{_,hasFocus ->
            if(!hasFocus){
                viewModel.validateEmptyValue()
            }
        }
        binding.firstNameEditText.onFocusChangeListener =  lossFocus
        binding.surNameEditText.onFocusChangeListener = lossFocus

    }

    private fun openSecondFragment() {
        val fragment = Fragment2()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    // возможно надо перенести внутрь ViewModel
    private fun showDatePicker(context: Context) {
        val currentDate = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedData =
                    String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day)
                viewModel.validData(day, month, year,
                    { showToast(MESSAGE_TOAST_INCORRECT_AGE, context) }
                )
                binding.birthEditText.setText(selectedData)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }




    private fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}