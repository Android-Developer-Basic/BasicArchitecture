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
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : Fragment(R.layout.fragment_address) {
    private lateinit var binding: FragmentAddressBinding
    private val viewModel : AddressViewModel by viewModels()

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
        binding.next.setOnClickListener() {
            viewModel.saveData(
                binding.country.editText!!.text.toString(),
                binding.city.editText!!.text.toString(),
                binding.address.editText!!.text.toString()
            )
            findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
        }

    }
}