package com.example.toters_test_mockmessaging.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toters_test_mockmessaging.ImageConverter.ImageConvert;
import com.example.toters_test_mockmessaging.R;
import com.example.toters_test_mockmessaging.UsersDB.UsersDisplay;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {
    private List<UsersDisplay> usersDisplayArrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void updateList(List<UsersDisplay> list) {
        usersDisplayArrayList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MainRecyclerViewAdapter(List<UsersDisplay> usersDisplayArrayList) {
        this.usersDisplayArrayList = usersDisplayArrayList;
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_item, parent, false);
        return new MainRecyclerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {
        try {
            if (usersDisplayArrayList != null) {
                holder.userName.setText(usersDisplayArrayList.get(position).getUserName());
                holder.profileImage.setImageBitmap(ImageConvert.convertByteArrayToImage(usersDisplayArrayList.get(position).getProfileImage()));
                if (usersDisplayArrayList.get(position).getMessage() != null && usersDisplayArrayList.get(position).getCreatedTime() != null) {
                    String msg = usersDisplayArrayList.get(position).getMessage();
                    String time = usersDisplayArrayList.get(position).getCreatedTime();
                    holder.userMsg.setText(msg);
                    holder.msgTime.setText(time);
                } else {
                    holder.userMsg.setText("");
                    holder.msgTime.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return usersDisplayArrayList.size();
    }

    public static class MainRecyclerViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileImage;
        public TextView userName, userMsg, msgTime;

        public MainRecyclerViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userName);
            userMsg = itemView.findViewById(R.id.userMessage);
            msgTime = itemView.findViewById(R.id.msgTime);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
