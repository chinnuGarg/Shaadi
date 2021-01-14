package com.app.shaadi.module.randomuser.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.app.shaadi.R;
import com.app.shaadi.database.RandomUser;
import com.app.shaadi.global.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Adapter class for the MatchListAdapter
 */
public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MyViewHolder> {
    //Variable to hold the reference for context
    Context context;
    //Variable to hold the reference for List<RandomUser>
    List<RandomUser> randomUserList;
    //Variable to hold the reference for onProfileLikeDislikeInterface
    OnProfileLikeDislike onProfileLikeDislikeInterface;
    int width, height, itemWidth;

    /**
     * View Model class for the recycler view
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textName, textAge, textCity, textGender, textProfileStatus;
        AppCompatImageView profileImage;
        AppCompatButton btnDislike;
        AppCompatButton btnLike;

        /**
         * Method to initialize the views
         *
         * @param view views in a layout
         */
        public MyViewHolder(View view) {
            super(view);
            profileImage = (AppCompatImageView) view.findViewById(R.id.iv_profile);
            textName = (AppCompatTextView) view.findViewById(R.id.tv_name);
            textProfileStatus = (AppCompatTextView) view.findViewById(R.id.tv_profile_status);
            textAge = (AppCompatTextView) view.findViewById(R.id.tv_age);
            textCity = (AppCompatTextView) view.findViewById(R.id.tv_city);
            textGender = (AppCompatTextView) view.findViewById(R.id.tv_gender);
            btnDislike = (AppCompatButton) view.findViewById(R.id.btn_dislike);
            btnLike = (AppCompatButton) view.findViewById(R.id.btn_like);
            //Setting Item width to 80% of device width
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(itemWidth,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * Constructor for the MatchListAdapter
     *
     * @param context        context of the calling activity
     * @param randomUserList list for the random user
     */
    public MatchListAdapter(Context context, List<RandomUser> randomUserList, OnProfileLikeDislike onProfileLikeDislikeInterface) {
        this.context = context;
        this.randomUserList = randomUserList;
        this.onProfileLikeDislikeInterface = onProfileLikeDislikeInterface;
        //Calculate the width and height of device
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        this.width = size.x;
        this.height = size.y;
        //100% of screen width
        itemWidth = width;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final RandomUser randomUser = randomUserList.get(position);
        holder.textName.setText(randomUser.getName());
        holder.textGender.setText(randomUser.getGender());
        holder.textCity.setText(randomUser.getCity());
        holder.textAge.setText(randomUser.getAge());
        if (randomUser.getLiked() == Constants.liked) {
            holder.textProfileStatus.setText(R.string.you_liked_profile);
            holder.textProfileStatus.setVisibility(View.VISIBLE);
        } else if (randomUser.getLiked() == Constants.disLiked) {
            holder.textProfileStatus.setText(R.string.you_disliked_profile);
            holder.textProfileStatus.setVisibility(View.VISIBLE);
        } else if (randomUser.getLiked() == Constants.notChanged) {
            holder.textProfileStatus.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(randomUser.getProfileImage())
                .apply(new RequestOptions().centerCrop().error(R.drawable.user)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(holder.profileImage);
        //On click Listener for the dislike button
        holder.btnDislike.setOnClickListener(view -> {
            onProfileLikeDislikeInterface.onStatusChanged(Constants.disLiked, randomUser);
            notifyDataSetChanged();
        });
        //On Click Listener for the like button
        holder.btnLike.setOnClickListener(view -> {
            onProfileLikeDislikeInterface.onStatusChanged(Constants.liked, randomUser);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return randomUserList.size();
    }

    /**
     * Interface for the profile like or dislike
     */
    public interface OnProfileLikeDislike {
        /**
         * Method to update the status into db
         *
         * @param status     0-like,1-dislike,2-not changed
         * @param randomUser user data
         */
        void onStatusChanged(int status, RandomUser randomUser);
    }
}