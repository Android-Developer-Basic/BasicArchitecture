package ru.otus.basicarchitecture.presentation.FourthScreen

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
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.FirstScreen.FirstScreenViewModel
import ru.otus.basicarchitecture.presentation.ViewModelFactory
import javax.inject.Inject

class FourthScreenFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[FourthScreenViewModel::class.java]
    }

    private var recyclerView: RecyclerView? = null

    private var name: TextView? = null
    private var surname: TextView? = null
    private var dateOfBirth: TextView? = null
    private var address: TextView? = null

    private var adapter = AdapterFourthScreen()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fourth_screen,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).component
            .fourthScreenSubComponent()
            .build()
            .inject(this)

        setupView(view)

    }

    private fun setupView(view: View){

        with(view){

            recyclerView = findViewById(R.id.recyclerView4)
            name = findViewById(R.id.Name)
            surname = findViewById(R.id.Surname)
            dateOfBirth = findViewById(R.id.DateOfBirth)
            address =  findViewById(R.id.Address)

        }


        name?.text = viewModel.modelFourthScreen.name
        surname?.text = viewModel.modelFourthScreen.surName
        dateOfBirth?.text = viewModel.modelFourthScreen.birthDate
        address?.text = viewModel.modelFourthScreen.fullAddress
        adapter.listInterestsInfo = viewModel.modelFourthScreen.interests

        val spanCount = 2
        val orientation = StaggeredGridLayoutManager.VERTICAL
        val layoutManager = StaggeredGridLayoutManager(spanCount, orientation)

        recyclerView?.layoutManager = layoutManager

        recyclerView?.adapter = adapter

    }

    companion object{
        fun instance() : FourthScreenFragment{
            return FourthScreenFragment()
        }
    }
}