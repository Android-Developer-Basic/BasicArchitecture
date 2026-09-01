package ru.otus.basicarchitecture.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.ui.address.AddressRoute
import ru.otus.basicarchitecture.ui.address.AddressViewModel
import ru.otus.basicarchitecture.ui.interests.InterestsRoute
import ru.otus.basicarchitecture.ui.interests.InterestsViewModel
import ru.otus.basicarchitecture.ui.personal.PersonalRoute
import ru.otus.basicarchitecture.ui.personal.PersonalViewModel
import ru.otus.basicarchitecture.ui.summary.SummaryRoute
import ru.otus.basicarchitecture.ui.summary.SummaryViewModel

private enum class WizardDestination(
    val route: String,
    @StringRes val titleRes: Int,
) {
    Personal("personal", R.string.personal_title),
    Address("address", R.string.address_title),
    Interests("interests", R.string.interests_title),
    Summary("summary", R.string.summary_title),
}

@Composable
fun WizardNavHost(
    viewModelFactory: ViewModelProvider.Factory,
    onDestinationChanged: (titleRes: Int, canNavigateBack: Boolean) -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = WizardDestination.entries.firstOrNull {
        it.route == currentBackStackEntry?.destination?.route
    } ?: WizardDestination.Personal

    LaunchedEffect(currentDestination) {
        onDestinationChanged(
            currentDestination.titleRes,
            currentDestination != WizardDestination.Personal,
        )
    }

    NavHost(
        navController = navController,
        startDestination = WizardDestination.Personal.route,
    ) {
        composable(WizardDestination.Personal.route) {
            val viewModel: PersonalViewModel = viewModel(factory = viewModelFactory)
            PersonalRoute(
                viewModel = viewModel,
                onNext = { navController.navigate(WizardDestination.Address.route) },
            )
        }
        composable(WizardDestination.Address.route) {
            val viewModel: AddressViewModel = viewModel(factory = viewModelFactory)
            AddressRoute(
                viewModel = viewModel,
                onNext = { navController.navigate(WizardDestination.Interests.route) },
            )
        }
        composable(WizardDestination.Interests.route) {
            val viewModel: InterestsViewModel = viewModel(factory = viewModelFactory)
            InterestsRoute(
                viewModel = viewModel,
                onNext = { navController.navigate(WizardDestination.Summary.route) },
            )
        }
        composable(WizardDestination.Summary.route) {
            val viewModel: SummaryViewModel = viewModel(factory = viewModelFactory)
            SummaryRoute(
                viewModel = viewModel,
            )
        }
    }
}