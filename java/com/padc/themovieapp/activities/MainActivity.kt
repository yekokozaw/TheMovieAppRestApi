package com.padc.themovieapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.padc.themovieapp.R
import com.padc.themovieapp.adapters.BannerAdapter
import com.padc.themovieapp.adapters.ShowcaseAdapter
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.delegates.BannerViewHolderDelegate
import com.padc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.themovieapp.delegates.ShowcaseViewHolderDelegate
import com.padc.themovieapp.dummy.dummyGenreList
import com.padc.themovieapp.mvp.presenters.MainPresenter
import com.padc.themovieapp.mvp.presenters.MainPresenterImpl
import com.padc.themovieapp.mvp.views.MainView
import com.padc.themovieapp.mvvm.MainViewModel
import com.padc.themovieapp.viewpods.ActorListViewPod
import com.padc.themovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BannerViewHolderDelegate,MovieViewHolderDelegate,ShowcaseViewHolderDelegate{

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcaseAdapter : ShowcaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mActorListViewPod: ActorListViewPod

    //View Model
    private lateinit var mViewModel : MainViewModel
    //private lateinit var mPresenter: MainPresenter
    //Model
    //private val mMovieModel : MovieModel = MovieModelImpl

    //Data
    //private var mGenres: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setUpPresenter()

        setUpViewModel()

        //App Bar Leading Icon
        setUpToolbar()
        setUpViewPods()
        setUpBannerViewPager()
        setUpListeners()
        setUpShowcaseRecyclerView()

        //request data
        //mPresenter.onUiReady(this)
        observableLiveData()

    }

//    private fun requestData(){
//        //Now Playing Movies
//        mMovieModel.getNowPlayingMovies{
//            showError(it)
//        }
//            ?.observe( this, Observer{
//            mBannerAdapter.setNewData((it))
//        })
//
//        //Popular Movies
//        mMovieModel.getPopularMovies{
//                showError(it)
//        }?.observe(this, Observer {
//            mBestPopularMovieListViewPod.setData(it)
//        })
//
//
//        //Top Rated Movies
//        mMovieModel.getTopRatedMovies{
//            showError(it)
//        }?.observe(this, Observer {
//            mShowcaseAdapter.setNewData(it)
//        })
//
//        mMovieModel.getGenres(
//            onSuccess = {
//                mGenres = it
//                setUpGenreTabLayout(it)
//
//                //Get Movie by Genre for first Genre
//                it.firstOrNull()?.id?.let{ genreId ->
//                    getMoviesByGenre(genreId)
//                }
//            },
//            onFailure = {
//
//            }
//        )
//
//        mMovieModel.getActors(
//            onSuccess = {
//                mActorListViewPod.setData(it)
//            },
//            onFailure = {
//
//            }
//        )
//    }

//    private fun getMoviesByGenre (genreId: Int) {
//        mMovieModel.getMoviesByGenre(
//            genreId = genreId.toString(),
//            onSuccess = {
//                mMoviesByGenreViewPod.setData(it)
//            },
//            onFailure = {
//
//            })
//    }

//    private fun setUpPresenter(){
//        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
//        mPresenter.initView(this)
//    }
    private fun setUpViewModel(){
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mViewModel.getInitialData()
    }

    private fun observableLiveData(){
        mViewModel.nowPlayingMovieLiveData?.observe(this,mBannerAdapter::setNewData)
        mViewModel.popularMoviesLiveData?.observe(this,mBestPopularMovieListViewPod::setData)
        mViewModel.topRatedMoviesLiveData?.observe(this,mShowcaseAdapter::setNewData)
        mViewModel.genreLiveData.observe(this,this::setUpGenreTabLayout)
        mViewModel.moviesByGenreLiveData.observe(this,mMoviesByGenreViewPod::setData)
        mViewModel.actorsLiveData.observe(this,mActorListViewPod::setData)
    }

    private fun setUpViewPods(){
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(this)

        mActorListViewPod = vpActorHomeScreen as ActorListViewPod
    }

    private fun setUpShowcaseRecyclerView(){
        mShowcaseAdapter = ShowcaseAdapter(this)
        rvShowcases.adapter = mShowcaseAdapter
        rvShowcases.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpBannerViewPager(){
        mBannerAdapter = BannerAdapter(this)
        viewPagerBanner.adapter = mBannerAdapter

        dotsIndicatorBanner.attachTo(viewPagerBanner)
    }

    private fun setUpListeners(){
        //Genre Tab Layout
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mViewModel.getMoviesByGenre(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpGenreTabLayout(genreList: List<GenreVO>){
        genreList.forEach {
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }
        }
    }
    private fun setUpToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover,menu)
        return true

    }

    //search click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search ->{
                startActivity(SearchMoviesActivity.newIntent(this))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }


//    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
//        mBannerAdapter.setNewData(nowPlayingMovies)
//    }
//
//    override fun showPopularMovies(popularMovies: List<MovieVO>) {
//        mBestPopularMovieListViewPod.setData(popularMovies)
//    }
//
//    override fun showTopRatedMovies(topRatedMovies: List<MovieVO>) {
//        mShowcaseAdapter.setNewData(topRatedMovies)
//    }
//
//    override fun showGenres(genreList: List<GenreVO>) {
//        setUpGenreTabLayout(genreList)
//    }
//
//    override fun showMoviesByGenre(moviesByGenre: List<MovieVO>) {
//        mMoviesByGenreViewPod.setData(moviesByGenre)
//    }
//
//    override fun showActors(actors: List<ActorVO>) {
//        mActorListViewPod.setData(actors)
//    }
//
//    override fun navigateToMovieDetailsScreen(movie: Int) {
//        startActivity(MovieDetailsActivity.newIntent(this, movieId = movie))
//    }

}