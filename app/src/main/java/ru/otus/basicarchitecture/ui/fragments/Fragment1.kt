package ru.otus.basicarchitecture.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.model.InputData
import ru.otus.basicarchitecture.ui.MainActivity
import ru.otus.basicarchitecture.viewmodels.Fragment1ViewModel
import ru.otus.basicarchitecture.viewmodels.MainViewModel
import ru.otus.basicarchitecture.viewmodels.ViewState

@AndroidEntryPoint
class Fragment1 : Fragment() {

    private val _state = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _state

    private val viewModel: Fragment1ViewModel by viewModels()

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var birthDateEditText: EditText
    private lateinit var nextBtn: Button

    private lateinit var toast: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameEditText = view.findViewById(R.id.name)
        lastNameEditText = view.findViewById(R.id.surname)
        birthDateEditText = view.findViewById(R.id.birthdate)
        nextBtn = view.findViewById(R.id.fragment1Btn)
        toast = view.findViewById(R.id.toast)

        birthDateEditText.setMask("99.99.9999")



        nextBtn.setOnClickListener {
            // Сохраняем данные из EditText в LiveData
            viewModel.firstName.value = firstNameEditText.text.toString()
            viewModel.lastName.value = lastNameEditText.text.toString()
            viewModel.birthDate.value = birthDateEditText.text.toString()

            // Выполняем валидацию и сохраняем данные в кэш
            if (viewModel.validateInput()) {
                viewModel.saveData()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Fragment2())
                    .addToBackStack(null)
                    .commit()

//                Toast.makeText(context, viewModel.firstName.value +
//                        viewModel.lastName.value + viewModel.birthDate.value, Toast.LENGTH_SHORT).show()

                // Можно добавить уведомление об успешном сохранении
            } else {
                Snackbar.make(toast, "Валидация не пройдена", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}