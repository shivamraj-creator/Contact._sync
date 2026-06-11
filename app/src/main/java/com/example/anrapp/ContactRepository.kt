package com.example.anrapp

class ContactRepository
    ( private val dao: ContactDao
            )
{
    fun getContacts() =
        dao.getContacts()

    suspend fun saveContacts(
        contacts: List<ContactEntity>
    ) {
        dao.insertContacts(contacts)
    }

}