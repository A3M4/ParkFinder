package com.example.parkfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import static com.example.parkfinder.StickerSharing.FIREBASE_TAG;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StickerReceivingHistory extends AppCompatActivity {
    private String curUsername;
    private DatabaseReference dbReference;
    private RecyclerView historyRecyclerView;
    private List<StickerRecord> records;
    private StickerHistoryAdapter stickerHistoryAdapter;
    private final HashMap<StickerType, Integer> typeToDrawable = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curUsername = getIntent().getExtras().getString("curUsername");
        setContentView(R.layout.activity_sticker_receiving_history);
        initTypeToDrawableMap();

        historyRecyclerView = (RecyclerView) findViewById(R.id.history_recyclerview);

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setHasFixedSize(true);

        dbReference = FirebaseDatabase.getInstance().getReference();

        records = new ArrayList<>();

        clearData();

        stickerHistoryAdapter = new StickerHistoryAdapter(this, records, typeToDrawable);
        historyRecyclerView.setAdapter(stickerHistoryAdapter);

        dbReference.child("received").child(curUsername)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        records.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            StickerRecord record = dataSnapshot.getValue(StickerRecord.class);
                            records.add(record);
                        }
                        stickerHistoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(FIREBASE_TAG, "loadPost:onCancelled", error.toException());
                    }
                });

    }

    private void clearData() {
        if (records != null) {
            records.clear();
        }
        records = new ArrayList<>();
    }

    private void initTypeToDrawableMap() {
        typeToDrawable.put(StickerType.BEE, R.drawable.bee);
        typeToDrawable.put(StickerType.CROCODILE, R.drawable.crocodile);
        typeToDrawable.put(StickerType.FOX, R.drawable.fox);
        typeToDrawable.put(StickerType.PANDA, R.drawable.panda);
    }
}