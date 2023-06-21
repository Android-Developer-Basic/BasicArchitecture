package ru.otus.basicarchitecture.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.ADDRESS
import ru.otus.basicarchitecture.BIRTH_DATE
import ru.otus.basicarchitecture.INTERESTS
import ru.otus.basicarchitecture.NAME
import ru.otus.basicarchitecture.app.App
import ru.otus.basicarchitecture.databinding.Fragment4Binding
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment4VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment4ViewModel
import javax.inject.Inject


class Fragment4 : Fragment() {
    private lateinit var binding:Fragment4Binding
    @Inject
    lateinit var factory: Fragment4VMFactory
    lateinit var viewModel: Fragment4ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment4Binding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.injectFragment4(this)
        viewModel = ViewModelProvider(this, factory)[Fragment4ViewModel::class.java]
        viewModel.data.observe(viewLifecycleOwner){
            binding.apply {
                f4Name.text = NAME + it.name + " " + it.surname
                f4Date.text = BIRTH_DATE + it.birthDate
                f4Address.text = ADDRESS + "${it.country}, ${it.city}, ${it.address}"
                val interests = it.hobby.joinToString(", ")
                f4Interests.text = INTERESTS + interests
            }
        }
    }


}