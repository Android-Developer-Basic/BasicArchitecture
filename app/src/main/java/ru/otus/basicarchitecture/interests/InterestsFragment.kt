package ru.otus.basicarchitecture.interests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentInterestsBinding

@AndroidEntryPoint
class InterestsFragment : Fragment() {

    private var binding: FragmentInterestsBinding? = null
    private inline fun withBinding(block: FragmentInterestsBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterestsBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {
                        buttonMoveToFragmentSummary.isEnabled = it
                    }
                }
            }

            buttonMoveToFragmentSummary.setOnClickListener {
                Log.d("AddressInfoFragment", "Next Fragment")
            }

            viewModel.defaultInterests.forEach {tag ->
                Chip(context).apply {
                    text = tag
                    isClickable = true
                    isCheckable = true
                    setOnCheckedChangeListener { buttonView, isChecked ->
                        Log.d("Test", "buttonView = ${buttonView.text} isChecked = $isChecked")
                        viewModel.updateInterest(tag)
                    }
                    chipGroupTags.addView(this)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}