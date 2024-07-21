package ru.otus.basicarchitecture.Core.Utils

import android.text.Editable

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)