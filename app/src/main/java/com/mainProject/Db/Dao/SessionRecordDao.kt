package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.SessionRecord


@Dao
interface SessionRecordDao{
    @Query("SELECT start_session " +
            "FROM sessionRecord ")
    suspend fun getDatesFromSessions(): List<String>


}