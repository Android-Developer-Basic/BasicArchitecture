package ru.otus.basicarchitecture.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.otus.basicarchitecture.R

class FirstScreenFragment : Fragment() {



    private var screen = UNKNOWN_SCREEN

    private val

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_and_second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun parseParam(){
        val args = requireArguments()
        if(!args.containsKey(KEY_SCREEN_MODE)){
            throw Exception(Exception_message)
        }
        screen = requireArguments().getString(SCREEN_MODE) ?: throw Exception(Exception_message)
    }


    companion object{
        private val Exception_message = "First fragment: unknown screen mode"
        private val UNKNOWN_SCREEN = "unknown_screen"
        private val KEY_SCREEN_MODE = "key_screen_mode"
        private val SCREEN_MODE = "first_screen"
        fun instance() : FirstScreenFragment{
            return FirstScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_SCREEN_MODE, SCREEN_MODE)
                }
            }
        }
    }
}