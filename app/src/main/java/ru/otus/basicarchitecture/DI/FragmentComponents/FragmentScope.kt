package ru.otus.basicarchitecture.DI.FragmentComponents

import javax.inject.Scope

//Синглтон внутри сабкомпонентов для фрагментов
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope()
