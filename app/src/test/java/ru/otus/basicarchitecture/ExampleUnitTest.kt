package ru.otus.basicarchitecture

import org.junit.Test

import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import ru.otus.basicarchitecture.data.WizardCache
import ru.otus.basicarchitecture.ui.StepOneViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import ru.otus.basicarchitecture.data.AddressRepository
import ru.otus.basicarchitecture.ui.StepTwoViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class StepOneViewModelTest {

    private lateinit var viewModel: StepOneViewModel
    private val wizardCache: WizardCache = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = StepOneViewModel(wizardCache)
    }

    @Test
    fun `when input is valid and age is 18 plus then button is enabled`() {
        val adultDate =
            LocalDate.now().minusYears(20).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        viewModel.onInputChanged("Ivan", "Ivanov", adultDate)
        assertTrue(viewModel.isNextButtonEnabled.value)
    }

    @Test
    fun `when age is less than 18 then button is disabled`() {
        val youngDate =
            LocalDate.now().minusYears(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        viewModel.onInputChanged("Ivan", "Ivanov", youngDate)
        assertFalse(viewModel.isNextButtonEnabled.value)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class StepTwoViewModelTest {

    private lateinit var viewModel: StepTwoViewModel
    private val repository: AddressRepository = mockk()
    private val wizardCache: WizardCache = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = StepTwoViewModel(repository, wizardCache)
    }

    @Test
    fun `getSuggestions should update state flow with data from repository`() = runTest {
        val mockSuggestions = listOf("Москва, ул. Ленина", "Московская обл.")
        coEvery { repository.getSuggestions("Мос") } returns mockSuggestions

        viewModel.getSuggestions("Мос")
        advanceUntilIdle()

        assertEquals(mockSuggestions, viewModel.suggestions.value)
    }
}