package ru.otus.basicarchitecture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.otus.basicarchitecture.databinding.Fragment4Binding


class Fragment4 : Fragment() {

    private lateinit var binding: Fragment4Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = Fragment4Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

}