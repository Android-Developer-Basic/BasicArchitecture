package ru.otus.basicarchitecture.Core.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import ru.otus.basicarchitecture.R
import javax.inject.Inject

class ErrorService @Inject constructor() {
    private var alert: AlertDialog? = null
    fun show(message: String, context: Context) {
        val title = "Ошибка"
        if (alert == null) {
            alert = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
                .create()

            alert?.setCancelable(false)
        }

        alert?.show()
    }
}