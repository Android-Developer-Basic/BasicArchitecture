package ru.otus.basicarchitecture.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.viewmodels.Fragment4ViewModel

@AndroidEntryPoint
class Fragment4 : Fragment() {
    private val viewModel: Fragment4ViewModel by viewModels()

    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var birthDateTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var tagFlexboxLayout: FlexboxLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameTextView = view.findViewById(R.id.tvFirstName)
        lastNameTextView = view.findViewById(R.id.tvLastName)
        birthDateTextView = view.findViewById(R.id.tvBirthDate)
        addressTextView = view.findViewById(R.id.tvAddress)
        tagFlexboxLayout = view.findViewById(R.id.tagFlexboxLayout)

        firstNameTextView.text = viewModel.firstName ?: "N/A"
        lastNameTextView.text = viewModel.lastName ?: "N/A"
        birthDateTextView.text = viewModel.birthDate ?: "N/A"
        addressTextView.text = viewModel.fullAddress

        viewModel.interests.forEach { interest ->
            val tagView = createTagView(interest)
            tagFlexboxLayout.addView(tagView)
        }
    }

    private fun createTagView(interest: String): TextView {
        val tagView = LayoutInflater.from(context).inflate(R.layout.tag_item, tagFlexboxLayout, false) as TextView
        tagView.text = interest
        return tagView
    }
}
