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
import ru.otus.basicarchitecture.databinding.Fragment1Binding
import ru.otus.basicarchitecture.viewModel.viewModelFR1


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
                goToFR2.isEnabled = state.dataOK  //должна блокировать или не блокировать кнопку
                name.setTextKeepState(state.name.orEmpty())          // есть ли в этом смысл? наверно нет т.к editText
                surName.setTextKeepState(state.surName.orEmpty())    // Ответ: и да, и нет. С точки зрения единого источника правды (модели) есть.
                data.setTextKeepState(state.data.orEmpty())          // Кроме того, при возврате на этот экран, мы заполним его данными из визард-кеш
            }

            goToFR2.setOnClickListener{
                // ОЧЕНЬ не рекомендуется прокидывать контекст активити в модель
                // Помним, что модель живет ДОЛЬШЕ активити.
                findNavController().navigate(R.id.action_fragment1_to_fragment2)
            }

            name.doOnTextChanged { text, _, _, _ ->
                // В модель записываем каждое изменение текста
                dataModel.setName(text.toString())  // запись имени в модель?
            }
            surName.doOnTextChanged { text, _, _, _ ->
                dataModel.setSurname(text.toString())
            }
            data.doOnTextChanged { text, _, _, _ ->
                dataModel.setDate(text.toString())
            }
        }
    }



}