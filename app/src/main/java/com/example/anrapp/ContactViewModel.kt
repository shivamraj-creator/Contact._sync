package com.example.anrapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class ContactViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    val contacts =
        repository.getContacts().asLiveData()
}