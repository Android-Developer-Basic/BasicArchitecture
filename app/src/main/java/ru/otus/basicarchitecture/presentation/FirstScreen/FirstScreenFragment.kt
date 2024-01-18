package ru.otus.basicarchitecture.presentation.FirstScreen

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.otus.basicarchitecture.R

class FirstScreenFragment : Fragment() {


    private var screen = UNKNOWN_SCREEN

    private var nextButton: Button? = null

    private var nameEditText: EditText? = null
    private var surNameEditText: EditText? = null
    private var birthDateEditText: EditText? = null


    private var nameInputLayout: TextInputLayout? = null
    private var surNameInputLayout: TextInputLayout? = null
    private var birthDateInputLayout: TextInputLayout? = null

    private lateinit var viewModel: FirstScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_and_second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FirstScreenViewModel::class.java]
        setupView(view)
        nameEditText.apply {
        }


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
        nameInputLayout?.apply {
            hint = R.string.hintName.toString()
        }
        surNameInputLayout?.apply {
            hint = R.string.surNameHint.toString()
        }
        birthDateInputLayout?.apply {
            hint = R.string.DateOfBirthHint.toString()
            startIconDrawable = ContextCompat.getDrawable(context, R.drawable.calendar)
            startIconContentDescription = R.string.DateOfBirthHint.toString()
        }
        birthDateEditText?.apply {
            inputType = InputType.TYPE_DATETIME_VARIATION_DATE
        }
    }

    private fun parseParam(){
        val args = requireArguments()
        if(!args.containsKey(KEY_SCREEN_MODE)){
            throw Exception(Exception_message)
        }
        screen = requireArguments().getString(SCREEN_MODE) ?: throw Exception(Exception_message)
    }


    companion object{
        private const val Exception_message = "First fragment: unknown screen mode"
        private const val UNKNOWN_SCREEN = "unknown_screen"
        private const val KEY_SCREEN_MODE = "key_screen_mode"
        private const val SCREEN_MODE = "first_screen"
        fun instance() : FirstScreenFragment {
            return FirstScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_SCREEN_MODE, SCREEN_MODE)
                }
            }
        }
    }
}