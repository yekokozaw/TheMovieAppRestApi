package com.padc.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.padc.themovieapp.R
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.mvp.presenters.MovieDetailsPresenter
import com.padc.themovieapp.mvp.presenters.MovieDetailsPresenterImpl
import com.padc.themovieapp.mvp.views.MovieDetailsView
import com.padc.themovieapp.mvvm.MovieDetailsViewModel
import com.padc.themovieapp.utils.IMAGE_BASE_URL
import com.padc.themovieapp.viewpods.ActorListViewPod
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity() ,MovieDetailsView{

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    //View Pods
    lateinit var actorsViewPods : ActorListViewPod
    lateinit var creatorsViewPod : ActorListViewPod

    //Presenter
    //private lateinit var mPresenter : MovieDetailsPresenter

    //View Model
    private lateinit var mViewModel : MovieDetailsViewModel

    //Model
    //private val mMovieModel : MovieModel = MovieModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //setUpPresenter()
        setUpViewPods()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID,0)
        movieId?.let {
           // mPresenter.onUiReadyInMovieDetails(this,movieId)
            setUpViewModel(it)
        }

        observeLiveData()
    }

    private fun setUpViewModel(movieId: Int) {
        mViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        mViewModel.getInitialData(movieId)
    }

    private fun observeLiveData(){
        mViewModel.movieDetailsLiveData?.observe(this){
            it?.let { movie -> bindData(movie) }
        }
        mViewModel.castLiveData?.observe(this, actorsViewPods::setData)
        mViewModel.crewLiveData?.observe(this,creatorsViewPod::setData)
    }
//    private fun setUpPresenter() {
//        mPresenter = ViewModelProvider(this)[MovieDetailsPresenterImpl::class.java]
//        mPresenter.initView(this)
//    }

//    private fun requestData(movieId: Int) {
//        mMovieModel.getMovieDetails(
//            movieId = movieId.toString(),
//            onFailure = { showError(it)}
//        )?.observe(this, Observer {
//            it?.let { movieDetails -> bindData(movieDetails) }
//        })
//
//        mMovieModel.getCreditsByMovie(
//            movieId = movieId.toString(),
//            onSuccess = {
//                actorsViewPods.setData(it.first)
//                creatorsViewPod.setData(it.second)
//            },
//            onFailure = {
//
//            }
//        )
//    }

    private fun bindData(movie: MovieVO) {
        val fmovie: MovieVO = movie
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backDropPath}")
            .into(ivMovieDetails)
            Log.d("MovieId", fmovie.id.toString())
        tvMovieName.text = movie.title ?: ""
        tvMovieReleaseYear.text = movie.releaseDate?.substring(0,4)
        tvMovieRating.text = movie.voteAverage?.toString() ?: ""
        tvOverview.text = movie.overview ?: ""
        tvOriginalTitle.text = movie.title ?: ""
        tvType.text = movie.getGenresAsCommaSeparatedString()
        tvProduction.text = movie.getProductionCountriesAsCommaSeparatedString()
        movie.voteCount?.let {
            tvNumberOfVotes.text = "$it VOTES"
        }
        bindGenre(movie,movie.genres ?: listOf())
        rbRatingMovieDetails.rating = movie.getRatingBasedOnFiveStars()
        tvDescription.text = movie.overview ?: ""
    }

    private fun setUpViewPods(){
        actorsViewPods = vpActors as ActorListViewPod
        actorsViewPods.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = ""
        )
        creatorsViewPod = vpCreators as ActorListViewPod
        creatorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
    }

    private fun bindGenre(movie:MovieVO,genres:List<GenreVO>){
        movie.genres?.count()?.let {
            tvFirstGenre.text = genres.firstOrNull()?.name ?: ""
            tvSecondGenre.text = genres.getOrNull(1)?.name ?: ""
            tvThirdGenre.text = genres.getOrNull(2)?.name ?: ""

            if(it<3){
                tvThirdGenre.visibility = View.GONE
            }else if(it<2){
                tvSecondGenre.visibility = View.GONE
            }
        }
    }

    override fun showMovieDetails(movie: MovieVO) {
        bindData(movie)
    }

    override fun showCreditsByMovie(cast: List<ActorVO>, crew: List<ActorVO>) {
        actorsViewPods.setData(cast)
        creatorsViewPod.setData(crew)
    }

    override fun navigateBack() {
        finish()
    }
}