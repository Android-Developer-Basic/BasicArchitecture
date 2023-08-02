package ru.otus

import java.time.LocalDate

/**
 * Общие данные для всех экранов
 */
interface WizardCache {
    var data: WizardData

    /**
     * Потом переделать на хилт с ActivityRetainedScope
     */
    object Impl : WizardCache {
        // Начальное состояние
        override var data: WizardData = WizardData()
    }
}

/**
 * Общие данные
 */
data class WizardData(
    val name: String = "",
    val surName: String= "",
    val bd: LocalDate? = null
)