package ru.otus.basicarchitecture.personal_info

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.databinding.PersonalInfoFragmentBinding

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

    private var binding: PersonalInfoFragmentBinding? = null
    private inline fun withBinding(block: PersonalInfoFragmentBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: PersonalInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PersonalInfoFragmentBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            buttonMoveToFragmentAddress.setOnClickListener {
                Log.d("PersonalInfoFragment", "Next Fragment")
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {
                        personalInfoName.setTextKeepState(it.name)
                        personalInfoSurname.setTextKeepState(it.surname)
                        personalInfoDateOfBirth.setTextKeepState(it.dateOfBirth)
                        buttonMoveToFragmentAddress.isEnabled = it.nextEnabled
                    }
                }
            }

            personalInfoName.addTextChangedListener {
                viewModel.setName(it.toString())
            }

            personalInfoSurname.addTextChangedListener {
                viewModel.setSurname(it.toString())
            }

            personalInfoDateOfBirth.addTextChangedListener {
                viewModel.setDateOfBirth(it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}