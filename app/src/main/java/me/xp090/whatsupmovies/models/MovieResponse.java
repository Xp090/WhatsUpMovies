
package me.xp090.whatsupmovies.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MovieResponse implements Serializable
{

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("results")
    @Expose
    public List<MovieResult> results = new ArrayList<>();
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;

    public boolean isFailedResponse;

    private final static long serialVersionUID = 1653743368635191092L;

    public MovieResponse() {

    }
    public MovieResponse(boolean isFailedResponse) {
        this.isFailedResponse = isFailedResponse;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("page", page).append("results", results).append("totalResults", totalResults).append("totalPages", totalPages).toString();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(results).append(totalResults).append(page).append(totalPages).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MovieResponse)) {
            return false;
        }
        MovieResponse rhs = ((MovieResponse) other);
        return new EqualsBuilder().append(results, rhs.results).append(totalResults, rhs.totalResults).append(page, rhs.page).append(totalPages, rhs.totalPages).isEquals();
    }

}
