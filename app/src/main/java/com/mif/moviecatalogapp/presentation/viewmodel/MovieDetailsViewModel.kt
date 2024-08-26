package com.mif.moviecatalogapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.domain.usecase.GetMovieDetailsUseCase
import com.mif.moviecatalogapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val state: StateFlow<MovieDetailsState> = _state

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collect { result ->
                _state.value = when (result) {
                    is Result.Success -> MovieDetailsState.Success(result.data)
                    is Result.Error -> MovieDetailsState.Error(result.exception.message ?: "Unknown error occurred")
                    is Result.Loading -> MovieDetailsState.Loading
                }
            }
        }
    }
}

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Success(val movie: Movie) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}