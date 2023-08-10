package ru.otus.basicarchitecture.questionnaire

data class QuestionnaireModelState(
    val name: String = "",
    val surname: String = "",
    val birthday: String = "",
    val address: String = "",
    val selectedInterests: List<String> = emptyList()
)