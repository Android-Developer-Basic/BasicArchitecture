package ru.otus.basicarchitecture

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.otus.basicarchitecture.databinding.Fragment1Binding
import ru.otus.basicarchitecture.databinding.Fragment2Binding
import ru.otus.contextMainAct


class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = Fragment2Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToFR3.setOnClickListener{
            contextMainAct.navController.navigate(R.id.action_fragment2_to_fragment3)
        }
    }
}