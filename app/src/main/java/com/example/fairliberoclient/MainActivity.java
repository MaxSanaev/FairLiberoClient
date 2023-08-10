package com.example.fairliberoclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.fairliberoclient.view.AddTeamActivity;


public class MainActivity extends AppCompatActivity {
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(Listener);
    }

    private View.OnClickListener Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           Intent myIntent = new Intent(MainActivity.this, AddTeamActivity.class);
            myIntent.putExtra("test",1);
            MainActivity.this.startActivity(myIntent);
        }
    };
}