package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.AddressFragmentBinding

@AndroidEntryPoint
class AddressFragment : Fragment(R.layout.address_fragment) {

    private lateinit var binding: AddressFragmentBinding
    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddressFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fillFieldsFromCache(binding)
        binding.nextButton.setOnClickListener {
            viewModel.saveFieldsToCache(binding)
            findNavController().navigate(R.id.addressNext)
        }
    }

}