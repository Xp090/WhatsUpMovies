package me.xp090.whatsupmovies.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.xp090.whatsupmovies.R;
import me.xp090.whatsupmovies.models.MovieResult;
import me.xp090.whatsupmovies.util.MoviesApi;
import me.xp090.whatsupmovies.util.MoviesApiValuesHelper;

/**
 * Created by Xp090 on 10/02/2018.
 */

public class MovieDetailFragment extends BottomSheetDialogFragment {
    private static final String BUNDLE_MOVE_RESULT_KEY = "movie_result";
    ImageView posterImage;
    TextView movieNameTV;
    TextView movieVotesTV;
    TextView movieGenTV;
    TextView noPosterTV;
    TextView movieDateTV;
    TextView movieOverview;
    ImageView firstStarImage;
    ImageView secondStarImage;
    ImageView thirdStarImage;
    MovieResult mMovieResultDetail;
    ProgressBar backDropProgressBar;

    public static MovieDetailFragment newInstance(MovieResult movieResult) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.mMovieResultDetail = movieResult;
        return  movieDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mMovieResultDetail = (MovieResult) savedInstanceState.getSerializable(BUNDLE_MOVE_RESULT_KEY);
        }

        movieNameTV.setText(mMovieResultDetail.title);
        movieVotesTV.setText(String.valueOf(mMovieResultDetail.voteAverage));
        movieDateTV.setText(mMovieResultDetail.releaseDate);
        movieOverview.setText(mMovieResultDetail.overview);
        if (mMovieResultDetail.backdropPath != null) {
            Picasso.with(getActivity()).load(MoviesApi.IMG_BACKDROP_URL + mMovieResultDetail.backdropPath).into(posterImage);
            noPosterTV.setVisibility(View.GONE);
            backDropProgressBar.setVisibility(View.VISIBLE);
        } else {
            noPosterTV.setVisibility(View.VISIBLE);
            backDropProgressBar.setVisibility(View.GONE);
        }
        movieGenTV.setText(MoviesApiValuesHelper.lookUpGeners(mMovieResultDetail.genreIds));
       MoviesApiValuesHelper.getStarLevel(mMovieResultDetail.voteAverage,firstStarImage,
               secondStarImage,thirdStarImage);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = View.inflate(getActivity(), R.layout.fragment_movie_detail, null);
        dialog.setContentView(view);
        posterImage = view.findViewById(R.id.img_poster);
        movieNameTV = view.findViewById(R.id.txt_name);
        movieVotesTV = view.findViewById(R.id.txt_votes);
        movieGenTV = view.findViewById(R.id.txt_genres);
        movieDateTV = view.findViewById(R.id.txt_date);
        movieOverview = view.findViewById(R.id.txt_overview);
        noPosterTV = view.findViewById(R.id.txt_no_poster);
        firstStarImage =view.findViewById(R.id.img_1st_star);
        secondStarImage =view.findViewById(R.id.img_2nd_star);
        thirdStarImage=view.findViewById(R.id.img_3rd_star);
        backDropProgressBar = view.findViewById(R.id.progbar_img_load);
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.bottom_sheet_width);
        dialog.getWindow().setLayout(width > 0 ? width : ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_MOVE_RESULT_KEY,mMovieResultDetail);
        super.onSaveInstanceState(outState);
    }


}

