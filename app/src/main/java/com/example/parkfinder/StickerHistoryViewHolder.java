package com.example.parkfinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerHistoryViewHolder extends RecyclerView.ViewHolder {
    ImageView receivedSticker;
    TextView sender;
    TextView receivingTime;

    public StickerHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        receivedSticker = itemView.findViewById(R.id.sticker_in_record);
        sender = itemView.findViewById(R.id.sender_username);
        receivingTime = itemView.findViewById(R.id.send_time);
    }
}
