package com.mif.moviecatalogapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mif.moviecatalogapp.R
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.presentation.viewmodel.MovieDetailsState
import com.mif.moviecatalogapp.presentation.viewmodel.MovieDetailsViewModel
import com.mif.moviecatalogapp.utils.Constant

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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetails(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GlideImage(
            model = Constant.BASE_IMAGE_URL + movie.backdropPath,
            contentDescription = "${movie.title} backdrop",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        ) {
            it
                .error(R.drawable.error_image)
                .placeholder(R.drawable.placeholder_image)
                .load(Constant.BASE_IMAGE_URL + movie.backdropPath)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = movie.title!!, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Release Date: ${movie.releaseDate}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Rating: ${movie.voteAverage}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = movie.overview!!, style = MaterialTheme.typography.bodyLarge)
    }
}