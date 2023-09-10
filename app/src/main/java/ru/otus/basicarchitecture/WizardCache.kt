package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.Date
import javax.inject.Inject

@ActivityRetainedScoped
open class WizardCache @Inject constructor() {
    open var firstName: String? = null
    open var lastName: String? = null
    open var dateOfBirth: Date? = null
    open var country: String? = null
    open var city: String? = null
    open var address: String? = null
    open var selectedTags: MutableList<String> = mutableListOf()
}

class FakeWizardCache : WizardCache() {
    override var firstName: String? = null
    override var lastName: String? = null
    override var dateOfBirth: Date? = null
    override var country: String? = null
    override var city: String? = null
    override var address: String? = null
    override var selectedTags: MutableList<String> = mutableListOf()
}