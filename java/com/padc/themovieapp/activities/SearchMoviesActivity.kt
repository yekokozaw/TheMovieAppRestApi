package com.padc.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.padc.themovieapp.R
import com.padc.themovieapp.adapters.MovieAdapter
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.delegates.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.activity_movie_search.*
import java.util.concurrent.TimeUnit

class SearchMoviesActivity : AppCompatActivity(),MovieViewHolderDelegate {

    private lateinit var mMovieAdapter: MovieAdapter
    private val mMovieModel:MovieModel = MovieModelImpl

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchMoviesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        setUpListeners()
        setUpRecyclerView()
    }

    override fun onTapMovie(movieId: Int) {

    }

    private fun setUpListeners(){
        edtSearch.textChanges()
            .debounce ( 500L, TimeUnit.MILLISECONDS)
            .flatMap {
                mMovieModel.searchMovies(it.toString())
            }
            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({
                mMovieAdapter.setNewData(it)
            },{
                Snackbar.make(window.decorView,it.localizedMessage,Snackbar.LENGTH_LONG).show()
            })
    }

    private fun setUpRecyclerView(){
        mMovieAdapter = MovieAdapter(this)
        rvMovies.adapter = mMovieAdapter
        rvMovies.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
    }
}