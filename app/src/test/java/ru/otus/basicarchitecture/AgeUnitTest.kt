package ru.otus.basicarchitecture

import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import ru.otus.basicarchitecture.domain.WizardCacheService
import ru.otus.basicarchitecture.ui.NameViewModel

class AgeUnitTest {
    private lateinit var viewModel: NameViewModel

    private val wizardCacheService: WizardCacheService = mockk()

    @Before
    fun before() {
        viewModel = NameViewModel(wizardCacheService)
    }

    @Test
    fun `age less than 18 should be invalid`() {
        val result = viewModel.isAgeValid(17)
        assertFalse(result)
    }

    @Test
    fun `age 18 should be valid`() {
        val result = viewModel.isAgeValid(18)
        assertTrue(result)
    }

    @Test
    fun `null age should be invalid`() {
        val result = viewModel.isAgeValid(null)
        assertFalse(result)
    }
}