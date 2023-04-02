//package com.padc.themovieapp.network.dataagent
//
//import android.util.Log
//import com.padc.themovieapp.data.vos.ActorVO
//import com.padc.themovieapp.data.vos.GenreVO
//import com.padc.themovieapp.data.vos.MovieVO
//import com.padc.themovieapp.network.responses.MovieListResponse
//import com.padc.themovieapp.network.TheMovieApi
//import com.padc.themovieapp.network.responses.GetActorsResponse
//import com.padc.themovieapp.network.responses.GetCreditByMovieResponse
//import com.padc.themovieapp.network.responses.GetGenresResponse
//import com.padc.themovieapp.utils.BASE_URL
//import okhttp3.OkHttpClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//object RetrofitDataAgentImpl : MovieDataAgent {
//
//    private var mTheMovieApi : TheMovieApi? = null
//
//    init {
//        val mOkHttpClient = OkHttpClient.Builder()
//            .connectTimeout(15,TimeUnit.SECONDS)
//            .readTimeout(15,TimeUnit.SECONDS)
//            .writeTimeout(15,TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(mOkHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        mTheMovieApi = retrofit.create(TheMovieApi::class.java)
//    }
//
//    override fun getNowPlayingMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getNowPlayingMovies()
//            ?.enqueue(object : Callback<MovieListResponse>{
//                override fun onResponse(
//                    call: Call<MovieListResponse>,
//                    response: Response<MovieListResponse>
//                ) {
//                    if(response.isSuccessful){
//                        val movieList = response.body()?.results ?: listOf()
//                        onSuccess(movieList)
//                        Log.d("MyTag", "This is a successful log message.")
//                    }
//                    else{
//                        onFailure(response.message())
//                        Log.d("MyTag", "This is a fail log message.")
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getTopRatedMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getTopRatedMovies()
//            ?.enqueue(object : Callback<MovieListResponse>{
//                override fun onResponse(
//                    call: Call<MovieListResponse>,
//                    response: Response<MovieListResponse>
//                ) {
//                    if(response.isSuccessful){
//                        val movieList = response.body()?.results ?: listOf()
//                        onSuccess(movieList)
//                        Log.d("MyTag", "This is a successful log message.")
//                    }
//                    else{
//                        onFailure(response.message())
//                        Log.d("MyTag", "This is a fail log message.")
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
//        mTheMovieApi?.getPopularMovies()
//            ?.enqueue(object : Callback<MovieListResponse>{
//                override fun onResponse(
//                    call: Call<MovieListResponse>,
//                    response: Response<MovieListResponse>
//                ) {
//                    if(response.isSuccessful){
//                        val movieList = response.body()?.results ?: listOf()
//                        onSuccess(movieList)
//                        Log.d("MyTag", "This is a successful log message.")
//                    }
//                    else{
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//            })
//    }
//
//    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
//        mTheMovieApi?.getGenres()
//            ?.enqueue(object: Callback<GetGenresResponse>{
//                override fun onResponse(
//                    call: Call<GetGenresResponse>,
//                    response: Response<GetGenresResponse>
//                ) {
//                    if(response.isSuccessful){
//                        val movieList = response.body()?.genres ?: listOf()
//                        onSuccess(movieList)
//                    }
//                    else{
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<GetGenresResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//            })
//    }
//
//    override fun getMoviesByGenre(
//        genreId: String,
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getMoviesByGenres(genreId = genreId)
//            ?.enqueue(object: Callback<MovieListResponse>{
//                override fun onResponse(
//                    call: Call<MovieListResponse>,
//                    response: Response<MovieListResponse>
//                ) {
//                    if(response.isSuccessful){
//                        val movieList = response.body()?.results ?: listOf()
//                        onSuccess(movieList)
//                    }else{
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
//        mTheMovieApi?.getActors()
//            ?.enqueue(object: Callback<GetActorsResponse>{
//                override fun onResponse(
//                    call: Call<GetActorsResponse>,
//                    response: Response<GetActorsResponse>
//                ) {
//                    if(response.isSuccessful){
//                        onSuccess(response.body()?.results ?: listOf())
//                    }else{
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<GetActorsResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getMovieDetails(
//        movieId: String,
//        onSuccess: (MovieVO) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getMovieDetails(movieId = movieId)
//            ?.enqueue(object: Callback<MovieVO>{
//                override fun onResponse(
//                    call: Call<MovieVO>,
//                    response: Response<MovieVO>
//                ) {
//                    if(response.isSuccessful){
//                        response.body()?.let {
//                            onSuccess(it)
//                        }
//                    }else{
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieVO>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getCreditsByMovie(
//        movieId: String,
//        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getCreditByMovieDetails(movieId= movieId)
//            ?.enqueue(object: Callback<GetCreditByMovieResponse>{
//                override fun onResponse(
//                    call: Call<GetCreditByMovieResponse>,
//                    response: Response<GetCreditByMovieResponse>
//                ) {
//                    if(response.isSuccessful){
//                        response.body()?.let {
//                            onSuccess(Pair(it.cast ?: listOf(),it.crew ?: listOf()))
//                        }
//                    }else{
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<GetCreditByMovieResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//}