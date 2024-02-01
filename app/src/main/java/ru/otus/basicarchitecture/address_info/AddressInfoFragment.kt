package ru.otus.basicarchitecture.address_info

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressInfoBinding

@AndroidEntryPoint
class AddressInfoFragment : Fragment() {

    private var binding: FragmentAddressInfoBinding? = null
    private inline fun withBinding(block: FragmentAddressInfoBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: AddressInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressInfoBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            buttonMoveToFragmentInterest.setOnClickListener {
                findNavController().navigate(R.id.action_addressInfoFragment_to_interestsFragment)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {
                        addressInfoCountry.setTextKeepState(it.country)
                        addressInfoCity.setTextKeepState(it.city)
                        addressInfoAddress.setTextKeepState(it.address)

                        buttonMoveToFragmentInterest.isEnabled = it.isValidCountry && it.isValidCity && it.isValidAddress
                    }
                }
            }

            addressInfoCountry.setTextKeepState(viewModel.viewState.value.country)
            addressInfoCity.setTextKeepState(viewModel.viewState.value.city)
            addressInfoAddress.setTextKeepState(viewModel.viewState.value.address)

            addressInfoCountry.addTextChangedListener {
                viewModel.setCountry(it.toString())
            }

            addressInfoCity.addTextChangedListener {
                viewModel.setCity(it.toString())
            }

            addressInfoAddress.addTextChangedListener {
                viewModel.setAddress(it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}