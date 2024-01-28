package ru.otus.basicarchitecture.presentation.FirstScreen

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.SecondScreen.SecondScreenFragment
import ru.otus.basicarchitecture.presentation.ViewModelFactory
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class FirstScreenFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FirstScreenViewModel::class.java]
    }


    private var nextButton: Button? = null

    private var nameEditText: EditText? = null
    private var surNameEditText: EditText? = null
    private var birthDateEditText: EditText? = null


    private var nameInputLayout: TextInputLayout? = null
    private var surNameInputLayout: TextInputLayout? = null
    private var birthDateInputLayout: TextInputLayout? = null


    /*    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            parseParam()
        }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_and_second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).component
            .firstScreenSubComponent()
            .build()
            .inject(this)

        setupView(view)

    }


    private fun setupView(view: View) {
        with(view) {
            nextButton = findViewById(R.id.nextButton)

            nameInputLayout = findViewById(R.id.firstInputLayout)
            nameEditText = findViewById(R.id.firstEditText)

            surNameInputLayout = findViewById(R.id.secondInputLayout)
            surNameEditText = findViewById(R.id.secondEditText)

            birthDateInputLayout = findViewById(R.id.thirdInputLayout)
            birthDateEditText = findViewById(R.id.thirdEditText)


        }

        nextButton?.let { button ->
            setListeners()
            viewModel.enabledButtonLiveData.observe(viewLifecycleOwner) {
                button.isEnabled = it
            }
        }
        nameInputLayout?.apply {
            hint = context.getString(R.string.hintName)
        }
        surNameInputLayout?.apply {
            hint = context.getString(R.string.surNameHint)
        }

        birthDateInputLayout?.apply {
            hint = context.getString(R.string.DateOfBirthHint)
            startIconDrawable = ContextCompat.getDrawable(context, R.drawable.calendar)
            startIconContentDescription = context.getString(R.string.DateOfBirthHint)
        }
        birthDateEditText?.apply {
            inputType = InputType.TYPE_NULL
            //    isEnabled = false
            isCursorVisible = false
            keyListener = null

            onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showDatePicker(view.context)
                }
                this.clearFocus()
            }

        }


    }


    private fun setListeners() {
        nextButton?.setOnClickListener {
            viewModel.setData(
                nameEditText?.text.toString(),
                surNameEditText?.text.toString(),
                birthDateEditText?.text.toString(),
                { showToast(MESSAGE_TOAST_INVALIDATE_TEXT, it.context) },
                { openSecondFragment() }
            )
        }
    }

    private fun openSecondFragment() {
        val fragment = SecondScreenFragment.instance()
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
                birthDateEditText?.setText(selectedData)
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


    companion object {
        private const val MESSAGE_TOAST_INVALIDATE_TEXT = "Есть незаполненные поля"
        private const val MESSAGE_TOAST_INCORRECT_AGE ="Возраст пользователя не может быть меньше 18"


        fun instance(): FirstScreenFragment {
            return FirstScreenFragment()
        }
    }
}