package com.example.parkfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class StickerSharing extends AppCompatActivity {
    private String curUsername;
    private ImageView selectedStickerImageView;
    private DatabaseReference databaseReference;
    private Spinner receiverSpinner;
    private String receiverUsername;
    private StickerType selectedStickerType;
    private boolean isFirstNotification = true;
    private final Map<ImageView, StickerType> stickerImageViewToType = new HashMap<>();
    private final Map<StickerType, Bitmap> stickerTypeToBitmap = new HashMap<>();
    static final String FIREBASE_TAG = "FIREBASE";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create NotificationChannel for newer version Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("My Notification", "My Notification",
                            NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        curUsername = getIntent().getExtras().getString("username");
        setContentView(R.layout.activity_sticker_sharing);

        // show welcome info
        TextView txtWelcomeInfo = findViewById(R.id.welcomeInfo);
        txtWelcomeInfo.setText(getString(R.string.welcome_info) + curUsername);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        initReceiverList();
        initStickerImageViewToTypeMap();
        showSendingCount();

        getSickerTypeToBitmap();
        getReceivedDataChange();
    }

    // get current user's received data change
    private void getReceivedDataChange() {
        databaseReference.child("received").child(curUsername).orderByKey().limitToLast(1)
                .addChildEventListener(
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot,
                                                     @Nullable String previousChildName) {
                                StickerRecord newReceivedRecord =
                                        snapshot.getValue(StickerRecord.class);
                                if (isFirstNotification) {
                                    isFirstNotification = false;
                                } else {
                                    Log.d(FIREBASE_TAG, "I am a receiver， received record: " +
                                            newReceivedRecord.getCompleteRecord());
                                    // send notification
                                    // cited from: https://developer.android.com/develop/ui/views/notifications/expanded
                                    NotificationCompat.Builder builder =
                                            new NotificationCompat.Builder(StickerSharing.this,
                                                    "My Notification");
                                    builder.setContentTitle("New Sticker Notification");
                                    builder.setContentText("Hello, " + curUsername +
                                            ", you received a new sticker from " +
                                            newReceivedRecord.getSenderUsername() + "!");
                                    builder.setSmallIcon(R.drawable.ic_new_notification);
                                    builder.setLargeIcon(stickerTypeToBitmap.get(
                                            newReceivedRecord.getStickerType()));
                                    builder.setAutoCancel(true);

                                    NotificationManagerCompat managerCompat =
                                            NotificationManagerCompat.from(StickerSharing.this);
                                    if (ActivityCompat.checkSelfPermission(StickerSharing.this,
                                            android.Manifest.permission.POST_NOTIFICATIONS) !=
                                            PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        return;
                                    }
                                    managerCompat.notify(1, builder.build());
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot,
                                                       @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot,
                                                     @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
    }


    private void getSickerTypeToBitmap() {
        stickerTypeToBitmap.put(StickerType.BEE,
                BitmapFactory.decodeResource(getResources(), R.drawable.bee));
        stickerTypeToBitmap.put(StickerType.CROCODILE,
                BitmapFactory.decodeResource(getResources(), R.drawable.crocodile));
        stickerTypeToBitmap.put(StickerType.FOX,
                BitmapFactory.decodeResource(getResources(), R.drawable.fox));
        stickerTypeToBitmap.put(StickerType.PANDA,
                BitmapFactory.decodeResource(getResources(), R.drawable.panda));
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
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    return;
                }
                List<String> receiverList = new ArrayList<>();
                for (DataSnapshot username: snapshot.getChildren()) {
                    if (username.getKey().equals(curUsername)) {
                        continue;
                    }
                    receiverList.add(username.getKey());
                }

                Log.d(FIREBASE_TAG, receiverList.toString());
                receiverSpinner = findViewById(R.id.spinner_select_receiver);
                receiverSpinner.setAdapter(new ArrayAdapter<>(StickerSharing.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, receiverList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(FIREBASE_TAG, "Error getting receiver list", error.toException());
            }
        });
    }

    private void initStickerImageViewToTypeMap() {
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_bee), StickerType.BEE);
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_crocodile),
                StickerType.CROCODILE);
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_fox), StickerType.FOX);
        stickerImageViewToType.put((ImageView) findViewById(R.id.sticker_panda), StickerType.PANDA);
    }


    @SuppressLint("SetTextI18n")
    private void showSendingCount() {
        TextView sentBeeCnt = findViewById(R.id.txt_bee_cnt);
        TextView sentCrocodileCnt = findViewById(R.id.txt_crocodile_cnt);
        TextView sentFoxCnt = findViewById(R.id.txt_fox_cnt);
        TextView sentPandaCnt = findViewById(R.id.txt_panda_cnt);

        databaseReference.child("sent")
                .child(curUsername)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            sentBeeCnt.setText("Already Sent: 0");
                            sentCrocodileCnt.setText("Already Sent: 0");
                            sentFoxCnt.setText("Already Sent: 0");
                            sentPandaCnt.setText("Already Sent: 0");
                        } else {
                            if (snapshot.hasChild(StickerType.BEE.toString())) {
                                sentBeeCnt.setText(
                                        "Already Sent: " + snapshot.child(StickerType.BEE.toString()).getValue(String.class));
                            } else {
                                sentBeeCnt.setText("Already Sent: 0");
                            }
                            if (snapshot.hasChild(StickerType.CROCODILE.toString())) {
                                sentCrocodileCnt.setText(
                                        "Already Sent: " + snapshot.child(StickerType.CROCODILE.toString()).getValue(String.class));
                            } else {
                                sentCrocodileCnt.setText("Already Sent: 0");
                            }
                            if (snapshot.hasChild(StickerType.FOX.toString())) {
                                sentFoxCnt.setText(
                                        "Already Sent: " + snapshot.child(StickerType.FOX.toString()).getValue(String.class));
                            } else {
                                sentFoxCnt.setText("Already Sent: 0");
                            }
                            if (snapshot.hasChild(StickerType.PANDA.toString())) {
                                sentPandaCnt.setText(
                                        "Already Sent: " + snapshot.child(StickerType.PANDA.toString()).getValue(String.class));
                            } else {
                                sentPandaCnt.setText("Already Sent: 0");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(FIREBASE_TAG, "Error reading all sending counts", error.toException());
                    }
                });
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

    // get the received stickers history for current user
    public void getReceivingHistory(View view) {
        Intent historyIntent = new Intent(this, StickerReceivingHistory.class);
        historyIntent.putExtra("curUsername", curUsername);
        startActivity(historyIntent);
    }
}