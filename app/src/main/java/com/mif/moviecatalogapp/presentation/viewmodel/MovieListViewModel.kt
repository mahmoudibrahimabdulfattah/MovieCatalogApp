package com.mif.moviecatalogapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.domain.usecase.GetPopularMoviesUseCase
import com.mif.moviecatalogapp.domain.util.Result
import com.mif.moviecatalogapp.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _state = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val state: StateFlow<MovieListState> = _state

    init {
        loadMovies()
        observeNetworkChanges()
    }

    private fun observeNetworkChanges() {
        viewModelScope.launch {
            networkUtils.observeNetworkStatus()
                .distinctUntilChanged()
                .collect { isConnected ->
                    if (isConnected) {
                        loadMovies()
                    }
                }
        }
    }

    fun refreshMovies() {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collect { result ->
                _state.value = when (result) {
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            MovieListState.Error("No movies found")
                        } else {
                            MovieListState.Success(result.data)
                        }
                    }
                    is Result.Error -> MovieListState.Error(result.exception.message ?: "Unknown error occurred")
                    is Result.Loading -> MovieListState.Loading
                }
            }
        }
    }
}

sealed class MovieListState {
    object Loading : MovieListState()
    data class Success(val movies: List<Movie>) : MovieListState()
    data class Error(val message: String) : MovieListState()
}