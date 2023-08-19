package ru.otus.basicarchitecture

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserInput4Binding
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class UserInputFragmentLast : Fragment(R.layout.fragment_user_input4) {
    private val viewModel: UserInputLastViewModel by viewModels()
    private var _binding: FragmentUserInput4Binding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInput4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstNameTextView.text = viewModel.wizardCache.firstName
        binding.lastNameTextView.text = viewModel.wizardCache.lastName
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        binding.dateOfBirthTextView.text = viewModel.wizardCache.dateOfBirth?.let { format.format(it) }
        binding.addressTextView.text = viewModel.wizardCache.address

        val flexboxLayout = binding.tagContainer
        val tags = viewModel.wizardCache.selectedTags
        tags.forEach { tag ->
            val textView = TextView(context).apply {
                text = tag
                textSize = 32f
                setTextColor(Color.WHITE)
                setPadding(30, 18, 30, 18)
                setBackgroundResource(R.drawable.tag_background2)
            }
            val lp = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(48, 48, 48, 48)
            flexboxLayout.addView(textView, lp)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
