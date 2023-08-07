package ru.otus.basicarchitecture

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityScoped

object DataCache {
    lateinit var name: String
    lateinit var surname: String
    lateinit var birthday: String
    lateinit var country: String
    lateinit var city: String
    lateinit var address: String
    val listOfInterests:List<String?> = listOf( "Music", "Cocking", "Walking", "Working", "Picture", "Travels", "Film", "Series")
    lateinit var selectedInterests: List<String?>
}


@Module
@InstallIn(ActivityRetainedComponent::class)
class DataCache2() {
    lateinit var name: String
    lateinit var surname: String
    lateinit var birthday: String
    lateinit var country: String
    lateinit var city: String
    lateinit var address: String
    val listOfInterests:List<String?> = listOf( "Music", "Cocking", "Walking", "Working", "Picture", "Travels", "Film", "Series")
    lateinit var selectedInterests: List<String?>


    @Provides
    @ActivityScoped
    fun name(): String = name

}