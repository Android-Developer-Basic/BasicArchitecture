package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.model.Tag
import ru.otus.basicarchitecture.repository.TagRepository

class Fragment3ViewModel : ViewModel() {
    private val repository = TagRepository()

    private val _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> get() = _tags

    private val _selectedTags = MutableLiveData<Set<Tag>>(setOf())
    val selectedTags: LiveData<Set<Tag>> get() = _selectedTags

    init {
        loadTags()
    }

    private fun loadTags() {
        _tags.value = repository.getTags()
    }

    fun toggleTagSelection(tag: Tag) {
        val currentSelection = _selectedTags.value ?: setOf()
        if (currentSelection.contains(tag)) {
            _selectedTags.value = currentSelection - tag
        } else {
            _selectedTags.value = currentSelection + tag
        }
    }
}