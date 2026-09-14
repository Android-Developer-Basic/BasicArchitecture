package ru.otus.basicarchitecture.validation

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PersonalInfoValidatorTest {
    private lateinit var validator: PersonalInfoValidator

    @Before
    fun setUp() {
        val clock = Clock.fixed(
            Instant.parse("2025-05-20T12:00:00Z"),
            ZoneOffset.UTC,
        )
        validator = PersonalInfoValidator(clock)
    }

    @Test
    fun `valid data for user exactly eighteen years old`() {
        val result = validator.validate(
            firstName = "Иван",
            lastName = "Иванов",
            birthDateText = "20.05.2007",
        )

        assertTrue(result.isValid)
        assertNull(result.firstNameError)
        assertNull(result.lastNameError)
        assertNull(result.birthDateError)
        assertEquals(LocalDate.of(2007, 5, 20), result.birthDate)
    }

    @Test
    fun `user one day younger than eighteen is rejected`() {
        val result = validator.validate(
            firstName = "Иван",
            lastName = "Иванов",
            birthDateText = "21.05.2007",
        )

        assertFalse(result.isValid)
        assertEquals(ValidationError.UnderEighteen, result.birthDateError)
    }

    @Test
    fun `future birth date is rejected as future date`() {
        val result = validator.validate(
            firstName = "Иван",
            lastName = "Иванов",
            birthDateText = "21.05.2025",
        )

        assertFalse(result.isValid)
        assertEquals(ValidationError.FutureDate, result.birthDateError)
    }

    @Test
    fun `invalid calendar date is rejected`() {
        val result = validator.validate(
            firstName = "Иван",
            lastName = "Иванов",
            birthDateText = "29.02.2023",
        )

        assertFalse(result.isValid)
        assertEquals(ValidationError.InvalidDate, result.birthDateError)
        assertNull(result.birthDate)
    }

    @Test
    fun `blank fields are marked as required`() {
        val result = validator.validate(
            firstName = " ",
            lastName = "",
            birthDateText = "",
        )

        assertFalse(result.isValid)
        assertEquals(ValidationError.Required, result.firstNameError)
        assertEquals(ValidationError.Required, result.lastNameError)
        assertEquals(ValidationError.Required, result.birthDateError)
    }
}
