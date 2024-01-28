package ru.otus.basicarchitecture.presentation.SecondScreen

import android.content.Context
import android.os.Bundle
import android.os.Message
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
import ru.otus.basicarchitecture.presentation.FirstScreen.FirstScreenFragment
import ru.otus.basicarchitecture.presentation.FirstScreen.FirstScreenViewModel
import ru.otus.basicarchitecture.presentation.ThirdScreen.ThirdScreenFragment
import ru.otus.basicarchitecture.presentation.ViewModelFactory
import javax.inject.Inject

class SecondScreenFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SecondScreenViewModel::class.java]
    }



    private var countryEditText: EditText? = null
    private var cityEditText: EditText? = null
    private var addressEditText: EditText? = null


    private var countryInputLayout: TextInputLayout? = null
    private var cityInputLayout: TextInputLayout? = null
    private var addressInputLayout: TextInputLayout? = null

    private var nextButton: Button? = null



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
            .secondScreenSubComponent()
            .build()
            .inject(this)
        setupView(view)
    }

    private fun setupView(view: View){
        with(view) {
            nextButton = findViewById(R.id.nextButton)

            countryInputLayout = findViewById(R.id.firstInputLayout)
            countryEditText = findViewById(R.id.firstEditText)

            cityInputLayout = findViewById(R.id.secondInputLayout)
            cityEditText = findViewById(R.id.secondEditText)

            addressInputLayout = findViewById(R.id.thirdInputLayout)
            addressEditText = findViewById(R.id.thirdEditText)


        }

        nextButton?.let{ button ->
            setListeners()
            viewModel.enabledButtonLiveData.observe(viewLifecycleOwner){
                button.isEnabled = it
            }
        }
        countryInputLayout?.apply {
            hint = context.getString(R.string.country)
        }
        cityInputLayout?.apply {
            hint = context.getString(R.string.city)
        }
        addressEditText?.apply {
            hint = context.getString(R.string.address)
        }
    }

    private fun setListeners() {
        nextButton?.setOnClickListener {
            viewModel.setData(
                countryEditText?.text.toString(),
                cityEditText?.text.toString(),
                addressEditText?.text.toString(),
                {showToast(MESSAGE_TOAST_INVALIDATE_TEXT, it.context)},
                {openFragment()}
            )
        }
    }

    private fun showToast(message: String, context: Context){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun openFragment(){
        val fragment = ThirdScreenFragment.instance()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object{
        private val MESSAGE_TOAST_INVALIDATE_TEXT = "есть незаполненные поля"
        fun instance():SecondScreenFragment{
            return SecondScreenFragment()
        }
    }
}