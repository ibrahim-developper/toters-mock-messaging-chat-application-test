package com.example.toters_test_mockmessaging.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toters_test_mockmessaging.Notification.NotificationHelper;
import com.example.toters_test_mockmessaging.R;
import com.example.toters_test_mockmessaging.UsersDB.CurrentUser;
import com.example.toters_test_mockmessaging.UsersDB.UserMessages;
import com.example.toters_test_mockmessaging.UsersDB.UsersDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    //***************recycler view***************
    private RecyclerView chatRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private List<ChatLayoutModel> chatLayoutModelList;
    //************************************************

    //***************toolbar***************
    private ImageView profileImage, btnSend;
    private TextView displayName;
    //************************************************

    //***************chat input***************
    private EditText inputMessage;
    private String username;
    //*********************************************

    //***************to get bundle***************
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_IMAGE = "USER_IMAGE";
    //****************************************************

    //listen when fragment close
    private OnFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start chat fragment");
        View view = inflater.inflate(R.layout.fragment_chat_layout, container, false);
        //init elements in chat fragment
        init(view);
        //get username and user image from bundle
        if (getArguments() != null) {
            Log.d(TAG, "onCreateView: fragment arguments not null");
            username = getArguments().getString(USER_NAME);
            Log.d(TAG, "onCreateView: username " + username);
            displayName.setText(username);
            profileImage.setImageBitmap(getArguments().getParcelable(USER_IMAGE));
            chatLayoutModelList = new ArrayList<>();
            fetchUserMessagesFromDatabase(username);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * get all messages to this user from room database
     * @param username
     */
    private void fetchUserMessagesFromDatabase(String username) {
        UsersDatabase db = UsersDatabase.getInstance(getActivity());
        List<CurrentUser> currentUserList = db.currentUserDao().getCurrentUserChat(username);
        if (currentUserList != null) {
            chatLayoutModelList.clear();
            for (CurrentUser currentUser : currentUserList) {
                Log.d(TAG, "fetchUserMessagesFromDatabase: current user message " + currentUser.currentUserMsg);
                chatLayoutModelList.add(new ChatLayoutModel(currentUser.currentUserMsg, currentUser.msgTime, true));
                List<UserMessages> userMessagesList = db.userMessagesDao().getUserMessagesAtTime(username, currentUser.msgTime);
                for (UserMessages userMessages : userMessagesList) {
                    chatLayoutModelList.add(new ChatLayoutModel(userMessages.message, userMessages.createdTime, false));
                    Log.d(TAG, "fetchUserMessagesFromDatabase: receiver message " + userMessages.message);
                }
            }
            chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(chatLayoutModelList);
            chatRecyclerView.setLayoutManager(layoutManager);
            chatRecyclerView.setAdapter(chatRecyclerViewAdapter);
            chatRecyclerView.smoothScrollToPosition(chatLayoutModelList.size());
            ((LinearLayoutManager) Objects.requireNonNull(chatRecyclerView.getLayoutManager())).setStackFromEnd(true);
            chatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    chatRecyclerView.smoothScrollToPosition(chatLayoutModelList.size());
                }
            });
        }
    }

    /**
     * init all element in fragment layout
     * @param v
     */
    private void init(View v) {
        Log.d(TAG, "init: init layout");
        chatRecyclerView = v.findViewById(R.id.chat_recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        ImageView backArrow = v.findViewById(R.id.backArrow);
        inputMessage = v.findViewById(R.id.inputMessage);
        inputMessage.requestFocus();
        backArrow.setVisibility(View.VISIBLE);
        btnSend = v.findViewById(R.id.btnSend);
        btnSend.setEnabled(false);
        //set listener to detect when message input change and enable or disable button send
        inputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    btnSend.setEnabled(false);
                } else {
                    btnSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call implemented method in main activity sense change in database
                closeKeyboard();
                sendBack();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the message of the current user in database
                String msg = inputMessage.getText().toString().trim();
                inputMessage.setText("");
                UsersDatabase db = UsersDatabase.getInstance(getActivity());
                db.currentUserDao().insertCurrentUserMsg(username, msg);
                //set the message to the receiver user
                db.userMessagesDao().insertUserMsg(msg, username);
                db.userMessagesDao().insertUserMsg(msg, username);
                //set random delay between 0 and 0.5 second then notify change recycler view
                updateRecyclerView(msg);
            }
        });
        profileImage = v.findViewById(R.id.displayProfileImage);
        profileImage.setVisibility(View.VISIBLE);
        displayName = v.findViewById(R.id.profileName);
    }
    //close soft keyboard when close fragment from back arrow in toolbar
    private void closeKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //close chat fragment
    public void sendBack() {
        if (mListener != null) {
            getActivity().onBackPressed();
        }
    }
    //insert new message in room database and set in recycler view chat
    private void updateRecyclerView(String msg) {
        String date;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.format(new Date());
        chatLayoutModelList.add(new ChatLayoutModel(msg, date, true));
        chatRecyclerViewAdapter.notifyDataSetChanged();
        //set random delay from 0 to 0.5 and insert the message twice then notify recycler view
        //get random number from 0 to 500 (500 = 0.5 second)
        Random random = new Random();
        int delay = random.nextInt(501);
        //add the message to recycler view
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatLayoutModelList.add(new ChatLayoutModel(msg, date, false));
                chatLayoutModelList.add(new ChatLayoutModel(msg, date, false));
                chatRecyclerViewAdapter.notifyDataSetChanged();
                chatRecyclerView.smoothScrollToPosition(chatLayoutModelList.size());
                NotificationHelper helper = new NotificationHelper(getActivity());
                NotificationCompat.Builder nb = helper.getChannel1Notification("Reply delay", delay + " Millisecond");
                helper.getManager().notify(1, nb.build());
            }
        }, delay);
    }
    //set arguments in bundle to pass variable from mainActivity to chat fragment
    public static ChatFragment newInstance(String text, Bitmap image) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, text);
        args.putParcelable(USER_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }
    //call onFragmentInteraction when fragment close to update recyclerView im mainActivity
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: detect fragment close ");
        mListener.onFragmentInteraction();
        mListener = null;
    }
    //user this interface to detect when close the chat fragment or can pass variable from chat fragment to mainActivity
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
