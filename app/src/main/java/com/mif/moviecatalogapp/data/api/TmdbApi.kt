package com.mif.moviecatalogapp.data.api

import com.mif.moviecatalogapp.data.model.Movie
import com.mif.moviecatalogapp.data.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): PopularMoviesResponse

    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Movie
}