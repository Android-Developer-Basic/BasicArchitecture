package ru.otus.basicarchitecture.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.interests.InterestsFragment
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment: Fragment() {

    private lateinit var binding: FragmentAddressBinding

    @Inject
    lateinit var viewModel: AddressFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.city.addTextChangedListener { viewModel.city.value = it.toString() }
        binding.country.addTextChangedListener { viewModel.country.value = it.toString() }
        binding.address.addTextChangedListener { viewModel.address.value = it.toString() }

        binding.buttonNext2.setOnClickListener(){
            viewModel.save()

            parentFragmentManager.beginTransaction()
                .replace(R.id.container, InterestsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}