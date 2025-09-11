package com.mainProject.ViewModel

import android.app.Application
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.util.UnstableApi
import com.mainProject.Db.DataClassGraphic.ExerciseHomeData
import com.mainProject.Db.DataClassGraphic.PlanHomeData
import com.mainProject.Repertory.RepertoryDbSqlite
import com.mainProject.Ui.Pages.ExerciseInfo

class ViewModelMain(application: Application): AndroidViewModel(application) {
    private val repository = RepertoryDbSqlite(application.applicationContext)
    private val graphViewModel = ViewModelGraphs(repository)
    //----- ROOM DATABASE -----//

    //----- getter table -----//

    suspend fun getPlansForHome() : List<PlanHomeData> {
        val allPlans = repository.getPlans()
        val allPlansInfo: MutableList<PlanHomeData> = mutableListOf()
        allPlans.forEach { planData ->
            allPlansInfo.add(
                PlanHomeData(
                    muscleGroups = repository.getMusclesGroupsOfPlan(planData.plan_id?:1),
                    name = planData.name_plan,
                    exercice_quantity = repository.getQuantityOfExercisePerPlan(planData.plan_id?:1),
                    plan_id = planData.plan_id?:1
                )
            )
        }
        return allPlansInfo
    }

    suspend fun getExercisesForHome() : List<ExerciseHomeData>{
        val allExercises = repository.getExercises()
        val allExercisesInfo : MutableList<ExerciseHomeData> = mutableListOf()
        allExercises.forEach { exercise ->
            val dataRecordExercise = repository.getLastRecordOfExercise(exercise.exercise_id?.toInt() ?: 0)
            allExercisesInfo.add(ExerciseHomeData(
                muscle = repository.getMusclesOfExercise(exercise.exercise_id?.toInt() ?: 0),
                muscleGroup = repository.getMusclesGroupsOfExercise(exercise.exercise_id?.toInt() ?: 0),
                name = exercise.name_exercise,
                weight = dataRecordExercise.weight,
                rep_count = dataRecordExercise.rep_count,
                set_count = dataRecordExercise.set_count,
                exercise_id = exercise.exercise_id?.toInt() ?: 0
            ))
        }
        return allExercisesInfo
    }

    @OptIn(UnstableApi::class)
    suspend fun getRecordForExercise(id_exercise: Int,temporality: String,reductionType: String,dataNeeded : String) =
    graphViewModel.getRecordsOfExercise(id_exercise,temporality,reductionType,dataNeeded)

    /*suspend fun getExerciceFromSession(idSession: Int) =
        withContext(Dispatchers.IO) { repository.getExercicesFromSession(idSession) }*/


}