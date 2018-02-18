package me.xp090.whatsupmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;

import me.xp090.whatsupmovies.models.viewmodels.SearchViewModel;
import me.xp090.whatsupmovies.ui.DiscoverFragment;
import me.xp090.whatsupmovies.ui.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_SEARCH_FRAGMENT = "search_fragment";
    public static final String TAG_DISCOVER_FRAGMENT = "discover_fragment";
    private SearchViewModel resultsViewModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultsViewModels = ViewModelProviders.of(this)
                .get(SearchViewModel.class);

        DiscoverFragment discoverFragment = (DiscoverFragment) getFragmentManager().findFragmentByTag(TAG_DISCOVER_FRAGMENT);
        if (discoverFragment == null) {
            discoverFragment = new DiscoverFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container,discoverFragment,TAG_DISCOVER_FRAGMENT)
                    .commit();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        resultsViewModels.getMoviesQuery().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (!searchView.getQuery().toString().equals(s)) {
                    searchItem.expandActionView();
                    searchView.setQuery(s,false);
                }
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                SearchFragment searchFragment = (SearchFragment) getFragmentManager().findFragmentByTag(TAG_SEARCH_FRAGMENT);
                if (searchFragment != null) {
                    getFragmentManager().beginTransaction()
                            .remove(searchFragment)
                            .commit();
                    getFragmentManager().popBackStack();
                }

                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchFragment searchFragment = (SearchFragment) getFragmentManager()
                        .findFragmentByTag(TAG_SEARCH_FRAGMENT);
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,android.R.animator.fade_in, android.R.animator.fade_out)
                            .add(R.id.container, searchFragment, TAG_SEARCH_FRAGMENT)
                            .addToBackStack(TAG_SEARCH_FRAGMENT)
                            .commit();

                }

                resultsViewModels.setMoivesQuery(s);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return true;
    }


}
