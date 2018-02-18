package me.xp090.whatsupmovies.models;


import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.Payload;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

import eu.davidea.viewholders.FlexibleViewHolder;
import me.xp090.whatsupmovies.R;


/**
 * Created by Xp090 on 30/01/2018.
 */

public class LoadMoreProgressItem extends AbstractFlexibleItem<LoadMoreProgressItem.ProgressViewHolder> {


    private static final int ITEM_TYPE_LOADING = 11;

    @Override
    public boolean equals(Object o) {
        return this == o;//The default implementation
    }

    @Override
    public int getItemViewType() {
        return ITEM_TYPE_LOADING;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.progress_item;
    }

    @Override
    public ProgressViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ProgressViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ProgressViewHolder holder,
                               int position, List payloads) {

    }

    static class ProgressViewHolder extends FlexibleViewHolder {

        ProgressBar progressBar;

        ProgressViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            progressBar = view.findViewById(R.id.progbar_load_more);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            AnimatorHelper.scaleAnimator(animators, itemView, 0f);
        }
    }

}
