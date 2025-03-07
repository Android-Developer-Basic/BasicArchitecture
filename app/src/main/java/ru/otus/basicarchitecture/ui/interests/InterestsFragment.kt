package ru.otus.basicarchitecture.ui.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.ui.data.Hobby
import javax.inject.Inject


@AndroidEntryPoint
class InterestsFragment : Fragment() {

    @Inject
    lateinit var hobby: Hobby

    companion object {
        fun newInstance() = InterestsFragment()
    }

    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_interests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        view.findViewById<Button>(R.id.toPersonProfile).setOnClickListener {

            navController.navigate(R.id.action_to_personProfileFragment)
        }
        setHobbyCards(view)
    }

    private fun setHobbyCards(view: View) {

        val flow = view.findViewById<Flow>(R.id.hobby_flow)
        val container = view.findViewById<ConstraintLayout>(R.id.interestsConstraintLayout)

        hobby.hobby_sports.forEach { hobby ->
            addToFlow(hobby, flow, container)
        }

        hobby.hobby_сreative.forEach { hobby ->
            addToFlow(hobby, flow, container)
        }
        hobby.hobby_intelligence.forEach { hobby ->
            addToFlow(hobby, flow, container)
        }
    }

    private fun addToFlow(hobby: String, flow: Flow, container: ConstraintLayout) {

        val card = LayoutInflater.from(context).inflate(R.layout.card, container, false)
        card.id = View.generateViewId()
        card.setOnClickListener {
            onCardHobbyClick(it.id)
        }
        container.addView(card)
        card.findViewById<TextView>(R.id.textHobby).text = hobby
        flow.addView(card)
    }

    private fun onCardHobbyClick(id: Int) {

        val selectedColor = context?.let { ContextCompat.getColor(it, R.color.cardSelected) };
        val defaultColor = context?.let { ContextCompat.getColor(it, R.color.card) };
        val card = view?.findViewById<CardView>(id)

        if (defaultColor != null && selectedColor != null && card != null) {

            if (card.cardBackgroundColor.defaultColor == selectedColor) {
                viewModel.removeHobby(card.findViewById<TextView>(R.id.textHobby).text.toString())
                card.setCardBackgroundColor(defaultColor)
            } else {
                viewModel.addHobby(card.findViewById<TextView>(R.id.textHobby).text.toString())
                card.setCardBackgroundColor(selectedColor)
            }
        }
    }
}

