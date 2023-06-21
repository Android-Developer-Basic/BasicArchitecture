package ru.otus.basicarchitecture.presentation.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.StateFlags
import ru.otus.basicarchitecture.app.App
import ru.otus.basicarchitecture.databinding.Fragment1Binding
import ru.otus.basicarchitecture.domain.models.UserBirthDate
import ru.otus.basicarchitecture.domain.models.UserName
import ru.otus.basicarchitecture.domain.models.UserSurname
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityViewModel
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment1VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment1ViewModel



import javax.inject.Inject


class Fragment1 : Fragment() {



    @Inject
    lateinit var factory:Fragment1VMFactory
    private lateinit var viewModel:Fragment1ViewModel
    @Inject
    lateinit var activityVMFactory: ActivityVMFactory
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var binding: Fragment1Binding
    private var validation = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1Binding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.injectFragment1(this)
        activityViewModel = ViewModelProvider( requireActivity(), activityVMFactory)[ActivityViewModel::class.java]
        viewModel = ViewModelProvider(this, factory)[Fragment1ViewModel::class.java]
        viewModel.outputData.observe(viewLifecycleOwner){user->
            binding.apply {
                f1NameField.setText(user.name)
                f1SurnameField.setText(user.surname)
                f1BirthDate.text = user.birthDate

            }
        }

        binding.f1BirthDate.setOnClickListener {

            viewModel.pickDate(requireContext(), it as TextView)

        }

        binding.f1Button.setOnClickListener {
            validation = viewModel.validation()
            if(!validation){
                Toast.makeText(requireContext(), "Validation false", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.apply {
                val name = f1NameField.text.toString()
                val surname = f1SurnameField.text.toString()
                val date = f1BirthDate.text.toString()
                viewModel.inputData.value = listOf(
                    UserName(name),
                    UserSurname(surname),
                    UserBirthDate(date)
                )
            }
            viewModel.saveData()
            activityViewModel.setFlag(StateFlags.FRAGMENT_2)

        }
    }


}