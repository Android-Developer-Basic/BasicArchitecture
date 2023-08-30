package ru.otus.basicarchitecture.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.WizardCache
import ru.otus.basicarchitecture.viewState.viewStateFR1
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Модель, куда мы инжектим из хилт общую часть
 * Данные нашего визарда будем копить там
 * Переделать конструктор на хилт
 */
@HiltViewModel
open class viewModelFR1 @Inject constructor(private val cache: WizardCache): ViewModel() {    //хранит состояние вьюх
    //val cache: WizardCache = WizardCache.Impl

    // Определяет состояние вью
    val _stateFR1: MutableLiveData<viewStateFR1> = MutableLiveData<viewStateFR1>(
        viewStateFR1() // Начальное значение
    )

    // Не дает изменить вью снаружи. Просто регулирует открытость/закрытость.
    // Меняем состояние только изнутри модели
    // Лишние перерисовки блокируем distinctUntilChanged
    val viewState: LiveData<viewStateFR1> get() = _stateFR1.distinctUntilChanged()  //в фрагментах/активити использовать это поле для обращения к состоянию

    // Промежуточные данные, поскольку в домене нам нужна только валидная дата
    // В остальных случаях - пишем сразу в кеш
    var dateString: String = ""

    // Вид даты, которую спарсить
    private val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // Преобразуем строку в дату. Нулл - если не удалось

    fun getValidDate(): LocalDate? = try {
        LocalDate.parse(dateString, dateFormat)
    } catch (e: Throwable) {
        null
    }

    // Проверяем дату на валидность - готовим ошибку для вью
    fun getDateError(): String? {
        val validDate = getValidDate()

        return when {
            null == validDate -> "Неверная или пустая дата рождения"
            validDate.plusYears(18).isAfter(LocalDate.now()) -> "Вам мало лет"
            else -> null  //вернет null если дата введена в соответствии с шаблном и выполняются все условия
        }
    }

    /**
     * Преобразуем предметные данные (из WizardCache) к данным для отображения
     * Делаем это каждый раз при изменении, поскольку мы решили отображать полное состояние,
     * а не полагаться на состояние View
     */
    private fun renderView() {   //обновлялка viewState (синхронизация с WizardCache)
        _stateFR1.value = with(cache) {
            viewStateFR1(
                name = name,
                surName = surName,
                data = dateString,
                dataOK = name.isNotBlank() && surName.isNotBlank() && null == getDateError(), //если все ок вернет true
                dataError = getDateError(), //сообщение об ошибке ввода даты
            )
        }
    }

    fun setName(value: String) {
        if (value == cache.name) return
        cache.name = value
        renderView()
    }

    fun setSurname(value: String) {
        if (value == cache.surName) return
        cache.surName = value
        renderView()
    }

    fun setDate(value: String) {
        if (value == dateString) return   //если дата отличается от пустой строки то
        dateString = value
        // Записываем дату только если она валидна
        cache.bd = getValidDate()   // запись если совпалает с шаблоном
        renderView()
    }

//*********************************
//в fragment во view загружаем состояние из viewModel
//получаем данные из view
//записываем полученные данные в WizardCache
//из WizardCache записываем данные в viewModel
//данные получаются везде одинаковые
//*********************************
}


// Потом сделай как ты хочешь. Я упрощу...
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun setData(data: String?){
//        valueFR1.data = data
//
//        val vet = valueFR1.data?.split(".")   //делит введенную дату
//        val splitData = mutableListOf<String>()
//        if (vet != null) {
//
//            for (i in vet){                             //записываем в список
//            splitData.add(i)   //dd, mm, gggg
//            }
//        }
//
//        val today = LocalDate.now()  //сегодня
//
//        try {
//                val specificDate = LocalDate.of(
//                splitData[2].toInt(),
//                splitData[1].toInt(),
//                splitData[0].toInt()
//            ) //введенная дата
//
//            valueFR1.dataOK = today.minusYears(18).isAfter(specificDate)   //true если болше 18
//
//        } catch (_: Exception){
//            valueFR1.dataOK = false
//        }
//
//
//
//
//    }