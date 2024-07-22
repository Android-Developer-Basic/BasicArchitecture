package ru.otus.basicarchitecture.Core.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import android.widget.Toast
import ru.otus.basicarchitecture.R
import javax.inject.Inject

class ErrorService @Inject constructor() {
    private var context: Context? = null
    fun show(message: String) {
        val title = "Ошибка"
        if (context!= null) {
            Toast.makeText(context,message ,Toast.LENGTH_SHORT).show();

        }

    }

    fun setContext(context: Context?) {
        this.context = context
    }
}