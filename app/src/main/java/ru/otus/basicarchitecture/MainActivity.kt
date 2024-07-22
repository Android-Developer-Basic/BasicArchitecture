package ru.otus.basicarchitecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.otus.basicarchitecture.Ui.Fragment1.Fragment1
import ru.otus.basicarchitecture.Ui.Fragment1.Fragment2


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null ){
            val fragment = Fragment2()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}