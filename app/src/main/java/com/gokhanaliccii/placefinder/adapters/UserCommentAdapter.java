package com.gokhanaliccii.placefinder.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.model.Photo;
import com.gokhanaliccii.placefinder.model.User;
import com.gokhanaliccii.placefinder.model.UserComment;
import com.gokhanaliccii.placefinder.utility.ViewUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gokhan on 05/02/17.
 */

public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentAdapter.UserCommentViewHolder> {

    ArrayList<UserComment> mUserComments;

    public UserCommentAdapter(ArrayList<UserComment> mUserComments) {

        this.mUserComments = mUserComments;
    }

    public void setUserComments(ArrayList<UserComment> mUserComments) {
        this.mUserComments = mUserComments;
    }

    @Override
    public UserCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user_comment, parent, false);

        return new UserCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserCommentViewHolder holder, int position) {

        if (holder == null)
            return;

        UserComment comment = mUserComments.get(position);
        if (comment == null)
            return;

        holder.comment.setText(comment.getText());
        User user = comment.getUser();
        Photo photo = user != null ? user.getPhoto() : null;

        if (photo != null)
            ViewUtility.loadCirclerView(holder.userIcon, photo.getImageBySize("40x40"));
    }

    @Override
    public int getItemCount() {

        if (mUserComments == null)
            return 0;

        return mUserComments.size();
    }

    public static class UserCommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_user_comment_text)
        TextView comment;

        @BindView(R.id.card_user_comment_icon)
        ImageView userIcon;

        public UserCommentViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
