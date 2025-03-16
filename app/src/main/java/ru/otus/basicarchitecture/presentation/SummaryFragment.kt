package ru.otus.basicarchitecture.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import javax.inject.Inject

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    @Inject
    lateinit var viewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.nameInfo).text = viewModel.name
        view.findViewById<TextView>(R.id.surnameInfo).text = viewModel.surname
        view.findViewById<TextView>(R.id.birthDateInfo).text = viewModel.birthDate
        view.findViewById<TextView>(R.id.addressInfo).text = viewModel.address

        viewModel.interests.forEach {
            Chip(context).apply {
                text = it
                isClickable = false
                isCheckable = false
                view.findViewById<ChipGroup>(R.id.tagsInfo).addView(this)
            }
        }
    }
}