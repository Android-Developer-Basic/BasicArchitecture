package ru.otus.basicarchitecture.presentation.fragments

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun EditText.textChanges(): Flow<String> {
    return callbackFlow{
        val listener = doOnTextChanged { text, _, _, _ ->
            trySend(text.toString())
        }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text.toString()) }
}