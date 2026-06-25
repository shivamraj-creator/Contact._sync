package com.example.anrapp

import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log

class ContactReader(
    private val contentResolver: ContentResolver,
) {
    companion object {
        private const val TAG = "ContactReader"
    }

    fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC",
        )

        cursor?.use {
            val idIndex = it.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            )
            val nameIndex = it.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            )
            val phoneIndex = it.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
            )

            while (it.moveToNext()) {
                val contact = Contact(
                    id = it.getLong(idIndex),
                    name = it.getString(nameIndex),
                    phoneNumber = it.getString(phoneIndex),
                )
                contacts.add(contact)
            }
        }

        Log.d(TAG, "Read ${contacts.size} contacts from device")
        return contacts
    }
}
