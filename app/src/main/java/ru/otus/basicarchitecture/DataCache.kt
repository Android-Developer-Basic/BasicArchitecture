package ru.otus.basicarchitecture

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.Date
import javax.inject.Inject

data class DataCache(
    val name: String = "",
    val surname: String = "",
    val birthday: Date = Date(),
    val country: String = "",
    val city: String = "",
    val address: String = "",
    val selectedInterests: List<String> = emptyList()) {
    val listOfInterests:List<String> = listOf( "Music", "Cocking", "Walking", "Working", "Picture", "Travels", "Film", "Series")
}
@ActivityRetainedScoped
class DataCacheStorage @Inject constructor(){
    val cache : MutableLiveData<DataCache> = MutableLiveData(DataCache())
}