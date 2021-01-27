package de.hdmstuttgart.checkin.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "checkIns")
data class CheckInEntity(
    @PrimaryKey
    val CheckInDate: String,
    val NfcTag: String
)
