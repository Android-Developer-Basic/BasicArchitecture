package ru.otus.basicarchitecture.Core.Utils

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import ru.otus.basicarchitecture.MainActivity
import ru.otus.basicarchitecture.R
import javax.inject.Inject

class ProgresService @Inject constructor() {
    private var loadingDialog: Dialog? = null
    fun showLoadingDialog(context: Context) {
        if (loadingDialog == null) {
            loadingDialog = Dialog(context)
            loadingDialog?.setContentView(R.layout.dialog_loading)
            loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            loadingDialog?.setCanceledOnTouchOutside(false)
        }
        loadingDialog?.show()
    }

    fun hideLoading() {
        loadingDialog?.hide()
    }
}