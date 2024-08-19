package ru.otus.basicarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.ActivityMainBinding
import ru.otus.basicarchitecture.name.NameFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NameFragment())
                .commit()
        }
    }
}