package ru.otus.basicarchitecture

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.Fragment2Binding
import ru.otus.basicarchitecture.viewModel.viewModelFR2
import java.util.SplittableRandom

@AndroidEntryPoint
class Fragment2 : Fragment() {

    private var binding: Fragment2Binding? = null

    private inline fun withBinding(block: Fragment2Binding.() -> Unit) {
        binding?.block()
    }

    private val dataModel: viewModelFR2 by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?, ): View {
        binding = Fragment2Binding.inflate(layoutInflater, container, false)
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
            // загрузка из viewModel элементов в view
            dataModel.viewState.observe(viewLifecycleOwner) { state ->

                val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, state.variantAddress)
                LIST.threshold = 2
                LIST.setAdapter(adapter)



//                viewCountry.setTextKeepState(state.address.orEmpty())
//                inrernetAdress.setTextKeepState(state.variantAddress.joinToString("\n"))
//                viewCity.setTextKeepState(state.city.orEmpty())
//                viewAdress.setTextKeepState(state.address.orEmpty())



                LIST.setTextKeepState(state.address.orEmpty())
        }




            goToFR3.setOnClickListener{
                // ОЧЕНЬ не рекомендуется прокидывать контекст активити в модель
                // Помним, что модель живет ДОЛЬШЕ активити.
                findNavController().navigate(R.id.action_fragment2_to_fragment3)
            }

            LIST.doOnTextChanged{text, _, _, _ ->
                dataModel.setAddress(text.toString())
            }

//            viewCountry.doOnTextChanged { text, _, _, _ ->
//                // В модель записываем каждое изменение текста
//                dataModel.setAddress(text.toString())
//            }
//            viewCity.doOnTextChanged { text, _, _, _ ->
//                dataModel.setCity(text.toString())
//            }
//            viewAdress.doOnTextChanged { text, _, _, _ ->
//                dataModel.setAddress(text.toString())
//            }
        }

    }


}