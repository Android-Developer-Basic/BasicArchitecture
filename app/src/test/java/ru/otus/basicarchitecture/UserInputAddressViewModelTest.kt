package ru.otus.basicarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserInputAddressViewModelTest {

    private lateinit var viewModel: UserInputAddressViewModel
    private lateinit var fakeWizardCache: WizardCache
    private lateinit var fakeDaDataService: FakeDaDataService

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        fakeWizardCache = FakeWizardCache()
        fakeDaDataService = FakeDaDataService()
        viewModel = UserInputAddressViewModel(fakeWizardCache, fakeDaDataService)
    }

    @Test
    fun `test validateAndSaveAddress with empty address should return LoseField`() {
        viewModel.viewState.value = ViewState(address = "")
        val result = viewModel.validateAndSaveAddress()
        Assert.assertEquals(ValidateState.LoseFiled("Адрес"), result)
    }

    @Test
    fun `test validateAndSaveAddress with non-empty address should return Ok`() {
        viewModel.viewState.value = ViewState(address = "Some address")
        val result = viewModel.validateAndSaveAddress()
        Assert.assertEquals(ValidateState.Ok, result)
    }
}
