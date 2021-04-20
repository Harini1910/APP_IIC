package com.example.appiic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Register extends AppCompatActivity {

    private Button button;
    private Button button6;
    private TextView textView;
    private ImageView imageView6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = (Button) findViewById(R.id.button);
        button6 = (Button) findViewById(R.id.button6);
        textView = (TextView) findViewById(R.id.textView);
        final MediaPlayer mediaPlayer1=MediaPlayer.create(this,R.raw.buttonsound2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.start();
                Intent intent = new Intent(Register.this, Studentsignup.class);
                startActivity(intent);
                finish();

            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.start();
                Intent intent = new Intent(Register.this, Facultysignup.class);
                startActivity(intent);
                finish();

            }
        });

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent=new Intent(Register.this,MainActivity.class);
        startActivity(intent);
    }
}
