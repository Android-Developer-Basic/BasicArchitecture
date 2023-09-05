package ru.otus.basicarchitecture

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.otus.basicarchitecture.viewmodel.UserViewModel
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {
    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.isEnabled = false
        binding.next.setOnClickListener() {
            viewModel.onNext(binding.name.editText!!.text.toString(), binding.surname.editText!!.text.toString())
            findNavController().navigate(R.id.action_nameFragment_to_addressFragment)
        }
        binding.date.editText!!.addTextChangedListener(DateMask(viewModel))

        viewModel.getResultValidDateLive().observe(viewLifecycleOwner) {
            if(it == null) {
                binding.next.isEnabled = false
                binding.next.text = "Введите корректную дату"
                return@observe
            }
            if (it == true) {
                binding.next.text = "Далее"
                binding.next.isEnabled = true

            } else {
                binding.next.isEnabled = false
                binding.next.text = "Вам нет 18 лет"
              }
        }
    }
       }
