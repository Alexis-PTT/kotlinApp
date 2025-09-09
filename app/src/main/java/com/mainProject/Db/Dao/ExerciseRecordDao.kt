package com.mainProject.Db.Dao

import androidx.room.Dao
import androidx.room.Query
import com.mainProject.Db.DataClassGraphic.ExerciseRecordData


@Dao
interface ExerciseRecordDao {

    @Query("SELECT weight,rep_count,set_count,start_session " +
            "FROM exerciseRecord,sessionRecord " +
            "WHERE exerciseRecord.id_session = sessionRecord.session_id " +
            "AND id_exercise = :id_exercise ")
    suspend fun getRecordsOfExercise( id_exercise : Int ) : List<ExerciseRecordData>

    @Query("SELECT weight,rep_count,set_count,start_session " +
            "FROM exerciseRecord,sessionRecord " +
            "WHERE exerciseRecord.id_session = sessionRecord.session_id " +
            "AND id_exercise = :id_exercise "+
            "AND start_session > :minDate ")
    suspend fun getRecordsOfExerciseAfterXDate( id_exercise : Int , minDate : String ) : List<ExerciseRecordData>


    @Query("SELECT weight,rep_count,set_count,start_session " +
            "FROM exerciseRecord,sessionRecord " +
            "WHERE exerciseRecord.id_session = sessionRecord.session_id " +
            "AND id_exercise = :id_exercise "+
            "ORDER BY start_session DESC " +
            "LIMIT 1 ")
    suspend fun getLastRecordOfExercise( id_exercise : Int ) : List<ExerciseRecordData>

    //TODO : MAYBE a could do a new button for the graphs of the sessions
    // where the data could be the poucentage of progression,
    // it would a average of progression on each exercise compare to the last record (weight,set,rep) it has
    /*@Query("SELECT weight,repetition,set_count,start_session " +
            "FROM recordNormalSet,recordSportSession " +
            "WHERE recordNormalSet.id_record_session = recordSportSession.id_record_session " +
            "AND recordNormalSet.id_ex = :id_exercise " +
            "ORDER BY start_session DESC " +
            "LIMIT 2 ")
    suspend fun test( id_exercise : Int , minDate : String ) : List<exerciseRecordData>*/
}