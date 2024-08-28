package com.mif.moviecatalogapp.data.repository

import com.mif.moviecatalogapp.data.api.TmdbApi
import com.mif.moviecatalogapp.data.db.MovieDao
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.utils.Constant
import com.mif.moviecatalogapp.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: TmdbApi,
    private val movieDao: MovieDao,
    private val networkUtils: NetworkUtils
) {
    fun getPopularMovies(): Flow<List<Movie>> = flow {
        // First, emit data from local database
        val localMovies = movieDao.getAllMovies().first()
        emit(localMovies)

        // Then, if there's an internet connection, fetch from API
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = api.getPopularMovies(Constant.API_KEY)
                movieDao.insertMovies(response.results)
                emit(response.results)
            } catch (e: Exception) {
                // If API call fails, we've already emitted local data, so just log the error
                e.printStackTrace()
            }
        }
    }

    fun getMovieDetails(movieId: Int): Flow<Movie> = flow {
        try {
            val remoteMovie = api.getMovieDetails(movieId, API_KEY)
            movieDao.insertMovie(remoteMovie)
            emit(remoteMovie)
        } catch (e: Exception) {
            val localMovie = movieDao.getMovieById(movieId).first()
            if (localMovie != null) {
                emit(localMovie)
            } else {
                throw e
            }
        }
    }

    companion object {
        private const val API_KEY = Constant.API_KEY
    }
}