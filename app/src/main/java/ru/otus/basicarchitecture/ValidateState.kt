package ru.otus.basicarchitecture

sealed class ValidateState {
    object Ok : ValidateState()
    object Not18 : ValidateState()
    data class BedFiled(val filed: String) : ValidateState()
    data class LoseFiled(val filed: String) : ValidateState()
}