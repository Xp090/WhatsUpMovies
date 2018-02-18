package me.xp090.whatsupmovies.models.viewmodels;

import android.arch.lifecycle.MutableLiveData;

import me.xp090.whatsupmovies.models.MovieResponse;
import me.xp090.whatsupmovies.util.MoviesApi;

/**
 * Created by Xp090 on 31/01/2018.
 */

@SuppressWarnings("ConstantConditions")
public class SearchViewModel extends AbstractMoviesViewModel<String> {

    @Override
    public MutableLiveData<MovieResponse> getNewResultsList(String s, int nextPage) {
        return MoviesApi.getInstance().getSearchQuery(s, nextPage);
    }

}
