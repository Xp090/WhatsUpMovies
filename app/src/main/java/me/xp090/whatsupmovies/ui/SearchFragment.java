package me.xp090.whatsupmovies.ui;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import me.xp090.whatsupmovies.R;
import me.xp090.whatsupmovies.models.viewmodels.AbstractMoviesViewModel;
import me.xp090.whatsupmovies.models.viewmodels.SearchViewModel;

/**
 * Created by Xp090 on 29/01/2018.
 */

@SuppressWarnings("unchecked")
public class SearchFragment extends AbstractMoviesFragment<String> {

    TextView searchKeywords;



    @Override
    public void onInitializeViews(View view) {
        searchKeywords = view.findViewById(R.id.txt_search_keywords);
    }

    @Override
    public int getFragmentRes() {
        return R.layout.fragment_search;
    }

    @Override
    public Class<? extends AbstractMoviesViewModel<String>> getViewModelClass() {
        return SearchViewModel.class;
    }

    @Override
    public void onQueryChanged(String query) {
        String searchResultsFor = String.format(getResources().getString(R.string.search_results_for), query);
        searchKeywords.setText(Html.fromHtml(searchResultsFor));
    }
}
