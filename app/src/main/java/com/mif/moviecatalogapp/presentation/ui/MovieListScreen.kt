package com.mif.moviecatalogapp.presentation.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.presentation.viewmodel.MovieListState
import com.mif.moviecatalogapp.presentation.viewmodel.MovieListViewModel
import com.mif.moviecatalogapp.utils.Constant

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is MovieListState.Loading -> LoadingScreen()
        is MovieListState.Success -> MovieList(
            movies = currentState.movies,
            onMovieClick = onMovieClick
        )

        is MovieListState.Error -> ErrorScreen(message = currentState.message)
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun MovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {

    val imageUrl = Constant.BASE_IMAGE_URL+movie.posterPath

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "${movie.title} poster",
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                onError = { errorState ->
                    Log.e("MovieItem", "Error loading image: ${errorState.result.throwable}")
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = movie.title!!, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.overview!!,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
    }
}