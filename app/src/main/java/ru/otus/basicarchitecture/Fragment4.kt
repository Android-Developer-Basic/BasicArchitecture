package ru.otus.basicarchitecture

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.WizardCache
import ru.otus.basicarchitecture.databinding.Fragment4Binding
import javax.inject.Inject

@AndroidEntryPoint
class Fragment4 : Fragment() {

    @Inject
    lateinit var  cache: WizardCache

    private var binding: Fragment4Binding? = null

    private inline fun withBinding(block: Fragment4Binding.() -> Unit) {
        binding?.block()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = Fragment4Binding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {

        super.onDestroyView()
        binding = null
    }


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {

            textView2.setTextKeepState(cache.name)
            textView4.setTextKeepState(cache.surName)
            textView6.setTextKeepState(cache.bd.toString())
            textView8.setTextKeepState("${cache.address}")  //${cache.city} ${cache.address}

            textView10.setText(cache.tags.filterValues { it }.keys.joinToString())
        }



    }

}