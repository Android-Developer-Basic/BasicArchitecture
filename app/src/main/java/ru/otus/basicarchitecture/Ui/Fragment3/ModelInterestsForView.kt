package ru.otus.basicarchitecture.presentation.Fragment3

import ru.otus.basicarchitecture.Core.Model.Interests

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