package ru.otus.basicarchitecture

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class UserInputViewModelTest {

    private lateinit var viewModel: UserInputViewModel
    private lateinit var fakeWizardCache: WizardCache

    @Before
    fun setUp() {
        fakeWizardCache = FakeWizardCache()
        viewModel = UserInputViewModel(fakeWizardCache)
    }

    @Test
    fun testIsValidFirst_missingField() {
        val result = viewModel.isValidFirst("", listOf(listOf("", "Имя"), listOf("", "Фамилия")))
        assertEquals(ValidateState.LoseFiled("Имя"), result)
    }

    @Test
    fun testIsValidFirst_invalidDate() {
        val result = viewModel.isValidFirst("invalid_date", listOf(listOf("John", "Имя"), listOf("Doe", "Фамилия")))
        assertEquals(ValidateState.BedFiled("Дата рождения"), result)
    }

    @Test
    fun testIsValidFirst_notAdult() {
        val notAdultDate = "10.01.2010" // Дата, когда еще нет 18 лет
        val result = viewModel.isValidFirst(notAdultDate, listOf(listOf("John", "Имя"), listOf("Doe", "Фамилия")))
        assertEquals(ValidateState.Not18, result)
    }

    @Test
    fun testIsValidFirst_valid() {
        val validDate = "10.01.1990" // Валидная дата рождения
        val result = viewModel.isValidFirst(validDate, listOf(listOf("John", "Имя"), listOf("Doe", "Фамилия")))
        assertEquals(ValidateState.Ok, result)
    }
}