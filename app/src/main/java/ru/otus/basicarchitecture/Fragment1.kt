package ru.otus.basicarchitecture

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.Fragment1Binding

import ru.otus.basicarchitecture.viewModel.viewModelFR1


@AndroidEntryPoint
class Fragment1 : Fragment() {

    private var binding: Fragment1Binding? = null

    /**
     * Удобная функция, чтобы не проверять каждый раз на нулл
     */
    private inline fun withBinding(block: Fragment1Binding.() -> Unit) {
        binding?.block()
    }

    private val dataModel: viewModelFR1 by viewModels() // Время жизни модели - фрагмент!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = Fragment1Binding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        // Смотри сюда: https://developer.android.com/topic/libraries/view-binding#fragments
        super.onDestroyView()
        binding = null
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {
            dataModel.viewState.observe(viewLifecycleOwner) { state ->
                goToFR2.isEnabled = state.dataOK                     //должна блокировать или не блокировать кнопку
                viewName.setTextKeepState(state.name.orEmpty())          // загрузка из viewModel элементов в view
                viewSurName.setTextKeepState(state.surName.orEmpty())    //
                viewData.setTextKeepState(state.data.orEmpty())          //
                errorMessage.setTextKeepState(state.dataError.orEmpty())
            }

            goToFR2.setOnClickListener{
                // ОЧЕНЬ не рекомендуется прокидывать контекст активити в модель
                // Помним, что модель живет ДОЛЬШЕ активити.
                findNavController().navigate(R.id.action_fragment1_to_fragment2)
            }

            viewName.doOnTextChanged { text, _, _, _ ->  //какая то стандартная лямбда
                // В модель записываем каждое изменение текста
                dataModel.setName(text.toString())
            }
            viewSurName.doOnTextChanged { text, _, _, _ ->
                dataModel.setSurname(text.toString())
            }
            viewData.doOnTextChanged { text, _, _, _ ->
                dataModel.setDate(text.toString())
            }
        }
    }



}