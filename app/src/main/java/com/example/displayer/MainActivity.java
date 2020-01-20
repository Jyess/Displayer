package com.example.displayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String USER_INPUT = "";
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.input);
                String userInput = input.getText().toString();

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(USER_INPUT, userInput);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView img = findViewById(R.id.img);
        img.setVisibility(View.VISIBLE);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                img.setImageResource(R.drawable.yes);
                Toast.makeText(getApplicationContext(), "Good choice", Toast.LENGTH_SHORT).show();
            } else {
                img.setImageResource(R.drawable.no);
                Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i(TAG, "what");
        }
    }
}
