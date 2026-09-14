package ru.otus.basicarchitecture.ui.address

import java.io.IOException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import ru.otus.basicarchitecture.address.AddressSuggestionsRepository
import ru.otus.basicarchitecture.address.DaDataAuthorizationException
import ru.otus.basicarchitecture.address.MissingDaDataApiKeyException
import ru.otus.basicarchitecture.cache.InMemoryWizardCache
import ru.otus.basicarchitecture.model.AddressSuggestion
import ru.otus.basicarchitecture.test.MainDispatcherRule

@OptIn(ExperimentalCoroutinesApi::class)
class AddressViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val suggestion = AddressSuggestion(
        value = "Россия, г Москва, ул Тверская, д 1",
        unrestrictedValue = "125009, Россия, г Москва, ул Тверская, д 1",
        country = "Россия",
        city = "г Москва",
        street = "ул Тверская",
        house = "д 1",
    )

    @Test
    fun `query shorter than three characters does not start request`() = runTest {
        val repository = FakeAddressSuggestionsRepository()
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("мо")
        advanceUntilIdle()

        assertTrue(repository.queries.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.uiState.value.hasSearched)
    }

    @Test
    fun `request starts after debounce and successful response updates state`() = runTest {
        val result = CompletableDeferred<List<AddressSuggestion>>()
        val repository = FakeAddressSuggestionsRepository { result.await() }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("  Москва ")
        advanceTimeBy(399)
        runCurrent()

        assertTrue(repository.queries.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)

        advanceTimeBy(1)
        runCurrent()

        assertEquals(listOf("Москва"), repository.queries)
        assertTrue(viewModel.uiState.value.isLoading)

        result.complete(listOf(suggestion))
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertEquals(listOf(suggestion), suggestions)
            assertFalse(isLoading)
            assertTrue(hasSearched)
            assertNull(error)
        }
    }

    @Test
    fun `new query cancels previous request`() = runTest {
        var firstRequestCancelled = false
        val repository = FakeAddressSuggestionsRepository { query ->
            if (query == "моск") {
                try {
                    awaitCancellation()
                } finally {
                    firstRequestCancelled = true
                }
            } else {
                listOf(suggestion)
            }
        }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("моск")
        advanceTimeBy(400)
        runCurrent()
        assertTrue(viewModel.uiState.value.isLoading)

        viewModel.onQueryChanged("москва")
        runCurrent()

        assertTrue(firstRequestCancelled)
        assertFalse(viewModel.uiState.value.isLoading)

        advanceUntilIdle()

        assertEquals(listOf("моск", "москва"), repository.queries)
        assertEquals(listOf(suggestion), viewModel.uiState.value.suggestions)
    }

    @Test
    fun `io exception is exposed as network error`() = runTest {
        val repository = FakeAddressSuggestionsRepository { throw IOException() }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("Москва")
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertEquals(AddressSearchError.Network, error)
            assertFalse(isLoading)
            assertTrue(hasSearched)
            assertTrue(suggestions.isEmpty())
        }
    }

    @Test
    fun `authorization exception is exposed as authorization error`() = runTest {
        val repository = FakeAddressSuggestionsRepository {
            throw DaDataAuthorizationException()
        }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("Москва")
        advanceUntilIdle()

        assertEquals(AddressSearchError.Authorization, viewModel.uiState.value.error)
    }

    @Test
    fun `missing key exception is exposed as key error`() = runTest {
        val repository = FakeAddressSuggestionsRepository {
            throw MissingDaDataApiKeyException()
        }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("Москва")
        advanceUntilIdle()

        assertEquals(AddressSearchError.ApiKeyMissing, viewModel.uiState.value.error)
    }

    @Test
    fun `empty response marks search as completed`() = runTest {
        val repository = FakeAddressSuggestionsRepository { emptyList() }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("несуществующий адрес")
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertTrue(hasSearched)
            assertTrue(suggestions.isEmpty())
            assertNull(error)
            assertFalse(isLoading)
        }
    }

    @Test
    fun `selected suggestion enables next and is saved to cache`() = runTest {
        val cache = InMemoryWizardCache()
        val viewModel = AddressViewModel(
            wizardCache = cache,
            addressSuggestionsRepository = FakeAddressSuggestionsRepository(),
        )

        viewModel.onSuggestionSelected(suggestion)

        with(viewModel.uiState.value) {
            assertEquals(suggestion.value, query)
            assertEquals(suggestion.toAddress(), selectedAddress)
            assertTrue(isNextEnabled)
            assertFalse(hasSearched)
            assertTrue(suggestions.isEmpty())
        }

        viewModel.onNextClicked()

        assertEquals(suggestion.toAddress(), cache.data.value.address)
    }

    private fun createViewModel(
        repository: AddressSuggestionsRepository,
    ): AddressViewModel = AddressViewModel(
        wizardCache = InMemoryWizardCache(),
        addressSuggestionsRepository = repository,
    )
}

private class FakeAddressSuggestionsRepository(
    private val answer: suspend (String) -> List<AddressSuggestion> = { emptyList() },
) : AddressSuggestionsRepository {
    val queries = mutableListOf<String>()

    override suspend fun getSuggestions(query: String): List<AddressSuggestion> {
        queries += query
        return answer(query)
    }
}
