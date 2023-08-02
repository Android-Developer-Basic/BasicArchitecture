package ru.otus.basicarchitecture.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import ru.otus.WizardCache
import ru.otus.basicarchitecture.viewState.viewStateFR1
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Модель, куда мы инжектим из хилт общую часть
 * Данные нашего визарда будем копить там
 * Переделать конструктор на хилт
 */
open class viewModelFR1 @JvmOverloads constructor(val cache: WizardCache = WizardCache.Impl): ViewModel() {    //хранит состояние вьюх

    // Определяет состояние вью
    val _stateFR1: MutableLiveData<viewStateFR1> = MutableLiveData<viewStateFR1>(
        viewStateFR1() // Начальное значение
    )

    // Не дает изменить вью снаружи. Просто регулирует открытость/закрытость.
    // Меняем состояние только изнутри модели
    // Лишние перерисовки блокируем distinctUntilChanged
    val viewState: LiveData<viewStateFR1> get() = _stateFR1.distinctUntilChanged()

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
            else -> null
        }
    }

    /**
     * Преобразуем предметные данные (из WizardCache) к данным для отображения
     * Делаем это каждый раз при изменении, поскольку мы решили отображать полное состояние,
     * а не полагаться на состояние View
     */
    private fun renderView() {
        _stateFR1.value = with(cache.data) {
            viewStateFR1(
                name = name,
                surName = surName,
                data = dateString,
                dataOK = name.isNotBlank() && surName.isNotBlank() && null == getDateError()
            )
        }
    }

    fun setName(value: String) {
        if (value == cache.data.name) return
        cache.data = cache.data.copy(name = value)
        renderView()
    }

    fun setSurname(value: String) {
        if (value == cache.data.surName) return
        cache.data = cache.data.copy(surName = value)
        renderView()
    }

    fun setDate(value: String) {
        if (value == dateString) return
        dateString = value
        // Записываем дату только если она валидна
        cache.data = cache.data.copy(bd = getValidDate())
        renderView()
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



}