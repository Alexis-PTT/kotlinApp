package com.mainProject.ViewModel

import android.app.Application
import androidx.annotation.OptIn
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.util.UnstableApi
import com.mainProject.Db.DataClassGraphic.ExerciseHomeData
import com.mainProject.Db.DataClassGraphic.PlanHomeData
import com.mainProject.Db.DataClassSQL.Exercise
import com.mainProject.Db.DataClassSQL.LinkPlanExercise
import com.mainProject.Db.DataClassSQL.SportPlan
import com.mainProject.Repertory.RepertoryDbSqlite

class ViewModelMain(application: Application): AndroidViewModel(application) {
    private val repository = RepertoryDbSqlite(application.applicationContext)
    private val graphViewModel = ViewModelGraphs(repository)
    //------- ROOM DATABASE -------//

    //----- getter table -----//
    suspend fun getExercises()= repository.getExercises()
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
        // for each exercises it get the most recent info
        // like every muscle et muscle group present
        // and the last performance
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
    suspend fun getRecordsForGraph(temporality : String) = graphViewModel.getDateRecords(temporality)

    @OptIn(UnstableApi::class)
    suspend fun getRecordForExercise(id_exercise: Int,temporality: String,reductionType: String,dataNeeded : String) =
    graphViewModel.getRecordsOfExercise(id_exercise,temporality,reductionType,dataNeeded)


    //----- adder table -----//

    suspend fun addSportPlan(listExercisesToAdd: List<Exercise>, sportPlan: SportPlan) {
        //add the plan to the SportPlan table, the insert function return directly the id generated
        val idNewPlan : Int  = repository.addSportPlan(sportPlan);
        // after creating the plan this link each exercise to it's plan
        listExercisesToAdd.forEach { exercise ->
            // !! used to go from int?(int or null) -> int    used only in a context where you a sure that it will not be null
            // in this context we got those id directly form the base so they should be not null
            repository.addLinkPlanExercise(LinkPlanExercise(idNewPlan,exercise.exercise_id!!))
        }
    }

    //----- deleter table -----//

    suspend fun deleteExercise(){

    }

    suspend fun deletePlanExercise(){

    }

    //----- modifier table -----//

}