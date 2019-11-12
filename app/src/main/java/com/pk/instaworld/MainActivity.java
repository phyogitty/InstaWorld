package com.pk.instaworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    Button btnAddPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "I am in MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAddPost = findViewById(R.id.btnAddPost);

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddPostActivity.class);
                startActivity(i);
            }
        });
    }
}
