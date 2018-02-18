
package me.xp090.whatsupmovies.models;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;
import me.xp090.whatsupmovies.ui.MovieDetailFragment;
import me.xp090.whatsupmovies.R;
import me.xp090.whatsupmovies.util.MoviesApi;
import me.xp090.whatsupmovies.util.MoviesApiValuesHelper;

public class MovieResult extends AbstractFlexibleItem<MovieResult.ResultsViewHolder> implements Serializable,IFlexible<MovieResult.ResultsViewHolder>
{
    public static final int ITEM_TYPE_RESULT =10;
    Context context;

    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("video")
    @Expose
    public boolean video;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    private final static long serialVersionUID = 5069832922167220898L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("posterPath", posterPath).append("adult", adult).append("overview", overview).append("releaseDate", releaseDate).append("genreIds", genreIds).append("id", id).append("originalTitle", originalTitle).append("originalLanguage", originalLanguage).append("title", title).append("backdropPath", backdropPath).append("popularity", popularity).append("voteCount", voteCount).append("video", video).append("voteAverage", voteAverage).toString();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(genreIds).append(originalLanguage).append(adult).append(backdropPath).append(voteCount).append(id).append(title).append(releaseDate).append(overview).append(posterPath).append(originalTitle).append(voteAverage).append(video).append(popularity).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MovieResult)) {
            return false;
        }
        MovieResult rhs = ((MovieResult) other);
        return new EqualsBuilder().append(genreIds, rhs.genreIds).append(originalLanguage, rhs.originalLanguage).append(adult, rhs.adult).append(backdropPath, rhs.backdropPath).append(voteCount, rhs.voteCount).append(id, rhs.id).append(title, rhs.title).append(releaseDate, rhs.releaseDate).append(overview, rhs.overview).append(posterPath, rhs.posterPath).append(originalTitle, rhs.originalTitle).append(voteAverage, rhs.voteAverage).append(video, rhs.video).append(popularity, rhs.popularity).isEquals();
    }

    @Override
    public int getItemViewType() {
        return ITEM_TYPE_RESULT;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_item;
    }

    @Override
    public ResultsViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        context = view.getContext();

        return new ResultsViewHolder(view,adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ResultsViewHolder holder, int position, List<Object> payloads) {
        holder.movieNameTV.setText(title);
        if (voteAverage == 0.0f) {
            holder.movieVotesTV.setText("N/A");
        } else {
            holder.movieVotesTV.setText(String.valueOf(voteAverage));
        }

        if (posterPath != null) {
            holder.posterProgressBar.setVisibility(View.VISIBLE);
            holder.noPosterTV.setVisibility(View.GONE);
            Picasso.with(context).load(MoviesApi.IMG_POSTER_URL + posterPath).into(holder.posterImage);
        } else {
            holder.noPosterTV.setVisibility(View.VISIBLE);
            holder.posterProgressBar.setVisibility(View.GONE);
        }
        holder.movieGenTV.setText(MoviesApiValuesHelper.lookUpGeners(genreIds));
        MoviesApiValuesHelper.getStarLevel(voteAverage,holder.firstStarImage,
                holder.secondStarImage,holder.thirdStarImage);
    }


    static class ResultsViewHolder extends FlexibleViewHolder {

        ImageView posterImage;
        TextView movieNameTV;
        TextView movieVotesTV;
        TextView movieGenTV;
        TextView noPosterTV;
        ImageView firstStarImage;
        ImageView secondStarImage;
        ImageView thirdStarImage;
        ProgressBar posterProgressBar;
        public ResultsViewHolder(View view, final FlexibleAdapter adapter) {
            super(view, adapter);
            posterImage = view.findViewById(R.id.img_poster);
            movieNameTV = view.findViewById(R.id.txt_name);
            movieVotesTV = view.findViewById(R.id.txt_votes);
            movieGenTV = view.findViewById(R.id.txt_genres);
            noPosterTV = view.findViewById(R.id.txt_no_poster);
            firstStarImage =view.findViewById(R.id.img_1st_star);
            secondStarImage =view.findViewById(R.id.img_2nd_star);
            thirdStarImage=view.findViewById(R.id.img_3rd_star);
            posterProgressBar = view.findViewById(R.id.progbar_img_load);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance((MovieResult) adapter.getItem(getAdapterPosition()));
                    movieDetailFragment.show(((AppCompatActivity)view.getContext()).getSupportFragmentManager(), movieDetailFragment.getTag());
                }
            });
        }
    }
}
