package ru.otus.basicarchitecture.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import ru.otus.basicarchitecture.model.AddressService
import ru.otus.basicarchitecture.model.AddressValue
import ru.otus.basicarchitecture.viewmodel.AddressViewModel
import java.util.stream.Collectors

class AddressFragment : Fragment() {
    companion object {
        fun newInstance() = AddressFragment()
    }
    private lateinit var binding: FragmentAddressBinding
    private val viewModel: AddressViewModel by activityViewModels()
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
        binding.button.setOnClickListener() {
            viewModel.saveData(binding.fullAddress.text.toString())
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, InterestsFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancel()
    }
}