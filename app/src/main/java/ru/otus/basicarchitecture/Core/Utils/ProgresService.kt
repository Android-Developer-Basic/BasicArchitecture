package ru.otus.basicarchitecture.Core.Utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import ru.otus.basicarchitecture.R
import javax.inject.Inject


class ProgresService @Inject constructor() {
    private var loadingDialog: Dialog? = null
    private var context: Context? = null

    init {

    }

    fun showLoadingDialog() {
        if (context != null) {
            loadingDialog = Dialog(context!!)
            loadingDialog?.setContentView(R.layout.dialog_loading)
            loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            loadingDialog?.setCanceledOnTouchOutside(false)
            loadingDialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        val customLayout = View.inflate(context, R.layout.fragment2, null)
        val editText: EditText = customLayout.findViewById(R.id.addressField)

        loadingDialog?.show()
        editText.requestFocus()
    }

    fun hideLoading() {
        loadingDialog?.hide()
        loadingDialog = null
    }

    fun setContext(context: Context?) {
        this.context = context
    }
}