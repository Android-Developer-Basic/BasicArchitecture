import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    private val daDataApi: DaDataApi
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    var address: String? = null
    var suggestions: List<String> = emptyList()

    fun fetchAddressSuggestions(query: String, onResult: (List<String>) -> Unit) {
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    daDataApi.suggestAddress(SuggestRequest(query))
                }
                suggestions = response.suggestions.map { it.value }
                onResult(suggestions)
            } catch (e: HttpException) {
                onResult(emptyList())
            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    fun saveData(): Boolean {
        wizardCache.address = address
        return true
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel() // Отменяем все запущенные корутины при уничтожении ViewModel
    }
}