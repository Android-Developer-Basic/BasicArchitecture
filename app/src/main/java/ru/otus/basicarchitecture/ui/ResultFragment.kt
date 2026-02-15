package ru.otus.basicarchitecture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.domain.Interests

@AndroidEntryPoint
class ResultFragment : Fragment() {

    val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<TextView>(R.id.name_value)
        val surname = view.findViewById<TextView>(R.id.surname_value)
        val age = view.findViewById<TextView>(R.id.age_value)
        val address = view.findViewById<TextView>(R.id.address_value)

        val musicChip = view.findViewById<Chip>(R.id.music)
        val museumChip = view.findViewById<Chip>(R.id.museums)
        val modernArtChip = view.findViewById<Chip>(R.id.modern_art)
        val carsChip = view.findViewById<Chip>(R.id.cars)
        val bicyclesChip = view.findViewById<Chip>(R.id.bicycles)
        val partiesChip = view.findViewById<Chip>(R.id.parties)
        val theatreChip = view.findViewById<Chip>(R.id.theatre)
        val moviesChip = view.findViewById<Chip>(R.id.movies)
        val gardeningChip = view.findViewById<Chip>(R.id.gardening)
        val hikingChip = view.findViewById<Chip>(R.id.hiking)
        val literatureChip = view.findViewById<Chip>(R.id.literature)
        val craftChip = view.findViewById<Chip>(R.id.craft)

        viewModel.userInfo.observe(this.viewLifecycleOwner) { info ->
            name.text = info.name
            surname.text = info.surname
            age.text = info.age?.let { info.age.toString() } ?: ""
            address.text = info.address
        }

        viewModel.interestsList.observe(this.viewLifecycleOwner) { interests ->
            interests.forEach { interest ->
                when (interest) {
                    Interests.MUSIC -> musicChip.visibility = View.VISIBLE
                    Interests.MUSEUMS -> museumChip.visibility = View.VISIBLE
                    Interests.MODERN_ART -> modernArtChip.visibility = View.VISIBLE
                    Interests.CARS -> carsChip.visibility = View.VISIBLE
                    Interests.BICYCLES -> bicyclesChip.visibility = View.VISIBLE
                    Interests.PARTIES -> partiesChip.visibility = View.VISIBLE
                    Interests.THEATRE -> theatreChip.visibility = View.VISIBLE
                    Interests.MOVIES -> moviesChip.visibility = View.VISIBLE
                    Interests.GARDENING -> gardeningChip.visibility = View.VISIBLE
                    Interests.HIKING -> hikingChip.visibility = View.VISIBLE
                    Interests.LITERATURE -> literatureChip.visibility = View.VISIBLE
                    Interests.CRAFT -> craftChip.visibility = View.VISIBLE
                }
            }
        }
    }
}