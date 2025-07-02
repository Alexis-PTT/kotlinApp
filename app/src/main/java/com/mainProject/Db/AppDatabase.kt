package com.mainProject.Db

import android.content.Context
import androidx.room.*
import com.mainProject.Db.Dao.ExerciceDao
import com.mainProject.Db.Dao.LinkExerciceToSessionDao
import com.mainProject.Db.Dao.MultiTableDao
import com.mainProject.Db.Dao.RecordNormalSetDao
import com.mainProject.Db.Dao.RecordSportSessionDao
import com.mainProject.Db.Dao.SportSessionDao
import com.mainProject.Db.DataClassSQL.Exercice
import com.mainProject.Db.DataClassSQL.LinkExerciceToSession
import com.mainProject.Db.DataClassSQL.RecordNormalSet
import com.mainProject.Db.DataClassSQL.RecordSportSession
import com.mainProject.Db.DataClassSQL.SportSession

@Database(entities = [ SportSession::class, Exercice::class,
    RecordSportSession::class, RecordNormalSet::class,
    LinkExerciceToSession::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun SportSession(): SportSessionDao
    abstract fun Exercice(): ExerciceDao
    abstract fun RecordNormalSet(): RecordNormalSetDao
    abstract fun RecordSportSession(): RecordSportSessionDao
    abstract fun LinkExerciceToSession(): LinkExerciceToSessionDao
    abstract fun complexQueries(): MultiTableDao

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