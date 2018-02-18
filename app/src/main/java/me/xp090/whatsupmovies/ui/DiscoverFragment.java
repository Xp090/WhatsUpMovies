package me.xp090.whatsupmovies.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import me.xp090.whatsupmovies.R;
import me.xp090.whatsupmovies.models.viewmodels.AbstractMoviesViewModel;
import me.xp090.whatsupmovies.models.viewmodels.DiscoverViewModel;
import me.xp090.whatsupmovies.util.MoviesApiValuesHelper;

/**
 * Created by Xp090 on 03/02/2018.
 */

public class DiscoverFragment extends AbstractMoviesFragment<MoviesApiValuesHelper.DiscoverMoviesOptions> {
    public static final String KEY_SELECTED_TYPE="key_type";
    public static final String KEY_SELECTED_SORT="key_sort";

    private Spinner mFilterTypeSpinner;
    private Spinner mFilterSortSpinner;
    private Button mGoFilterButton;

    @Override
    public void onInitializeViews(View view) {
        mFilterTypeSpinner = view.findViewById(R.id.spin_type);
        mFilterSortSpinner = view.findViewById(R.id.spin_sort);
        mGoFilterButton = view.findViewById(R.id.btn_go_filter);
        ArrayAdapter<CharSequence> filterTypeAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.main_filter_type, android.R.layout.simple_spinner_dropdown_item);
        mFilterTypeSpinner.setAdapter(filterTypeAdapter);

        ArrayAdapter<CharSequence> filterSortAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.sort_filter, android.R.layout.simple_spinner_dropdown_item);
        mFilterSortSpinner.setAdapter(filterSortAdapter);



        mGoFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMoviesViewModels().setMoivesQuery(MoviesApiValuesHelper
                        .setupDiscoverMoviesOptions(mFilterTypeSpinner.getSelectedItemPosition(),
                                mFilterSortSpinner.getSelectedItemPosition()));
            }
        });
    }

    @Override
    public int getFragmentRes() {
        return R.layout.fragment_discover;
    }

    @Override
    public Class<? extends AbstractMoviesViewModel<MoviesApiValuesHelper.DiscoverMoviesOptions>> getViewModelClass() {
        return DiscoverViewModel.class;
    }

    @Override
    public void onViewModelPrepared() {
        if (getMoviesViewModels().getMoviesQuery().getValue()==null) {
            getMoviesViewModels().setMoivesQuery(MoviesApiValuesHelper
                    .setupDiscoverMoviesOptions(mFilterTypeSpinner.getSelectedItemPosition(),
                            mFilterSortSpinner.getSelectedItemPosition()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_TYPE,mFilterTypeSpinner.getSelectedItemPosition());
        outState.putInt(KEY_SELECTED_SORT,mFilterSortSpinner.getSelectedItemPosition());
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mFilterTypeSpinner.setSelection(savedInstanceState.getInt(KEY_SELECTED_TYPE));
            mFilterSortSpinner.setSelection(savedInstanceState.getInt(KEY_SELECTED_SORT));
        }
    }
}
