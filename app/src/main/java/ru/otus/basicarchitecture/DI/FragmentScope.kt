package ru.otus.basicarchitecture.DI

import javax.inject.Scope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope()