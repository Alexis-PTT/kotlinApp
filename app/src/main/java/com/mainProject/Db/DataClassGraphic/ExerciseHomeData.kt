package com.mainProject.Db.DataClassGraphic

data class ExerciseHomeData(
    val muscle: List<String>,
    val muscleGroup: List<String>,
    val name: String,
    val weight: Double,
    val rep_count: Int,
    val set_count: Int,
    val exercise_id: Int)


