package com.example.parkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Set;

public class StickerSharing extends AppCompatActivity {
    private String curUserName;
    private ImageView selectedStickerImageView;
    private DatabaseReference databaseReference;
    private final String FIREBASE_TAG = "FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curUserName = getIntent().getExtras().getString("username");
        setContentView(R.layout.activity_sticker_sharing);

        // show welcome info
        TextView txtWelcomeInfo = findViewById(R.id.welcomeInfo);
        txtWelcomeInfo.setText(getString(R.string.welcome_info) + curUserName);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        initReceiverList();

    }

    // select the sticker clicked
    public void clickSticker(View view) {
        if (selectedStickerImageView != null) {
            selectedStickerImageView.setColorFilter(null);
        }
        selectedStickerImageView = (ImageView) view;
        selectedStickerImageView.setColorFilter(R.color.purple_200);
    }

    // init spinner of user list for selecting a sender (excluding themselves)
    private void initReceiverList() {
        databaseReference.child("users").get().addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        Log.e(FIREBASE_TAG, "Error getting receiver list", task.getException());
                    } else {
                        HashMap usernameToUser = (HashMap) task.getResult().getValue();
                        Set receiverSet = usernameToUser.keySet();
                        receiverSet.remove(curUserName);
                        Log.d(FIREBASE_TAG, receiverSet.toString());
                        Spinner receiverSpinner = findViewById(R.id.spinner_select_receiver);
                        receiverSpinner.setAdapter(new ArrayAdapter<>(this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                receiverSet.toArray()));

                    }
                });
    }


    public void sendSticker(View view) {
    }
}