package ru.otus.basicarchitecture.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.presentation.FirstScreen.FirstScreenFragment
import ru.otus.basicarchitecture.presentation.ThirdScreen.ThirdScreenFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null ){
           // val fragment = FirstScreenFragment.instance()
            val fragment = ThirdScreenFragment.instance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }


    }
}