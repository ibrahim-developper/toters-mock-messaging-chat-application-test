package com.example.toters_test_mockmessaging.Chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toters_test_mockmessaging.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatRecyclerViewHolder> {
    private static final String TAG = "ChatRecyclerViewAdapter";
    private List<ChatLayoutModel> userMessagesList;

    public ChatRecyclerViewAdapter(List<ChatLayoutModel> userMessagesList) {
        Log.d(TAG, "ChatRecyclerViewAdapter: init chat recyclerview list");
        this.userMessagesList = userMessagesList;
    }

    @NonNull
    @Override
    public ChatRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: start");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, parent, false);
        return new ChatRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: start view holder");
        if (userMessagesList != null) {
            if (userMessagesList.get(position).isCurrentUser()) {
                holder.receiverLayout.setVisibility(View.GONE);
                holder.senderLayout.setVisibility(View.VISIBLE);
                holder.senderMessage.setText(userMessagesList.get(position).getMessage());
                holder.senderMsgTime.setText(userMessagesList.get(position).getTime());
            } else {
                holder.senderLayout.setVisibility(View.GONE);
                holder.receiverLayout.setVisibility(View.VISIBLE);
                holder.receiverMessage.setText(userMessagesList.get(position).getMessage());
                holder.receiverMsgTime.setText(userMessagesList.get(position).getTime());
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: user messages list size " + userMessagesList.size());
        return userMessagesList.size();
    }

    public static class ChatRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView senderMessage, senderMsgTime, receiverMessage, receiverMsgTime;
        public RelativeLayout senderLayout, receiverLayout;

        public ChatRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sender_message_text);
            senderMsgTime = itemView.findViewById(R.id.senderMsgTime);
            receiverMessage = itemView.findViewById(R.id.receiver_message_text);
            receiverMsgTime = itemView.findViewById(R.id.receiverMsgTime);
            senderLayout = itemView.findViewById(R.id.senderLayout);
            receiverLayout = itemView.findViewById(R.id.receiverLayout);
        }
    }
}
