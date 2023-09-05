package ru.otus.basicarchitecture
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import androidx.navigation.fragment.findNavController
import ru.otus.basicarchitecture.viewmodel.AddressViewModel
import androidx.fragment.app.viewModels

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import ru.otus.basicarchitecture.model.AddressService
import ru.otus.basicarchitecture.model.AddressValue
import java.util.stream.Collectors

@AndroidEntryPoint
class AddressFragment : Fragment(R.layout.fragment_address) {
    private lateinit var binding: FragmentAddressBinding
    private val viewModel : AddressViewModel by viewModels()
    private val service = AddressService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fullAddress.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                viewModel.search(editable.toString())
            }
        })

        viewModel.getResultAddressLive().observe(viewLifecycleOwner) {
            if(it!=null) {
                val list = it.stream().map(AddressValue::value).collect(Collectors.toList())
                val adapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    list
                )
                binding.fullAddress.setAdapter(adapter)
            }
        }

        binding.next.setOnClickListener() {
            viewModel.saveData(binding.fullAddress.text.toString())
            findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancel()
    }
}