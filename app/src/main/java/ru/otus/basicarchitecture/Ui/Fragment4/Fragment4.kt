package ru.otus.basicarchitecture.Ui.Fragment4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.Core.Model.ViewModelFactory
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.Fragment3Binding
import ru.otus.basicarchitecture.databinding.Fragment4Binding

import javax.inject.Inject

class Fragment4: Fragment() {
    private lateinit var binding: Fragment4Binding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[Fragment4ViewModel::class.java]
    }

    private var adapter = Fragment4Adapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = Fragment4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).component
            .fragment4SubComponent()
            .build()
            .inject(this)
   }

    override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun setupView(){
        binding.Name.text = viewModel.fragment4Model.name
        binding.Surname.text = viewModel.fragment4Model.surName
        binding.DateOfBirth.text = viewModel.fragment4Model.birthDate
        binding.Address.text = viewModel.fragment4Model.fullAddress
        adapter.listInterestsInfo = viewModel.fragment4Model.interests

        val spanCount = 2
        val orientation = StaggeredGridLayoutManager.VERTICAL
        val layoutManager = StaggeredGridLayoutManager(spanCount, orientation)

        binding.recyclerView4.layoutManager = layoutManager
        binding.recyclerView4.adapter = adapter
    }
}