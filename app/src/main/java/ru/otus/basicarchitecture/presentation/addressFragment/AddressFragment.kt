import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.FragmentAddressBinding
import ru.otus.basicarchitecture.presentation.interestsFragment.InterestsFragment

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, emptyList<String>())
        binding.autoCompleteTextViewAddress.setAdapter(adapter)

        binding.autoCompleteTextViewAddress.addTextChangedListener { text ->
            val query = text.toString()
            if (query.length > 3) {
                viewModel.fetchAddressSuggestions(query) { suggestions ->
                    adapter.clear()
                    adapter.addAll(suggestions)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.buttonNext.setOnClickListener {
            viewModel.address = binding.autoCompleteTextViewAddress.text.toString()
            if (viewModel.saveData()) {
                navigateToNextFragment()
            }
        }
    }

    private fun navigateToNextFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, InterestsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}