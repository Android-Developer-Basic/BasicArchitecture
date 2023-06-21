package ru.otus.basicarchitecture.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.StateFlags
import ru.otus.basicarchitecture.app.App
import ru.otus.basicarchitecture.databinding.Fragment3Binding
import ru.otus.basicarchitecture.presentation.HobbyListAdapter
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityViewModel
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment3VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment3ViewModel
import javax.inject.Inject


class Fragment3 : Fragment() {
    private lateinit var binding: Fragment3Binding
    @Inject
    lateinit var factory: Fragment3VMFactory
    private lateinit var viewModel: Fragment3ViewModel
    @Inject
    lateinit var activityVMFactory: ActivityVMFactory
    private lateinit var activityViewModel: ActivityViewModel
    private val hobbySet = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.injectFragment3(this)
        viewModel = ViewModelProvider(this, factory)[Fragment3ViewModel::class.java]
        activityViewModel =
            ViewModelProvider(requireActivity(), activityVMFactory)[ActivityViewModel::class.java]
        val adapter = HobbyListAdapter()
        binding.hobbyListRV.adapter = adapter
        adapter.onItemClick = {
            viewModel.changeEnableState(it)
        }

        viewModel.data.observe(viewLifecycleOwner){
            adapter.setItemList(it)
            hobbySet.clear()
            for(i in it){
                if(i.enabled) hobbySet.add(i.hobby)
            }
        }
        binding.f3Button.setOnClickListener { saveData() }
    }

    private fun saveData(){
        viewModel.saveData(hobbySet.toSet())
        activityViewModel.setFlag(StateFlags.FRAGMENT_4)
    }
}