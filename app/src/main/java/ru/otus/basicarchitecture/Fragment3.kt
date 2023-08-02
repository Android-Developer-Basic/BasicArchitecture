package ru.otus.basicarchitecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.otus.basicarchitecture.databinding.Fragment3Binding

class Fragment3 : Fragment() {

    private lateinit var binding: Fragment3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = Fragment3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToFR4.setOnClickListener{
            findNavController().navigate(R.id.action_fragment3_to_fragment4)
        }


    }

}