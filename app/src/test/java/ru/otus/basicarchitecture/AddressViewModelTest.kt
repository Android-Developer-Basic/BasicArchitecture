package ru.otus.basicarchitecture

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.otus.basicarchitecture.data.network.AddressQuery
import ru.otus.basicarchitecture.data.network.AddressResponse
import ru.otus.basicarchitecture.data.network.AddressSuggestion
import ru.otus.basicarchitecture.domain.AddressService
import ru.otus.basicarchitecture.domain.WizardCacheService
import ru.otus.basicarchitecture.ui.AddressViewModel

class AddressViewModelTest {
    private lateinit var viewModel: AddressViewModel

    private val networkService: AddressService = mockk()
    private val wizardCacheService: WizardCacheService = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = AddressViewModel(networkService, wizardCacheService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after(){
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `hint equal ро`() {
        runTest {
            val input = "ро"
            val expectedSuggestions = listOf(
                AddressSuggestion("Ростовская обл"),
                AddressSuggestion("г Ростов-на-Дону")
            )

            val response = AddressResponse(expectedSuggestions)

            coEvery { networkService.getHints(AddressQuery(input)) } returns response

            viewModel.getHints(input)
            advanceUntilIdle()

            val actual = viewModel.hints.value
            assertEquals(expectedSuggestions, actual)
        }
    }
}