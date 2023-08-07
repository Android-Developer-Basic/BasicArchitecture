package ru.otus.basicarchitecture.domain.models

data class HobbyItem(
    val hobby: String,
    var enabled: Boolean = false,
    val id: Int = UNDEFINED_ID

){
    companion object{
        const val UNDEFINED_ID = -1
    }
}
