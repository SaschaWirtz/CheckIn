package de.hdmstuttgart.checkin.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CheckInEntity::class], version = 1, exportSchema = false)
abstract class CheckInDatabase: RoomDatabase() {
    //Creating database model with room annotation
    abstract fun checkInDao() :CheckInDao

}