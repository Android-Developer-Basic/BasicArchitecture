package ru.otus.basicarchitecture.Ui.Fragment3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.Core.Model.ViewModelFactory
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.Ui.Fragment4.Fragment4
import ru.otus.basicarchitecture.databinding.Fragment3Binding
import javax.inject.Inject

class Fragment3: Fragment() {
    private lateinit var binding: Fragment3Binding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: InterestsAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[Fragment3ViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = Fragment3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).component
            .fragment3SubComponent()
            .build()
            .inject(this)
    }

    override fun onResume() {
        super.onResume()
        view?.let { setupRV(it) }
        setupView()


    }

    fun setupView() {
        binding.nextButton.setOnClickListener {
            viewModel.setData {
                openFourthFragment()
            }
        }

        viewModel.listInterestsLD.observe(viewLifecycleOwner) {
            adapter.interestsList = it
        }
    }

    private fun openFourthFragment() {
        val fragment = Fragment4()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRV(view: View) {
        adapter = InterestsAdapter()
        val layoutManager = GridLayoutManager(view.context,3)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        adapter.onClickListener = {
            viewModel.changeEnabledState(it)
        }
    }
}