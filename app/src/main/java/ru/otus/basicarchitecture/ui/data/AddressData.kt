package ru.otus.basicarchitecture.ui.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressData @Inject constructor() {
    var query: String = ""
}

class ResponseData {
    var suggestions: List<SingleAddress> = listOf()
}

class SingleAddress {
    var value: String = ""
}
