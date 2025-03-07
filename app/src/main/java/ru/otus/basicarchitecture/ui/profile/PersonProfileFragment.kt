package ru.otus.basicarchitecture.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R

@AndroidEntryPoint
class PersonProfileFragment : Fragment() {

    companion object {
        fun newInstance() = PersonProfileFragment()
    }

    private val viewModel: PersonProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_person_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateProfile(view)
        setHobbyCards(view)
    }

    @SuppressLint("SetTextI18n")
    private fun updateProfile(view: View) {
        val nameTextView = view.findViewById<TextView>(R.id.namePerson)
        val surnameTextView = view.findViewById<TextView>(R.id.surnamePerson)
        val birthDateTextView = view.findViewById<TextView>(R.id.birthDataPerson)
        val countryTextView = view.findViewById<TextView>(R.id.addressPerson)

        nameTextView.text = viewModel.personName
        surnameTextView.text = viewModel.personSurname
        birthDateTextView.text = viewModel.personBirthDate
        countryTextView.text = viewModel.personAddress
    }

    private fun setHobbyCards(view: View) {

        val flow = view.findViewById<Flow>(R.id.hobby_flow_on_profile)
        val container = view.findViewById<ConstraintLayout>(R.id.interestsConstraintLayout)

        viewModel.hobby.forEach { hobby ->

            val card = LayoutInflater.from(context).inflate(R.layout.card, container, false)
            card.id = View.generateViewId()
            container.addView(card)
            card.findViewById<TextView>(R.id.textHobby).text = hobby
            flow.addView(card)
        }
    }
}


