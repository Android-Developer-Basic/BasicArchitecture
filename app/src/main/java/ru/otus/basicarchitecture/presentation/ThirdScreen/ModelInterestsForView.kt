package ru.otus.basicarchitecture.presentation.ThirdScreen

import ru.otus.basicarchitecture.domain.Model.Interests

data class ModelInterestsForView(
    var id: Int,
    val interests: String,
    var enabled: Boolean
){

    companion object {
        fun toDomain(modelView: ModelInterestsForView): Interests {
            return Interests(modelView.interests)
        }

        fun toModel(id: Int, interests: Interests): ModelInterestsForView {
            return ModelInterestsForView(id, interests.interests, false)
        }
    }
}