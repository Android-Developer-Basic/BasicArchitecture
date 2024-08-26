import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    fun getName(): String? {
        return wizardCache.name
    }

    fun getSurname(): String? {
        return wizardCache.surname
    }

    fun getDateOfBirth(): String? {
        return wizardCache.dateOfBirth
    }

    fun getFullAddress(): String {
        return "${wizardCache.address}, ${wizardCache.city}, ${wizardCache.country}"
    }

    fun getInterestsAsString(): String {
        return wizardCache.interests.joinToString(", ")
    }
}
