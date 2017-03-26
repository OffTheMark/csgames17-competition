package com.mirego.cschat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mirego.cschat.R;
import com.mirego.cschat.viewdatas.UserViewData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserViewData> users;
    private final UserAdapterListener listener;
    private final Context context;

    public UserAdapter(UserAdapterListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        final UserViewHolder viewHolder = new UserViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClicked(users.get(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        UserViewData userViewData = users.get(position);
        if (userViewData != null) {
            holder.tvUsername.setText(userViewData.username());
            Glide.with(context).load(userViewData.avatarUrl()).placeholder(R.drawable.img_profile).bitmapTransform(new CropCircleTransformation(context)).into(holder.ivAvatar);
        }
    }

    public void populateUsers(List<UserViewData> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        @BindView(R.id.tv_username)
        TextView tvUsername;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface UserAdapterListener {
        void onUserClicked(UserViewData userViewData);

    }

}
