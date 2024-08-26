package com.mif.moviecatalogapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.presentation.viewmodel.MovieDetailsState
import com.mif.moviecatalogapp.presentation.viewmodel.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    when (val currentState = state) {
        is MovieDetailsState.Loading -> LoadingScreen()
        is MovieDetailsState.Success -> MovieDetails(movie = currentState.movie)
        is MovieDetailsState.Error -> ErrorScreen(message = currentState.message)
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = movie.title!!, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Release Date: ${movie.releaseDate}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Rating: ${movie.voteAverage}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = movie.overview!!, style = MaterialTheme.typography.bodyLarge)
    }
}