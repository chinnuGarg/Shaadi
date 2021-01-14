package com.app.shaadi.module.randomuser.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Class for the ItemOffsetDecoration
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    //Variable to hold the reference for mItemOffset
    private int mItemOffset;

    /**
     * Constructor for the class  ItemOffsetDecoration
     *
     * @param itemOffset item offset value
     */
    public ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    /**
     * Constructor for the class  ItemOffsetDecoration
     *
     * @param context      Context of calling activity or fragment
     * @param itemOffsetId item offset value
     */
    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}