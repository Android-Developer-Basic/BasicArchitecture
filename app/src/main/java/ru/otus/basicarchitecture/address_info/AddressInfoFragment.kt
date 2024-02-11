package ru.otus.basicarchitecture.address_info

import android.os.Bundle
import android.text.TextWatcher
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

    private val adapter = AddressAdapter {
        viewModel.setAddress(it)
    }

    private lateinit var textWatcher: TextWatcher

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

            textWatcher = addressInfoAddress.addTextChangedListener {
                viewModel.searchAddress(it.toString())
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {

                        addressInfoAddress.removeTextChangedListener(textWatcher)
                        addressInfoAddress.setTextKeepState(it.address)
                        addressInfoAddress.addTextChangedListener(textWatcher)

                        adapter.submitList(it.addressList)

                        buttonMoveToFragmentInterest.isEnabled = it.address.isNotEmpty()
                    }
                }
            }

            recyclerAddress.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}