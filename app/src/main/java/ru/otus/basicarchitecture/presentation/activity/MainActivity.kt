package ru.otus.basicarchitecture.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.StateFlags
import ru.otus.basicarchitecture.app.App
import ru.otus.basicarchitecture.presentation.fragments.*
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityVMFactory
import ru.otus.basicarchitecture.presentation.viewmodels.ActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ActivityVMFactory
    @Inject
    lateinit var fragment1: Fragment1
    @Inject
    lateinit var fragment2: UserAddressFragment
    @Inject
    lateinit var fragment3: Fragment3
    @Inject
    lateinit var fragment4: Fragment4
    private lateinit var vm:ActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.injectActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this)[ActivityViewModel::class.java]
        vm.liveData.observe(this){
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when(it){
                StateFlags.FRAGMENT_1 -> openFragment(fragment1)
                StateFlags.FRAGMENT_2 -> openFragment(fragment2)
                StateFlags.FRAGMENT_3 -> openFragment(fragment3)
                StateFlags.FRAGMENT_4 -> openFragment(fragment4)
            }
        }
    }

    private fun openFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrameLayout, fragment )
            .addToBackStack(null)
            .commit()
    }
}