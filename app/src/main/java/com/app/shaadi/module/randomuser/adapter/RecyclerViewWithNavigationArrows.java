package com.app.shaadi.module.randomuser.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.app.shaadi.R;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * RecyclerViewWithNavigationArrows to handle the recycler view events
 */
public class RecyclerViewWithNavigationArrows extends RelativeLayout implements View.OnClickListener {
    //Variable to hold the reference for RecyclerView
    RecyclerView recyclerView;
    //Variable to hold the reference for AppCompatImageView
    AppCompatImageView leftArrow;
    //Variable to hold the reference for AppCompatImageView
    AppCompatImageView rightArrow;
    //To track the position of the current visible item .for navigation with arrows
    int currentVisibleItem = 0;
    //To check whether user scrolled the recycler view or used arrows to navigate.
    private boolean programaticallyScrolled;
    ProgressBar progressBar;
    //LinearLayoutManagerWithSmoothScroller linearLayoutManagerWithSmoothScroller;
    private LinearLayoutManager linearLayoutManager;

    /**
     * Constructor to for the RecyclerViewWithNavigationArrows class
     *
     * @param context of the calling activity
     */
    public RecyclerViewWithNavigationArrows(Context context) {
        super(context);
        //Calling the init function to initialize the operations
        init();
    }

    /**
     * Constructor to for the RecyclerViewWithNavigationArrows class
     *
     * @param context of the calling activity
     * @param attrs   attribute set list
     */
    public RecyclerViewWithNavigationArrows(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Calling the init function to initialize the operations
        init();
    }

    /**
     * Constructor to for the RecyclerViewWithNavigationArrows class
     *
     * @param context      of the calling activity
     * @param attrs        attribute set list
     * @param defStyleAttr style sttribute
     */
    public RecyclerViewWithNavigationArrows(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Calling the init function to initialize the operations
        init();
    }

    /**
     * Function used to initilaize the function
     */
    private void init() {
        inflate(getContext(), R.layout.recyclerview_with_arrows, this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_random_user);
        leftArrow = (AppCompatImageView) findViewById(R.id.iv_left_arrow);
        rightArrow = (AppCompatImageView) findViewById(R.id.iv_right_arrow);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.recyclerview_item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_DRAGGING:
                        //Indicated that user scrolled.
                        programaticallyScrolled = false;
                        break;
                    case SCROLL_STATE_IDLE:
                        if (!programaticallyScrolled) {
                            currentVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            handleWritingViewNavigationArrows(false);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * Handles the arrows visibility based on current visible items and scrolls the
     * current visibility item based on param scroll.
     * <p>
     * Scroll - is False if User scrolls the Reycler Manually
     * - is True means User used arrows to navigate
     *
     * @param scroll scroll to position
     */
    private void handleWritingViewNavigationArrows(boolean scroll) {
        if (currentVisibleItem == (recyclerView.getAdapter().getItemCount() - 1)) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
        } else if (currentVisibleItem != 0) {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.VISIBLE);
        } else if (currentVisibleItem == 0) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.VISIBLE);
        }
        if (scroll) {
            recyclerView.smoothScrollToPosition(currentVisibleItem);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_arrow:
                programaticallyScrolled = true;
                //Decrement current visible item position to navigate back to previous item
                currentVisibleItem--;
                handleWritingViewNavigationArrows(true);
                break;
            case R.id.iv_right_arrow:
                programaticallyScrolled = true;
                //Increment current visible item position to navigate next item
                currentVisibleItem++;
                handleWritingViewNavigationArrows(true);
                break;
            default:
                break;
        }
    }

    /**
     * Function to set the adapter
     *
     * @param adapter recyclerview adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * Function to show the progressbar
     */
    public void showProgressBar() {
        progressBar.setVisibility(VISIBLE);
    }

    /**
     * Function to hide the progressbar
     */
    public void hideProgressBar() {
        progressBar.setVisibility(GONE);
    }
}