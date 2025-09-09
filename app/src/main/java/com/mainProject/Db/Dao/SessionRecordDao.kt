package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassSQL.SessionRecord


@Dao
interface RecordSportSessionDao{

    @Query("SELECT * " +
            "FROM recordSportSession " +
            "WHERE session_id = :idSession ")
    suspend fun getRecordsOfSession( idSession : Int ) : List<SessionRecord>

    @Query("SELECT start_session "+
        "FROM recordSportSession " +
        "WHERE session_id = :idSession")
    suspend fun getDateRecordsOfSession( idSession : Int ) : List<String>

    /*
    @Query("SELECT start_session " +
    "FROM recordSportSession " +
    "WHERE session_id = :idSession")
    suspend fun getDateRecordsOfExercice( idSession: Int ) : List<String>
*/

}