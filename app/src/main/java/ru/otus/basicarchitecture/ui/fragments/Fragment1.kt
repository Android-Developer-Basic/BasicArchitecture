package ru.otus.basicarchitecture.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.model.InputData
import ru.otus.basicarchitecture.ui.MainActivity
import ru.otus.basicarchitecture.viewmodels.Fragment1ViewModel
import ru.otus.basicarchitecture.viewmodels.MainViewModel
import ru.otus.basicarchitecture.viewmodels.ViewState

class Fragment1 : Fragment() {

    private val _state = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _state

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        val editText = view.findViewById<TextInputEditText>(R.id.name)
        val button = view.findViewById<Button>(R.id.fragment1Btn)

        button.setOnClickListener {
            val inputData = InputData(editText.text.toString())
            viewModel.setInputData(inputData)

            // Переход ко второму фрагменту
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Fragment2())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

    fun onNextButtonClicked() {

    }
}