package ru.otus.basicarchitecture.interests

data class InterestsFragmentViewState(
    val selectedInterests: Set<String> = emptySet(),
    val checkInterest: Boolean
)