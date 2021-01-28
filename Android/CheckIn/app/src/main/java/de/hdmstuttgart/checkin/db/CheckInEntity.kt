package de.hdmstuttgart.checkin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//Defining and creating the table with the room framework for the sql database
@Entity(tableName = "checkIns")
data class CheckInEntity(
    @PrimaryKey
    val CheckInDate: String,
    val NfcTag: String
)
