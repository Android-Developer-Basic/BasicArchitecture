package ru.otus.basicarchitecture

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.otus.basicarchitecture.utils.DateTimeConverter
import java.util.Date
import javax.inject.Inject

data class WizardCache(
    val name: String = "",
    val surname: String = "",
    val birth: Date = Date(),
    val location: String = "",
    val selectedInterests: List<String> = emptyList()
) {
    val listOfInterests: List<String> = listOf(
        "Music",
        "Programming",
        "Yoga",
        "Sport",
        "Dancing",
        "Theatre",
        "Smoking",
        "Photo",
        "Hunting",
        "Reading",
        "Cooking",
        "Walking",
        "Picture",
        "Travel",
        "Films",
        "Series"
    )
}

@ActivityRetainedScoped
class WizardCacheStorage @Inject constructor() {
    val cache: MutableLiveData<WizardCache> = MutableLiveData(WizardCache())
}