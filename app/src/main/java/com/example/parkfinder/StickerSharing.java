package com.example.parkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class StickerSharing extends AppCompatActivity {
    private String curUserName;
    private ImageView selectedStickerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curUserName = getIntent().getExtras().getString("username");
        setContentView(R.layout.activity_sticker_sharing);

        // show welcome info
        TextView txtWelcomeInfo = findViewById(R.id.welcomeInfo);
        txtWelcomeInfo.setText(getString(R.string.welcome_info) + curUserName);

    }

    // select the sticker clicked
    public void clickSticker(View view) {
        if (selectedStickerImageView != null) {
            selectedStickerImageView.setColorFilter(null);
        }
        selectedStickerImageView = (ImageView) view;
        selectedStickerImageView.setColorFilter(R.color.purple_200);
    }


}