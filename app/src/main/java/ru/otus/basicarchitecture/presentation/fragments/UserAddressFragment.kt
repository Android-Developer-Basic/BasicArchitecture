package ru.otus.basicarchitecture.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.StateFlags
import ru.otus.basicarchitecture.app.App
import ru.otus.basicarchitecture.databinding.FragmentUserAddressBinding
import ru.otus.basicarchitecture.domain.models.Suggestions
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityViewModel
import ru.otus.basicarchitecture.presentation.viewmodels.UserAddressFragmentVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.UserAddressFragmentViewModel
import javax.inject.Inject


class UserAddressFragment : Fragment() {
    private val binding by lazy { FragmentUserAddressBinding.inflate(layoutInflater) }

    @Inject
    lateinit var factory: UserAddressFragmentVMFactory
    private lateinit var viewModel: UserAddressFragmentViewModel

    @Inject
    lateinit var activityVMFactory: ActivityVMFactory
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.injectUserAddressFragment(this)
        viewModel = ViewModelProvider(this, factory)[UserAddressFragmentViewModel::class.java]
        activityViewModel =
            ViewModelProvider(requireActivity(), activityVMFactory)[ActivityViewModel::class.java]
        adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.editAddress.setAdapter(adapter)

        viewModel.outputData.observe(viewLifecycleOwner) { user ->
            binding.editAddress.setText(user.address)
        }

        binding.sendDataButton.setOnClickListener {

            val address = binding.editAddress.text.toString()
            viewModel.inputData.value = UserAddress(address = address)
            viewModel.saveData()
            activityViewModel.setFlag(StateFlags.FRAGMENT_3)

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sendData(
                    binding.editAddress.textChanges()
                )

                viewModel.state.collect { result ->
                    result?.let {
                        adapter.clear()
                        val suggestions = result.networkData as Suggestions
                        suggestions.suggestions.forEach { address ->
                            adapter.add(address.value)
                        }
                    }

                }


            }


        }

    }
}

