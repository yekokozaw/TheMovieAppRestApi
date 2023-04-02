package com.padc.themovieapp.network.dataagent

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.network.responses.MovieListResponse
import com.padc.themovieapp.utils.API_GET_NOW_PLAYING
import com.padc.themovieapp.utils.BASE_URL
import com.padc.themovieapp.utils.MOVIE_API_KEY
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//object MovieDataAgentImpl : MovieDataAgent {
//    override fun getNowPlayingMovies() {
//        GetNowPlayingAsyncTask().execute()
//    }

//    class GetNowPlayingAsyncTask() : AsyncTask<Void,Void,MovieListResponse?>(){
//        //work in background thread
//        override fun doInBackground(vararg params: Void?): MovieListResponse? {
//            val url: URL
//            var reader: BufferedReader? = null
//            val stringBuilder: StringBuilder
//
//            try {
//                //create the HttpURLConnection
//                url =
//                    URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
//
//                val connection = url.openConnection() as HttpURLConnection
//
//                connection.requestMethod = "GET"
//
//                connection.readTimeout = 15 * 1000 //ms
//
//                connection.doInput = true
//                connection.doOutput = false
//
//                connection.connect()
//
//                //read the output from the server
//                reader = BufferedReader(
//                    InputStreamReader(connection.inputStream)
//                )
//
//                stringBuilder = StringBuilder()
//
//                for (line in reader.readLines()) {
//                    stringBuilder.append(line + "\n")
//                }
//                val responseString = stringBuilder.toString()
//                Log.d("NowPlayingMovies", responseString)
//
//                val movieListResponse = Gson().fromJson(
//                    responseString,
//                    MovieListResponse::class.java
//                )
//
//                return movieListResponse
//            }catch (e:Exception){
//                e.printStackTrace()
//                Log.e("NewsError",e.message ?: "")
//            }finally {
//                if (reader != null){
//                    try{
//                        reader.close()
//                    }catch(ioe: IOException){
//                        ioe.printStackTrace()
//                    }
//                }
//            }
//            return null
//        }
//
//        //work in main thread
//        override fun onPostExecute(result: MovieListResponse?) {
//            super.onPostExecute(result)
//        }
//
//    }

//    override fun getNowPlayingMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//
//    }
//}