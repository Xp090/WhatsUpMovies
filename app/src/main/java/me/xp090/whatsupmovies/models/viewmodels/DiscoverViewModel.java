package me.xp090.whatsupmovies.models.viewmodels;

import android.arch.lifecycle.MutableLiveData;

import me.xp090.whatsupmovies.models.MovieResponse;
import me.xp090.whatsupmovies.util.MoviesApi;
import me.xp090.whatsupmovies.util.MoviesApiValuesHelper;

/**
 * Created by Xp090 on 04/02/2018.
 */

public class DiscoverViewModel extends AbstractMoviesViewModel<MoviesApiValuesHelper.DiscoverMoviesOptions> {
    @Override
    public MutableLiveData<MovieResponse> getNewResultsList(MoviesApiValuesHelper.DiscoverMoviesOptions discoverMoviesOptions, int nextPage) {
        return MoviesApi.getInstance().getDiscoverQuery(discoverMoviesOptions, nextPage);
    }
}
