package me.xp090.whatsupmovies.ui;

import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import me.xp090.whatsupmovies.R;
import me.xp090.whatsupmovies.models.viewmodels.AbstractMoviesViewModel;
import me.xp090.whatsupmovies.models.LoadMoreProgressItem;
import me.xp090.whatsupmovies.models.MovieResponse;
import me.xp090.whatsupmovies.models.MovieResult;

/**
 * Created by Xp090 on 04/02/2018.
 */

@SuppressWarnings("unchecked")
public abstract class AbstractMoviesFragment<T> extends Fragment {
    RecyclerView moviesResultsView;
    ProgressBar progressBar;
    TextView resultsStatus;
    FlexibleAdapter<IFlexible> adapter;
    AbstractMoviesViewModel<T> moviesViewModels;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentRes(), container, false);
        moviesResultsView = view.findViewById(R.id.recycler_movies_items);
        progressBar = view.findViewById(R.id.progbar_loading);
        resultsStatus = view.findViewById(R.id.txt_results_status);
        onInitializeViews(view);
        return view;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final int spanCount = getResources().getInteger(R.integer.num_of_grid_columns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null) {
                    return adapter.getItemViewType(position) == MovieResult.ITEM_TYPE_RESULT ? 1 : spanCount;
                }
                return 1;
            }
        });
        moviesResultsView.setLayoutManager(gridLayoutManager);

        moviesViewModels = ViewModelProviders.of((AppCompatActivity) getActivity()).get(getViewModelClass());

        moviesViewModels.getMoviesQuery().observe((LifecycleOwner) getActivity(), new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                onQueryChanged(t);
                progressBar.setVisibility(View.VISIBLE);
                resultsStatus.setVisibility(View.GONE);
            }
        });


        moviesViewModels.getLiveMoviesResults().observe((LifecycleOwner) getActivity(), new Observer<MovieResponse>() {
            int lastItemPos;

            @Override
            public void onChanged(@Nullable  final MovieResponse movieResponse) {

                if (movieResponse == null) {
                    moviesResultsView.setAdapter(null);
                    adapter = null;
                } else if (adapter == null) {
                    progressBar.setVisibility(View.GONE);

                    if (movieResponse.results.size() == 0) {
                        resultsStatus.setVisibility(View.VISIBLE);
                        resultsStatus.setText(movieResponse.isFailedResponse ? R.string.failed_to_connect : R.string.no_results_found);
                        return;
                    }

                    adapter = new FlexibleAdapter<>((List<IFlexible>) (List<?>) movieResponse.results);
                    adapter.setEndlessScrollListener(new FlexibleAdapter.EndlessScrollListener() {
                        @Override
                        public void noMoreLoad(int newItemsSize) {}
                        @Override
                        public void onLoadMore(int lastPosition, int currentPage) {
                            if ( movieResponse.totalResults > lastPosition) {
                                lastItemPos = lastPosition;
                                currentPage++;
                                moviesViewModels.loadMore(currentPage);
                            } else {
                                adapter.onLoadMoreComplete(null);
                            }
                        }
                    }, new LoadMoreProgressItem())
                            .setEndlessScrollThreshold(1)
                            .setEndlessPageSize(20)
                            .setEndlessTargetCount(movieResponse.totalResults);
                    moviesResultsView.setAdapter(adapter);
                } else {
                    if (movieResponse.isFailedResponse){
                        Toast.makeText(getActivity(), R.string.failed_to_connect, Toast.LENGTH_LONG).show();
                        return;
                    }
                    adapter.onLoadMoreComplete((List<IFlexible>) (List<?>) movieResponse.results
                            .subList(lastItemPos, movieResponse.results.size()));
                }

            }
        });
        onViewModelPrepared();
    }

    public abstract int getFragmentRes();
    public abstract Class<? extends AbstractMoviesViewModel<T>> getViewModelClass();
    public void onViewModelPrepared() {

    }
    public void onQueryChanged(T query) {
    }

    public void onInitializeViews(View view) {

    }

    @Override
    public void onDestroyView() {
        moviesViewModels.getMoviesQuery().removeObservers((LifecycleOwner) getActivity());
        moviesViewModels.getLiveMoviesResults().removeObservers((LifecycleOwner) getActivity());
        super.onDestroyView();
    }

    public AbstractMoviesViewModel<T> getMoviesViewModels() {
        return moviesViewModels;
    }
}
