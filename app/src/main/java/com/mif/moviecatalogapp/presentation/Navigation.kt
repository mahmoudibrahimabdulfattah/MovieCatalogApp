package com.mif.moviecatalogapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mif.moviecatalogapp.presentation.ui.MovieDetailsScreen
import com.mif.moviecatalogapp.presentation.ui.MovieListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(onMovieClick = { movieId ->
                navController.navigate("movieDetails/$movieId")
            })
        }
        composable(
            "movieDetails/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            MovieDetailsScreen(movieId = movieId)
        }
    }
}