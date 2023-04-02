package com.padc.themovieapp.network.dataagent
//
//import android.app.DownloadManager
//import android.os.AsyncTask
//import com.google.gson.Gson
//import com.padc.themovieapp.data.vos.MovieVO
//import com.padc.themovieapp.network.responses.MovieListResponse
//import com.padc.themovieapp.utils.API_GET_NOW_PLAYING
//import com.padc.themovieapp.utils.BASE_URL
//import com.padc.themovieapp.utils.MOVIE_API_KEY
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import java.util.concurrent.TimeUnit
//
//object OkHttpDataAgentImpl : MovieDataAgent {
//
//    private val mClient : OkHttpClient = OkHttpClient.Builder()
//        .connectTimeout(15,TimeUnit.SECONDS)
//        .readTimeout(15,TimeUnit.SECONDS)
//        .writeTimeout(15,TimeUnit.SECONDS)
//        .build()
//
//    override fun getNowPlayingMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        GetNowPlayingMovieOkHttpTask(mClient).execute()
//    }
//
//    class GetNowPlayingMovieOkHttpTask(
//        private val mOkHttpClient: OkHttpClient
//    ) :
//        AsyncTask<Void,Void,MovieListResponse>(){
//        override fun doInBackground(vararg voids: Void?): MovieListResponse? {
//
//            val request = Request.Builder()
//                .url("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
//                .build()
//
//            try {
//                val response = mOkHttpClient.newCall(request).execute()
//
//                if (response.isSuccessful){
//                    response.body?.let{
//                        val responseString = it.string()
//                        val response = Gson().fromJson<MovieListResponse>(
//                            responseString,
//                            MovieListResponse::class.java
//                        )
//                        return response
//                    }
//                }
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: MovieListResponse?) {
//            super.onPostExecute(result)
//        }
//    }
//}