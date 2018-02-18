package me.xp090.whatsupmovies.util;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import me.xp090.whatsupmovies.models.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static android.content.ContentValues.TAG;

/**
 * Created by Xp090 on 29/01/2018.
 */

public class MoviesApi {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMG_POSTER_URL = "https://image.tmdb.org/t/p/w185";
    public static final String IMG_BACKDROP_URL = "https://image.tmdb.org/t/p/w780";
    private Retrofit retrofit;
    private MoviesSearchService moviesSearchService;
    public List<AbstractFlexibleItem> mSearchResults;
    private static MoviesApi moviesApi;

    private MoviesApi () {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesSearchService = retrofit.create(MoviesSearchService.class);


    }

    public static MoviesApi getInstance() {
        if (moviesApi == null) {
            moviesApi = new MoviesApi();
        }
        return moviesApi;
    }

    public MutableLiveData<MovieResponse> getSearchQuery(String query, int page ) {
       final MutableLiveData<MovieResponse> liveData = new MutableLiveData<>();
        moviesSearchService.getSearchResults(query, page).enqueue(new CallbackResponse(liveData));
        return liveData;
    }

    public MutableLiveData<MovieResponse> getDiscoverQuery(MoviesApiValuesHelper.DiscoverMoviesOptions options, int page ) {
        final MutableLiveData<MovieResponse> liveData = new MutableLiveData<>();
        moviesSearchService.getDiscoverMovies(options.minDate, options.maxDate, options.sortBy, page)
                .enqueue(new CallbackResponse(liveData));
        return liveData;
    }

    public interface MoviesSearchService {
        @GET("search/movie?api_key=2ee76e793a6b4fb8dc346d78c01aa9e6")
        Call<MovieResponse> getSearchResults(@Query("query") String query, @Query("page") int page);

        @GET("discover/movie?api_key=2ee76e793a6b4fb8dc346d78c01aa9e6")
        Call<MovieResponse> getDiscoverMovies(@Query("primary_release_date.gte") String releaseDateMin
                , @Query("primary_release_date.lte") String releaseDateMax, @Query("sort_by") String sortBy, @Query("page") int page);

    }

    private static class CallbackResponse implements Callback<MovieResponse> {
        MutableLiveData<MovieResponse> liveData;
        private CallbackResponse(MutableLiveData<MovieResponse> liveData) {
            this.liveData=liveData;
        }
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            if (response.body() != null) {
                Log.e(TAG, "onFailure: ");
                liveData.setValue(response.body());
            } else {
                liveData.setValue(new MovieResponse(true));
                Log.e("MoviesApi", "onResponse: " + response.message());
            }

        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            liveData.setValue(new MovieResponse(true));
            Log.e("MoviesApi", "onFailure: " + t.getMessage());

        }
    }


}
