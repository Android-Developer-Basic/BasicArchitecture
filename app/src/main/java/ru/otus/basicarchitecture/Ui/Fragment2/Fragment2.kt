package ru.otus.basicarchitecture.Ui.Fragment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.Core.Model.ViewModelFactory
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.Ui.Fragment3.Fragment3
import ru.otus.basicarchitecture.databinding.Fragment2Binding
import javax.inject.Inject

class Fragment2: Fragment() {
    private lateinit var binding: Fragment2Binding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[Fragment2ViewModel::class.java]
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        setupView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).component
            .fragment2SubComponent()
            .build()
            .inject(this)
        setupView()
    }

    private fun setupView() {
        binding.nextButton.let { button ->
            setListeners()
            viewModel.enabledButtonLiveData.observe(viewLifecycleOwner) {
                button.isEnabled = it
            }
        }


        setFocusListener()
    }

    private fun setListeners() {
        binding.nextButton.setOnClickListener {
            viewModel.setData(
                binding.countryEditText.text.toString(),
                binding.cityEditText.text.toString(),
                binding.addressEditText.text.toString()
            ) { openFragment() }
        }
    }


    private fun openFragment() {
        val fragment = Fragment3()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setFocusListener() {
        val lossFocus = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.buttonEnabled()
        }
        binding.countryEditText.onFocusChangeListener = lossFocus
        binding.cityEditText.onFocusChangeListener = lossFocus
        binding.addressEditText.onFocusChangeListener = lossFocus
    }
}