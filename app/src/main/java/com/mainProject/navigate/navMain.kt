package com.mainProject.navigate



import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mainProject.Ui.Pages.Home
import com.mainProject.Ui.Pages.SessionInfo
import com.mainProject.NotUsedForNow.WorkoutDataEntry
import com.mainProject.NotUsedForNow.test23
import com.mainProject.Ui.ComposablesForPages.GraphMultiLineXDateYInt
import com.mainProject.ViewModel.ViewModelMain
import com.mainProject.Ui.Pages.ExerciseInfo

@Composable
fun NavMain (navController: NavHostController){
    val homePage = Home()
    val sessionInfoPage = SessionInfo()
    val exerciseInfoPage = ExerciseInfo()
    val wDataEntry = WorkoutDataEntry()
    val test1234 = test23()
    val test2345 = GraphMultiLineXDateYInt()

    val viewModelMain : ViewModelMain = viewModel()


    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            homePage.mainPage(viewModelMain,navController)
        }
        composable(route = "sessionInfoPage/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) {
                backStackEntry ->
                sessionInfoPage.listExercice(backStackEntry.arguments?.getString("id")!!,viewModelMain,navController)
        }
        composable(route = "exerciseInfoPage/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) {
                backStackEntry ->
            exerciseInfoPage.listExercice(backStackEntry.arguments?.getString("id")!!,viewModelMain)
        }
        composable("workoutDataEntry") {
            wDataEntry.dataEntryMain()
        }
        composable ("rien2"){
            //test2345.lineChartMain(viewModelMain,"Mois","7","","moyenne")
            exerciseInfoPage.listExercice("1",viewModelMain)
        }

    }
}