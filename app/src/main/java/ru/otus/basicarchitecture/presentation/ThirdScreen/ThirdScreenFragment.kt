package ru.otus.basicarchitecture.presentation.ThirdScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.otus.basicarchitecture.App
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.FourthScreen.FourthScreenFragment
import ru.otus.basicarchitecture.presentation.SecondScreen.SecondScreenFragment
import ru.otus.basicarchitecture.presentation.ViewModelFactory
import javax.inject.Inject

class ThirdScreenFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapterCastom: ListInterestsAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ThirdScreenViewModel::class.java]
    }

    private var recyclerView: RecyclerView? = null

    private var button: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).component
            .thirdScreenSubComponent()
            .build()
            .inject(this)

        setupRV(view)

        viewModel.listInterestsLD.observe(viewLifecycleOwner) {
            adapterCastom.interestsList = it
        }
        setupClickListener()

        button = view.findViewById(R.id.nextButton)

        button?.setOnClickListener {
            viewModel.setData {
                openFourthFragment()
            }
        }
    }
    private fun openFourthFragment() {
        val fragment = FourthScreenFragment.instance()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRV(view: View) {
        adapterCastom = ListInterestsAdapter()
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView?.let {
            with(it) {
                val layoutManager = GridLayoutManager(view.context,3)
                this.layoutManager = layoutManager
                adapter = adapterCastom
                recycledViewPool.setMaxRecycledViews(ListInterestsAdapter.ENABLED_VIEW, 15)
                recycledViewPool.setMaxRecycledViews(ListInterestsAdapter.DISABLED_VIEW, 15)
            }
        }
    }

    private fun setupClickListener() {
        adapterCastom.onClickListener = {
            viewModel.changeEnabledState(it)
        }
    }

    companion object {
        fun instance(): ThirdScreenFragment {
            return ThirdScreenFragment()
        }
    }


}