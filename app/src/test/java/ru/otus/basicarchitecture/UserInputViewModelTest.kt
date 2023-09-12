package ru.otus.basicarchitecture

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.time.Instant

class UserInputViewModelTest {

    private lateinit var viewModel: UserInputViewModel
    private lateinit var fakeWizardCache: WizardCache
    private lateinit var mockClock: Clock

    @Before
    fun setUp() {
        fakeWizardCache = FakeWizardCache()
        mockClock = Mockito.mock(Clock::class.java)
        viewModel = UserInputViewModel(fakeWizardCache, mockClock)
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
        `when`(mockClock.currentMillis()).thenReturn(Instant.parse("2023-01-01T00:00:00Z").toEpochMilli())
        val notAdultDate = "10.01.2010" // Дата, когда еще нет 18 лет
        val result = viewModel.isValidFirst(notAdultDate, listOf(listOf("John", "Имя"), listOf("Doe", "Фамилия")))
        assertEquals(ValidateState.Not18, result)
    }

    @Test
    fun testIsValidFirst_valid() {
        `when`(mockClock.currentMillis()).thenReturn(Instant.parse("2023-01-01T00:00:00Z").toEpochMilli())
        val validDate = "10.01.1990" // Валидная дата рождения
        val result = viewModel.isValidFirst(validDate, listOf(listOf("John", "Имя"), listOf("Doe", "Фамилия")))
        assertEquals(ValidateState.Ok, result)
    }
}