package com.mainProject.navigate



import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mainProject.Db.DataClassGraphic.SessionPlanData
import com.mainProject.Ui.Pages.Home
import com.mainProject.Ui.Pages.SessionPlan
import com.mainProject.Ui.Pages.WorkoutDataEntry
import com.mainProject.ViewModel.ViewModelMain
import com.patrykandpatrick.vico.sample.compose.test2

@Composable
fun NavMain (navController: NavHostController){
    val homeComposes = Home()
    val sessionPlanCompose = SessionPlan()
    val wDataEntry = WorkoutDataEntry()
    val test22 = test2()
    val Ses = "sessionInfoPage"
    val S_ID = "exerciceInfoPage"
    val viewModelMain : ViewModelMain = viewModel()


    NavHost(navController = navController, startDestination = "test1234") {
        composable("home") {
            homeComposes.mainPage(viewModelMain,navController)
        }
        composable(route = "sessionInfoPage/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) {
                backStackEntry ->
                sessionPlanCompose.listExercice(backStackEntry.arguments?.getString("id")!!,viewModelMain)
        }
        composable(route = "exerciceInfoPage/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) {
                backStackEntry ->
            sessionPlanCompose.listExercice(backStackEntry.arguments?.getString("id")!!,viewModelMain)
        }
        composable("workoutDataEntry") {
            wDataEntry.dataEntryMain()
        }
        composable("test1234") {
            test22.Preview()
        }
    }
}