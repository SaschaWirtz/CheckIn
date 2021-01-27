package de.hdmstuttgart.checkin.db

import androidx.room.*

@Dao
interface CheckInDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCheckIn(checkInEntity: CheckInEntity)

    @Delete
    fun deleteCheckIn(checkInEntity: CheckInEntity)

    @Query("SELECT * FROM checkIns")
    fun readAllData(): List<CheckInEntity>

    @Query("DELETE FROM checkIns")
    fun nukeTable()

}