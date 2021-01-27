package de.hdmstuttgart.checkin.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CheckInEntity::class], version = 1, exportSchema = false)
abstract class CheckInDatabase: RoomDatabase() {

    abstract fun checkInDao() :CheckInDao

}