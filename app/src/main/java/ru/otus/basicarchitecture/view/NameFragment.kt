package ru.otus.basicarchitecture.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.viewmodel.NameViewModel
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentNameBinding

@AndroidEntryPoint
class NameFragment : Fragment() {

    companion object {
        fun newInstance() = NameFragment()
    }
    private lateinit var binding: FragmentNameBinding
    private val viewModel: NameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.isEnabled = false
        binding.button.setOnClickListener() {
            viewModel.onNext(binding.name.text.toString(), binding.surname.text.toString())

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, AddressFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.date.addTextChangedListener(DateMask(viewModel))

        viewModel.getResultValidDateLive().observe(viewLifecycleOwner) {
            if(it == null) {
                binding.button.isEnabled = false
                binding.button.text = "Введите корректную дату"
                return@observe
            }
            if (it == true) {
                binding.button.text = "Далее"
                binding.button.isEnabled = true
            } else {
                binding.button.isEnabled = false
                binding.button.text = "нет 18"
            }
        }
    }

}