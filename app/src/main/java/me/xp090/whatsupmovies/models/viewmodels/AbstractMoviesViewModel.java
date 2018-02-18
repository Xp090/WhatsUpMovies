package me.xp090.whatsupmovies.models.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import me.xp090.whatsupmovies.models.MovieResponse;
import me.xp090.whatsupmovies.util.MoviesApi;

/**
 * Created by Xp090 on 04/02/2018.
 */

public abstract class AbstractMoviesViewModel<T> extends ViewModel {

    private MediatorLiveData<MovieResponse> liveMoviesResults;

    private MutableLiveData<MovieResponse> newResultsList;
    private MutableLiveData<T> moviesQuery;
    public AbstractMoviesViewModel() {
        liveMoviesResults = new MediatorLiveData<>();
        moviesQuery = new MutableLiveData<>();

        liveMoviesResults.addSource(moviesQuery, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                liveMoviesResults.setValue(null);
                loadMore(1);
            }
        });

    }

    public void loadMore(int nextPage) {
        newResultsList = getNewResultsList(moviesQuery.getValue(), nextPage);
        liveMoviesResults.addSource(newResultsList, new Observer<MovieResponse>() {
            @Override
            public void onChanged(@Nullable MovieResponse movieResponse) {
                if (liveMoviesResults.getValue() == null) {
                    liveMoviesResults.setValue(movieResponse);
                } else {
                    MovieResponse tempResponse = liveMoviesResults.getValue();
                    if (!movieResponse.isFailedResponse) {
                        tempResponse.results.addAll(movieResponse.results);
                    }
                    tempResponse.isFailedResponse = movieResponse.isFailedResponse;
                    liveMoviesResults.setValue(tempResponse);
                }
            }
        });
    }

    public abstract MutableLiveData<MovieResponse> getNewResultsList(T t , int nextPage);

    public LiveData<MovieResponse> getLiveMoviesResults() {

        return liveMoviesResults;
    }

    public LiveData<T> getMoviesQuery() {
        return moviesQuery;
    }

    public void setMoivesQuery(T moviesQuery) {
        this.moviesQuery.setValue(moviesQuery);
    }
}
