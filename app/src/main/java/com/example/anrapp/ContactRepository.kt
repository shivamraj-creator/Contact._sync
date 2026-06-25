package com.example.anrapp

import android.content.ContentResolver
import com.example.anrapp.mapper.toEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ContactRepository(
    private val dao: ContactDao,
) {

    fun getContacts(): Flow<List<ContactEntity>> = dao.getContacts()

    suspend fun syncFromDevice(contentResolver: ContentResolver): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val deviceContacts = ContactReader(contentResolver).getContacts()
                dao.insertContacts(deviceContacts.toEntities())
            }
        }
    }
}
