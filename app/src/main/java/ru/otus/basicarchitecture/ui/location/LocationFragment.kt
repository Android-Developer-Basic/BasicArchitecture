package ru.otus.basicarchitecture.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentLocationBinding
import ru.otus.basicarchitecture.navigate
import ru.otus.basicarchitecture.retrofit.LocationValue
import java.util.stream.Collectors

@AndroidEntryPoint
class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private val viewModel by viewModels<LocationFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextLocation.addTextChangedListener {
            viewModel.search(it?.toString() ?: "")
        }

        viewModel.locationResult.observe(viewLifecycleOwner) { value ->
            value?.apply {
                binding.editTextLocation.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        stream().map(LocationValue::value).collect(Collectors.toList())
                    )
                )
            }
        }

        binding.buttonNext.setOnClickListener {
            when {
                binding.editTextLocation.text.toString().trim().isEmpty() -> {
                    binding.editTextLocation.requestFocus()
                    binding.editTextLocation.error = getString(R.string.error_text_field)
                }

                else -> {
                    viewModel.saveDataToStorage(
                        binding.editTextLocation.text.toString()
                    )
                    navigate(R.id.actionLocationFragmentToInterestsFragment)
                }
            }
        }
    }
}