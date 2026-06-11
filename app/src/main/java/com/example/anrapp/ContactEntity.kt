package com.example.anrapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    val id :Long,
    val name: String,
    val phoneNumber: String

)