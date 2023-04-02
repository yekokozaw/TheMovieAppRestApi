package com.padc.themovieapp.network.dataagent

import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.network.responses.GetCreditByMovieResponse

interface MovieDataAgent {

    fun getNowPlayingMovies(
        onSuccess : (List<MovieVO>)->Unit,
        onFailure: (String) -> Unit
    )

    fun getTopRatedMovies(
        onSuccess : (List<MovieVO>)->Unit,
        onFailure: (String) -> Unit
    )

    fun getPopularMovies(
        onSuccess : (List<MovieVO>)->Unit,
        onFailure: (String) -> Unit
    )

    fun getGenres(
        onSuccess : (List<GenreVO>)->Unit,
        onFailure: (String) -> Unit
    )

    fun getMoviesByGenre(
        genreId : String,
        onSuccess : (List<MovieVO>)->Unit,
        onFailure: (String) -> Unit
    )

    fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieDetails(
        movieId : String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCreditsByMovie(
        movieId : String,
        onSuccess: (Pair<List<ActorVO>,List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    )
}