package com.mainProject.Repertory

import android.content.Context
import com.mainProject.Db.AppDatabase
import com.mainProject.Db.DataClassSQL.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepertoryDbSqlite(context : Context) {
    private val databaseSqlite = AppDatabase.getDatabase(context)
    private val daoExercise = databaseSqlite.Exercise()
    private val daoRecordExercise = databaseSqlite.ExerciseRecord()
    private val daoSessionRecord = databaseSqlite.SessionRecord()
    private val daoLinkPlanExercise = databaseSqlite.LinkPlanExercise()
    private val daoMuscle = databaseSqlite.Muscle()
    private val daoMuscleGroup = databaseSqlite.MuscleGroup()
    private val daoPlan = databaseSqlite.Plan()

    //----- getter des tables -----//

    suspend fun getExercisesFromPlan(idPlan : Int): List<Exercise> = daoLinkPlanExercise.getExercicesFromPlan(idPlan)
    suspend fun getDatesOfSessions() : List<String> = daoSessionRecord.getDatesFromSessions()
    suspend fun getRecordsOfExercise(id_ex : Int) = daoRecordExercise.getRecordsOfExercise(id_ex)
    suspend fun getExercises() = daoExercise.getExercises()
    suspend fun getMusclesGroupsOfExercise(exercise_id : Int) = daoMuscleGroup.getMuscleGroupFromExercise(exercise_id)
    suspend fun getMusclesOfExercise(exercise_id : Int) = daoMuscle.getMuscleFromExercise(exercise_id)
    suspend fun getLastRecordOfExercise(exercise_id : Int) = daoRecordExercise.getLastRecordOfExercise(exercise_id)
    suspend fun getQuantityOfExercisePerPlan(plan_id : Int) = daoPlan.getQuantityOfExercisePerPlan(plan_id)
    suspend fun getPlans() = daoPlan.getPlans()
    suspend fun getMusclesGroupsOfPlan(plan_id : Int) = daoMuscleGroup.getMusclesGroupsFromPlan(plan_id)

    //----- setter des tables -----//
}