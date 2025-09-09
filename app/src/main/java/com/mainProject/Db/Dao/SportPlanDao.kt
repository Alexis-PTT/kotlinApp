package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SportSessionDao {

    @Query("SELECT * FROM sportSession")
    suspend fun getAllSessions(): List<SportSession>

}