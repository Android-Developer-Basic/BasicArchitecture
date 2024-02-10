package ru.otus.basicarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Model.Person
import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val wizardCache: WizardCache
) : Repository {


    private val listInterests =  listOf(
        "Путешествия на велосипеде",
        "Готовка экзотических блюд",
        "Фотография природы",
        "Чтение фантастической литературы",
        "Участие в квестах и головоломках",
        "Скалолазание",
        "Изучение иностранных языков",
        "Фильмы ужасов",
        "Коллекционирование винтажных книг",
        "Графический дизайн",
        "Велоспорт",
        "Рисование мандал и зентанглов",
        "Плавание с аквалангом",
        "Изучение астрономии",
        "Фотография уличного искусства",
        "Участие в косплей-событиях",
        "Йога и медитация",
        "Игры настольные",
        "Пение в караоке",
        "Реставрация старинной мебели",
        "Занятия танцами",
        "Развитие навыков программирования",
        "Садоводство и уход за растениями",
        "Фотография архитектурных сооружений",
        "Шитье и создание одежды",
        "Философия и обсуждение философских вопросов",
        "Развитие навыков игры на музыкальных инструментах",
        "Прослушивание подкастов о науке",
        "Рукоделие и создание поделок",
        "Экстримальные виды спорта: парашютный спорт, сноубординг, скейтбординг"
    )




    private val mutableLiveDataWizardCache = MutableLiveData<WizardCache>()

    val liveDataWizardCache: LiveData<WizardCache> = mutableLiveDataWizardCache



    override fun setPerson(person: Person) {
        wizardCache.firstName = person.firstName
        wizardCache.surName = person.surName
        wizardCache.dateOfBirth = person.firstName
        updateWizardCache()
    }

    override fun setInterests(interests: Interests) {
        wizardCache.interests = interests.interests
        updateWizardCache()
    }

    override fun setAddress(address: Address) {
        wizardCache.city = address.city
        wizardCache.country = address.country
        wizardCache.address = address.address
        updateWizardCache()
    }

    override fun getInfoPersonUseCase() {
        TODO()
    }

    override fun getListInterests(): List<String>  = listInterests

    private fun updateWizardCache() {
        mutableLiveDataWizardCache.postValue(wizardCache)
    }
}