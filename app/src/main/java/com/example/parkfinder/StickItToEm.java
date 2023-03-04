package com.example.parkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickItToEm extends AppCompatActivity {
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_it_to_em);
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    // user sign in
    public void onClickSignIn(View view) {
        EditText userInput = findViewById(R.id.editTextUserName);
        String userName = userInput.getText().toString();
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a non-empty username.", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(userName);
        // add user to database, use the username as the key
        dbReference.child("users").child(userName).setValue(user);
        Toast.makeText(this, "Sign in successfully.", Toast.LENGTH_LONG).show();

        Intent stickerIntent = new Intent(this, StickerSharing.class);
        stickerIntent.putExtra("username", userName);
        startActivity(stickerIntent);
    }
}