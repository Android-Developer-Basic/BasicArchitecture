package ru.otus.basicarchitecture

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import ru.otus.basicarchitecture.ui.navigation.WizardNavHost
import ru.otus.basicarchitecture.ui.theme.BasicArchitectureTheme

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BasicArchitectureApp).appComponent.inject(this)

        setContent {
            BasicArchitectureTheme {
                WizardNavHost(
                    viewModelFactory = viewModelFactory,
                    onDestinationChanged = ::updateActionBar,
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun updateActionBar(
        @StringRes titleRes: Int,
        canNavigateBack: Boolean,
    ) {
        supportActionBar?.apply {
            setTitle(titleRes)
            setDisplayHomeAsUpEnabled(canNavigateBack)
            setHomeButtonEnabled(canNavigateBack)
        }
    }
}