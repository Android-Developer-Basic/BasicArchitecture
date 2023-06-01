package ru.otus.basicarchitecture.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import ru.otus.basicarchitecture.viewmodel.AddressViewModel
import ru.otus.basicarchitecture.viewmodel.InterestsViewModel

class AddressFragment : Fragment() {

    companion object {
        fun newInstance() = AddressFragment()
    }
    private lateinit var binding: FragmentAddressBinding
    private val viewModel: AddressViewModel by activityViewModels()

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

        binding.button.setOnClickListener() {
            viewModel.saveData(
                binding.country.text.toString(),
                binding.city.text.toString(),
                binding.address.text.toString()
            )
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, InterestsFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}