package com.mainProject.Db

import android.content.Context
import androidx.room.*

import com.mainProject.Db.Dao.ExerciseDao
import com.mainProject.Db.Dao.ExerciseRecordDao
import com.mainProject.Db.Dao.LinkPlanExerciseDao
import com.mainProject.Db.Dao.MuscleDao
import com.mainProject.Db.Dao.MuscleGroupDao
import com.mainProject.Db.Dao.SessionRecordDao
import com.mainProject.Db.Dao.SportPlanDao


import com.mainProject.Db.DataClassSQL.Exercise
import com.mainProject.Db.DataClassSQL.LinkPlanExercise
import com.mainProject.Db.DataClassSQL.ExerciseRecord
import com.mainProject.Db.DataClassSQL.LinkMuscleExercise
import com.mainProject.Db.DataClassSQL.LinkMuscleGroupMuscle
import com.mainProject.Db.DataClassSQL.Muscle
import com.mainProject.Db.DataClassSQL.MuscleGroup
import com.mainProject.Db.DataClassSQL.SessionRecord
import com.mainProject.Db.DataClassSQL.SportPlan

@Database(entities = [ Exercise::class,
    SessionRecord::class, ExerciseRecord::class,
    LinkPlanExercise::class , LinkMuscleExercise::class ,
    LinkMuscleGroupMuscle::class , Muscle::class,
    MuscleGroup::class , SportPlan::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {



    abstract fun Exercise(): ExerciseDao
    abstract fun ExerciseRecord(): ExerciseRecordDao
    abstract fun SessionRecord(): SessionRecordDao
    abstract fun LinkPlanExercise(): LinkPlanExerciseDao
    abstract fun Muscle(): MuscleDao
    abstract fun MuscleGroup(): MuscleGroupDao
    abstract fun Plan(): SportPlanDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"  // Nom du fichier SQLite que tu veux utiliser
                    ).createFromAsset("databases/testData.sqlite")
                    .fallbackToDestructiveMigration()// Si tu as un fichier SQLite existant dans assets
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}