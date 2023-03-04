package com.example.parkfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class StickerSharing extends AppCompatActivity {
    private String curUsername;
    private ImageView selectedStickerImageView;
    private DatabaseReference databaseReference;
    private Spinner receiverSpinner;
    private String receiverUsername;
    private Map<ImageView, StickerType> stickerImageViewToType = new HashMap<>();
    private StickerType selectedStickerType;
    private final String FIREBASE_TAG = "FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curUsername = getIntent().getExtras().getString("username");
        setContentView(R.layout.activity_sticker_sharing);

        // show welcome info
        TextView txtWelcomeInfo = findViewById(R.id.welcomeInfo);
        txtWelcomeInfo.setText(getString(R.string.welcome_info) + curUsername);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        initReceiverList();
        initStickerImageViewToTypeMap();

    }

    // select the sticker clicked
    public void clickSticker(View view) {
        if (selectedStickerImageView != null) {
            selectedStickerImageView.setColorFilter(null);
            selectedStickerType = null;
        }
        selectedStickerImageView = (ImageView) view;
        selectedStickerImageView.setColorFilter(R.color.purple_200);
        selectedStickerType = stickerImageViewToType.get(selectedStickerImageView);
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
                        receiverSet.remove(curUsername);
                        Log.d(FIREBASE_TAG, receiverSet.toString());
                        receiverSpinner = findViewById(R.id.spinner_select_receiver);
                        receiverSpinner.setAdapter(new ArrayAdapter<>(this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                receiverSet.toArray()));
                    }
                });
    }

    private void initStickerImageViewToTypeMap() {
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_bee), StickerType.BEE);
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_crocodile), StickerType.CROCODILE);
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_fox), StickerType.FOX);
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_panda), StickerType.PANDA);
    }


    // send sticker and add the record to database
    public void sendSticker(View view) {
        if (selectedStickerImageView == null) {
            Toast.makeText(this, "Please select a sticker.", Toast.LENGTH_LONG).show();
            return;
        }
        receiverUsername = receiverSpinner.getSelectedItem().toString();
        String curTime =
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        StickerRecord record =
                new StickerRecord(selectedStickerType, curUsername, receiverUsername, curTime);

        addSendingCountToDatabase();
        addReceivingRecordToDatabase(record);
    }

    // add sending record to database, path: sent/username/strStickerType/strCount
    private void addSendingCountToDatabase() {
        databaseReference.child("sent")
                .child(curUsername)
                .child(selectedStickerType.toString())
                .get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(FIREBASE_TAG, "Error reading sending count", task.getException());
                    } else {
                        int existingCount;
                        if (task.getResult().getValue() == null) {
                            existingCount = 0;
                        } else {
                            existingCount = Integer.parseInt((String) task.getResult().getValue());
                        }
                        Log.d(FIREBASE_TAG,
                                "existing count for " + selectedStickerType.toString() +
                                        " " +
                                        "is: " +
                                        existingCount);
                        // update count for this sticker type
                        String newCount = String.valueOf(existingCount + 1);
                        databaseReference.child("sent")
                                .child(curUsername)
                                .child(selectedStickerType.toString())
                                .setValue(newCount);
                        Log.d(FIREBASE_TAG,
                                "new count for " + selectedStickerType.toString() +
                                        " " +
                                        "is: " +
                                        newCount);
                        Toast.makeText(this, "Send successfully.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // add receiving record to database, path: received/receiverUsername/autoUid/stickerRecord
    private void addReceivingRecordToDatabase(StickerRecord record) {
        databaseReference.child("received")
                .child(receiverUsername)
                .push()
                .setValue(record);
        Log.d(FIREBASE_TAG, record.getCompleteRecord());
    }
}