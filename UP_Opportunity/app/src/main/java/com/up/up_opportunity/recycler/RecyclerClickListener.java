package com.up.up_opportunity.recycler;

import android.view.View;

/**
 * Created by Billy on 7/9/16.
 */
public interface RecyclerClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
