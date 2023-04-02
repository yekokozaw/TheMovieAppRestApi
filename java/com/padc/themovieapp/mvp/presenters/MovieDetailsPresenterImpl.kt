package com.padc.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.mvp.views.MovieDetailsView

class MovieDetailsPresenterImpl : MovieDetailsPresenter ,ViewModel() {

    //Model
    private val mMovieModel = MovieModelImpl

    private var mView : MovieDetailsView? = null

    override fun initView(view: MovieDetailsView) {
        mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {
        mMovieModel.getMovieDetails(
            movieId = movieId.toString(),
            onFailure = { mView?.showError(it)}
        )?.observe(owner){
            it?.let {
                mView?.showMovieDetails(it)
            }
        }

        //Credits
        mMovieModel.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                mView?.showCreditsByMovie(it.first,it.second)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }

    override fun onUiReady(owner: LifecycleOwner) { }
}