package ru.otus.basicarchitecture.presentation.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.StateFlags
import ru.otus.basicarchitecture.app.App
import ru.otus.basicarchitecture.databinding.Fragment2Binding
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityViewModel
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment2VMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.Fragment2ViewModel

import javax.inject.Inject


class Fragment2 : Fragment() {
    private lateinit var binding: Fragment2Binding
    @Inject
    lateinit var factory: Fragment2VMFactory
    private lateinit var viewModel: Fragment2ViewModel
    @Inject
    lateinit var activityVMFactory: ActivityVMFactory
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment2Binding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.injectFragment2(this)
        activityViewModel =
            ViewModelProvider(requireActivity(), activityVMFactory)[ActivityViewModel::class.java]
        viewModel = ViewModelProvider(this, factory)[Fragment2ViewModel::class.java]
        viewModel.outputData.observe(viewLifecycleOwner) { user ->
            binding.apply {
                f2CountryField.setText(user.country)
                f2CityField.setText(user.city)
                f2Address.setText(user.address)

            }
        }

        binding.f2Button.setOnClickListener {

            binding.apply {
                val country = f2CountryField.text.toString()
                val city = f2CityField.text.toString()
                val address = f2Address.text.toString()
                viewModel.inputData.value = UserAddress(country, city, address)
            }

            viewModel.saveData()
            activityViewModel.setFlag(StateFlags.FRAGMENT_3)

        }
    }
}