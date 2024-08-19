package ru.otus.basicarchitecture.name

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi

class DateMask(private var viewModel: NameFragmentViewModel) : TextWatcher {
    private var ignore = false
    private var sb = StringBuilder()
    private val numPlace = '#'
    private val template = "##.##.####"

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun afterTextChanged(editable: Editable?) {
        viewModel.clearValidation()
        if (!ignore) {
            removeFormat(editable.toString());
            applyFormat(sb.toString());
            ignore = true;
            editable?.replace(0, editable.toString().length, sb.toString());
            ignore = false;
            viewModel.date.value = editable.toString()
        }
    }

    private fun removeFormat(text: String) {
        sb.setLength(0)
        for (element in text) {
            if (isNumberChar(element)) {
                sb.append(element)
            }
        }
    }

    private fun applyFormat(text: String) {
        sb.setLength(0)
        var i = 0
        var textIndex = 0
        while (i < template.length && textIndex < text.length) {
            if (template[i] == numPlace) {
                sb.append(text[textIndex])
                textIndex++
            } else {
                sb.append(template[i])
            }
            i++
        }
    }
    private fun isNumberChar(c: Char): Boolean = c in '0'..'9'
}
