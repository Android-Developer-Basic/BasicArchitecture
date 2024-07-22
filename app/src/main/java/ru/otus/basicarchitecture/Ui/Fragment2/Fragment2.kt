package ru.otus.basicarchitecture.Ui.Fragment1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.Core.Model.DTO.Suggestion
import ru.otus.basicarchitecture.Core.Model.ViewModelFactory
import ru.otus.basicarchitecture.Core.Utils.toEditable
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.Ui.Fragment2.Fragment2Adapter
import ru.otus.basicarchitecture.Ui.Fragment2.OnItemClickListener
import ru.otus.basicarchitecture.Ui.Fragment3.Fragment3
import ru.otus.basicarchitecture.databinding.Fragment2Binding
import javax.inject.Inject

class Fragment2: Fragment() {
    private lateinit var binding: Fragment2Binding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var adapter: Fragment2Adapter

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

        viewModel.errorService.setContext(requireContext())
        setupView()
    }

    private fun setupView() {
        binding.nextButton.let { button ->
            setListeners()
            viewModel.enabledButtonLiveData.observe(viewLifecycleOwner) {
                button.isEnabled = it
            }
        }


        binding.addressField.doAfterTextChanged {
            val context = requireContext()
            viewModel.loadSuggestions(input = it.toString())
        }

        viewModel.suggestionsLiveData.observe(viewLifecycleOwner) {
            Log.d("Server", "Size=" + it.size.toString())
            adapter.addList(it)
        }

        viewModel.showProgress.observe(viewLifecycleOwner) {
            if(it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        configureRecycler()
        setFocusListener()
    }

    fun configureRecycler() {
        adapter = Fragment2Adapter(listener = object : OnItemClickListener {
            override fun onItemClick(item: Suggestion?) {
                item?.value?.let { viewModel.setQuery(it) }
                binding.addressField.text = item?.value?.toEditable()
            }
        })
        binding.recyclerAddress.adapter = adapter
    }

    private fun setListeners() {
        binding.nextButton.setOnClickListener {
            viewModel.setData(
                binding.addressField.text.toString()
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

        binding.addressField.onFocusChangeListener = lossFocus
    }
}