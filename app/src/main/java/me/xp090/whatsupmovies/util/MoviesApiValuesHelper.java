package me.xp090.whatsupmovies.util;


import android.text.format.DateFormat;
import android.util.SparseArray;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.xp090.whatsupmovies.R;

/**
 * Created by Xp090 on 04/02/2018.
 */

public class MoviesApiValuesHelper {
    public static final int TYPE_NEWLY = 0;
    public static final int TYPE_UPCOMING = 1;

    public static final int SORT_RELEASE_A = 0;
    public static final int SORT_RELEASE_D = 1;
    public static final int SORT_POPULARITY_A = 2;
    public static final int SORT_POPULARITY_D = 3;
    public static final int SORT_VOTE_AVG_A = 4;
    public static final int SORT_VOTE_AVG_D = 5;
    public static final int SORT_VOTE_COUNT_A = 6;
    public static final int SORT_VOTE_COUNT_D = 7;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String[] mSortValuesArray = new String[]{
            "popularity.desc","popularity.asc", "primary_release_date.desc","primary_release_date.asc",
            "vote_average.desc","vote_average.asc","vote_count.desc","vote_count.asc"};
    public static final SparseArray<String> GENERS_IDS = new SparseArray<String>(){{
        put(28,"Action"); put(12,"Adventure"); put(16,"Animation"); put(35,"Comedy");
        put(80,"Crime"); put(99,"Documentary"); put(18,"Drama"); put(10751,"Family");
        put(14,"Fantasy"); put(36,"History"); put(27,"Horror"); put(10402,"Music");
        put(9648,"Mystery"); put(10749,"Romance"); put(878,"Science Fiction");
        put(10770,"TV Movie"); put(53,"Thriller"); put(10752,"War"); put(37,"Western");
    }};

    public static DiscoverMoviesOptions setupDiscoverMoviesOptions(int type, int sort) {
        DiscoverMoviesOptions options = new DiscoverMoviesOptions();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        if (type == TYPE_NEWLY) {
            minDate.set(Calendar.DAY_OF_MONTH, minDate.get(Calendar.DAY_OF_MONTH) - 30);
        } else if (type == TYPE_UPCOMING) {
            maxDate.set(Calendar.DAY_OF_MONTH,maxDate.get(Calendar.DAY_OF_MONTH) + 30);
        }
        options.minDate = DateFormat.format(DATE_FORMAT, minDate.getTimeInMillis()).toString();
        options.maxDate = DateFormat.format(DATE_FORMAT, maxDate.getTimeInMillis()).toString();

        options.sortBy = mSortValuesArray[sort];
        return options;

    }

    public static CharSequence lookUpGeners(List<Integer> geners) {
        StringBuilder gnersString = new StringBuilder();
        if (geners.size() == 0) {
            return "N/A";
        }
        for (int i = 0; i < geners.size() ; i++) {
            int id = geners.get(i);
            gnersString.append(GENERS_IDS.get(id));
            if (i < geners.size() - 1) {
                gnersString.append(", ");
            }
        }
        return gnersString;

    }

    public static void getStarLevel(float votes,ImageView... starImages) {
        int numOfHalfStars = Math.round (votes / (10f/6f));
        for (ImageView starImage : starImages) {
            int starValue = Math.min(2, numOfHalfStars);
            setStarImage(starImage, Star.valueOf(starValue));
            if (numOfHalfStars <= 1) {
                numOfHalfStars = 0;
            } else {
                numOfHalfStars = numOfHalfStars - 2;
            }
        }
    }

    private static void setStarImage(ImageView starImage, Star starValue) {
        if (starValue == Star.Empty) {
            starImage.setImageResource(R.drawable.ic_star_border);
        } else if (starValue == Star.Half) {
            starImage.setImageResource(R.drawable.ic_star_half);
        } else {
            starImage.setImageResource(R.drawable.ic_star);
        }
    }

    public static class DiscoverMoviesOptions {
        private DiscoverMoviesOptions() {

        }
        public String minDate;
        public String maxDate;
        public String sortBy;
    }

    public enum Star {
        Empty,Half,Full;

        public static Star valueOf(int value) {
            return values()[value];
        }
    }

}
