package com.mif.moviecatalogapp.data.repository

import com.mif.moviecatalogapp.data.api.TmdbApi
import com.mif.moviecatalogapp.data.db.MovieDao
import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: TmdbApi,
    private val movieDao: MovieDao
) {
    fun getPopularMovies(): Flow<List<Movie>> = flow {
        // Local Data First
        if (movieDao.getAllMovies().first().isNotEmpty()) {
            emit(movieDao.getAllMovies().first())
            println("Fetched movies from local database")
        }

        // Remote Data
        try {
            println("Fetching popular movies...")
            val response = api.getPopularMovies(Constant.API_KEY)
            println("Fetched ${response.results.size} movies")
            movieDao.insertMovies(response.results)
            emit(response.results)
        } catch (e: Exception) {
            println("Error fetching popular movies: ${e.message}")
            e.printStackTrace()
            println("Falling back to local database...")
            emit(movieDao.getAllMovies().first())
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