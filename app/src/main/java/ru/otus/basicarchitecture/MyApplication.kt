package ru.otus.basicarchitecture

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.chip.Chip
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()


fun Fragment.navigate(route: Int){
    Navigation.findNavController(
        requireActivity(),
        R.id.navHostFragment
    ).navigate(route)
}

fun Fragment.createTagChip(context: Context, chipName: String, index: Int): Chip {
    return Chip(context).apply {
        text = chipName
        id = index
        isCheckable = true
    }
}