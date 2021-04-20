package com.example.appiic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Loginactivity extends AppCompatActivity {

    private Button student;
    private Button faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        student=findViewById(R.id.button9);
        faculty=findViewById(R.id.button10);
        final MediaPlayer mediaPlayer1=MediaPlayer.create(this,R.raw.buttonsound2);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.start();
                Intent intent=new Intent(Loginactivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.start();
                Intent intent=new Intent(Loginactivity.this,FacultyLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent=new Intent(Loginactivity.this,MainActivity.class);
        startActivity(intent);
    }
}
