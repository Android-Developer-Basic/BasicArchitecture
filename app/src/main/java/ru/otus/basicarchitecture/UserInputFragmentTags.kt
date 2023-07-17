package ru.otus.basicarchitecture

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserInput3Binding

@AndroidEntryPoint
class UserInputFragmentTags : Fragment(R.layout.fragment_user_input3) {
    private val viewModel: UserInputViewModel by viewModels()
    private var _binding: FragmentUserInput3Binding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInput3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flexboxLayout = binding.tagContainer
        val tags = listOf("Android", "Kotlin", "Fragment", "Tag", "Cloud")
        tags.forEach { tag ->
            val textView = TextView(context).apply {
                text = tag
                textSize = 32f
                setTextColor(Color.WHITE)
                setPadding(30, 18, 30, 18)
                setBackgroundResource(R.drawable.tag_background)
                setOnClickListener {
                    it.isSelected = !it.isSelected
                    if (it.isSelected) {
                        val selectedTags = viewModel.viewState.value!!.selectedTags
                        selectedTags.add(tag)
                        viewModel.viewState.value = viewModel.viewState.value!!.copy(selectedTags = selectedTags)
                    } else {
                        val selectedTags = viewModel.viewState.value!!.selectedTags
                        selectedTags.remove(tag)
                        viewModel.viewState.value = viewModel.viewState.value!!.copy(selectedTags = selectedTags)
                    }
                }
            }
            val lp = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(48, 48, 48, 48)
            flexboxLayout.addView(textView, lp)
        }


        binding.nextButton3.setOnClickListener {
            viewModel.saveTags()
            findNavController().navigate(R.id.action_userInputFragment3_to_userInputFragment4)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
