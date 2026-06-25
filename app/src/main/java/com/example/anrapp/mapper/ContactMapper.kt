package com.example.anrapp.mapper

import com.example.anrapp.Contact
import com.example.anrapp.ContactEntity

fun Contact.toEntity(): ContactEntity {
    return ContactEntity(
        id = id,
        name = name.orEmpty(),
        phoneNumber = phoneNumber.orEmpty(),
    )
}

fun List<Contact>.toEntities(): List<ContactEntity> = map { it.toEntity() }
