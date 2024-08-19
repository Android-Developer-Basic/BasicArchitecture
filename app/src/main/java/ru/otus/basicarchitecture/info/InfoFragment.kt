package ru.otus.basicarchitecture.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentInfoBinding
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment: Fragment() {

    private lateinit var binding: FragmentInfoBinding

    @Inject
    lateinit var viewModel: InfoFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameInfo.text = viewModel.dataCache.name
        binding.surnameInfo.text = viewModel.dataCache.surname
        binding.birthDateInfo.text = viewModel.dataCache.dateOfBirth
        binding.addressInfo.text = viewModel.getAddressInfo()

        viewModel.dataCache.interests.forEach {
            Chip(context).apply {
                text = it
                isClickable = false
                isCheckable = false
                binding.chipGroupInfo.addView(this)
            }
        }
    }
}