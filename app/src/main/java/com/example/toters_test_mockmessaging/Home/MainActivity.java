package com.example.toters_test_mockmessaging.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toters_test_mockmessaging.Chat.ChatFragment;
import com.example.toters_test_mockmessaging.ImageConverter.ImageConvert;
import com.example.toters_test_mockmessaging.R;
import com.example.toters_test_mockmessaging.RecyclerView.MainRecyclerViewAdapter;
import com.example.toters_test_mockmessaging.RecyclerView.MainRecyclerViewItem;
import com.example.toters_test_mockmessaging.SharedPref.Shared;
import com.example.toters_test_mockmessaging.UsersDB.User;
import com.example.toters_test_mockmessaging.UsersDB.UsersDatabase;
import com.example.toters_test_mockmessaging.UsersDB.UsersDisplay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private RecyclerView mainRecyclerView;
    private MainRecyclerViewAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<UsersDisplay> usersDisplays;
    ArrayList<MainRecyclerViewItem> arrayList = new ArrayList<>();
    private static final String USERS_EXIST = "USERS_EXIST";
    private static final String USERS_NOT_EXIST = "USERS_NOT_EXIST";
    private List<User> userList;
    private UsersDatabase db;
    // private View toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: start activity");
        db = UsersDatabase.getInstance(getApplicationContext());
        usersDisplays = db.userDao().getAllUsersDisplay();
        String sharedString = Shared.sharedRead(getApplicationContext(), "usersdb", USERS_NOT_EXIST);
        Log.d(TAG, "onCreate: shared string " + sharedString);
        //check if no users in database. if no set 200 users
        if (USERS_NOT_EXIST.equals(Shared.sharedRead(getApplicationContext(), "usersdb", USERS_NOT_EXIST)) ^ usersDisplays == null) {
            Log.d(TAG, "onCreate: go to setup 200 user");
            setupUsersInRoomDB();
        }
        init();
    }
    //set demo 200 user
    private void setupUsersInRoomDB() {
        Log.d(TAG, "setupUsersInRoomDB: start to setup list of 200 user");
        userList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < 200; i++) {
            switch (j) {
                case 0:
                    Log.d(TAG, "setupUsersInRoomDB: case 0 set the user with image one");
                    userList.add(new User("user" + i, getImageOne()));
                    j++;
                    break;
                case 1:
                    userList.add(new User("user" + i, getImageTwo()));
                    j++;
                    break;
                case 2:
                    userList.add(new User("user" + i, getImageThree()));
                    j = 0;
                    break;
            }
        }

        db.userDao().insertListOfUser(userList);
        Log.d(TAG, "setupUsersInRoomDB: insert list of users in room database");
        Shared.sharedSave(MainActivity.this, "usersdb", USERS_EXIST);
        Toast.makeText(getApplicationContext(), "finish set database", Toast.LENGTH_LONG).show();
    }

    /**
     * init elements in mainActivity
     */
    private void init() {
        Log.d(TAG, "init: init recyclerview");
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        CircleImageView profileImage = findViewById(R.id.displayProfileImage);
        profileImage.setVisibility(View.GONE);
        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setVisibility(View.GONE);
        TextView title = findViewById(R.id.profileName);
        title.setText(getString(R.string.mock_messaging));
        usersDisplays.clear();
        usersDisplays = db.userDao().getAllUsersDisplay();
        recyclerAdapter = new MainRecyclerViewAdapter(usersDisplays);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new MainRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                ChatFragment chatFragment = ChatFragment.newInstance(usersDisplays.get(position).getUserName(),
                        ImageConvert.convertByteArrayToImage(usersDisplays.get(position).getProfileImage()));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_enter_from_rigth, R.anim.fragment_exit_to_right,
                        R.anim.fragment_enter_from_rigth, R.anim.fragment_exit_to_right);
                fragmentTransaction.addToBackStack("ChatFragment");
                fragmentTransaction.add(R.id.chat_container, chatFragment, "CHAT_FRAGMENT").commit();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resume activity");
    }
    //get fake image from drawable
    private byte[] getImageOne() {
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.image0);
        return ImageConvert.convertImage2ByteArray(icon);
    }

    private byte[] getImageTwo() {
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.image1);
        return ImageConvert.convertImage2ByteArray(icon);
    }

    private byte[] getImageThree() {
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.image2);
        return ImageConvert.convertImage2ByteArray(icon);
    }
    //implement this interface to detect when chat fragment close to refresh main recyclerView
    @Override
    public void onFragmentInteraction() {
        Log.d(TAG, "onFragmentInteraction: done");
                Log.d(TAG, "run: start thread");
                usersDisplays = db.userDao().getAllUsersDisplay();
                recyclerAdapter.updateList(usersDisplays);
                mainRecyclerView.smoothScrollToPosition(0);
        Log.d(TAG, "onFragmentInteraction: notify recyclerview");
    }
}