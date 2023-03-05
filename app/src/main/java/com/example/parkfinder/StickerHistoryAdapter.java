package com.example.parkfinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class StickerHistoryAdapter extends RecyclerView.Adapter<StickerHistoryViewHolder> {
    private Context context;
    private List<StickerRecord> records;
    private final HashMap<StickerType, Integer> typeToDrawable;

    public StickerHistoryAdapter(Context context, List<StickerRecord> records,
                                 HashMap<StickerType, Integer> typeToDrawable) {
        this.context = context;
        this.records = records;
        this.typeToDrawable = typeToDrawable;
    }


    @NonNull
    @Override
    public StickerHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerHistoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sticker_record_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StickerHistoryViewHolder holder, int position) {
        StickerRecord record = records.get(position);

        holder.receivedSticker.setImageResource(typeToDrawable.get(record.getStickerType()));
        holder.sender.setText("Sender: " + record.getSenderUsername());
        holder.receivingTime.setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
