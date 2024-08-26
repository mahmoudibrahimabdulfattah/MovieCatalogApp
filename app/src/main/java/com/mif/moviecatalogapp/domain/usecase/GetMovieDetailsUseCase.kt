package com.mif.moviecatalogapp.domain.usecase

import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.data.repository.MovieRepository
import com.mif.moviecatalogapp.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Result<Movie>> =
        repository.getMovieDetails(movieId)
            .map { Result.Success(it) as Result<Movie> }
            .onStart { emit(Result.Loading) }
            .catch { emit(Result.Error(it)) }
}