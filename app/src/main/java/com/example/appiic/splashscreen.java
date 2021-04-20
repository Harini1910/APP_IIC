package com.example.appiic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashscreen extends AppCompatActivity {

    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        img=(ImageView)findViewById(R.id.logo);
        Animation vecl= (Animation) AnimationUtils.loadAnimation(splashscreen.this,R.anim.mytransition);
        img.startAnimation(vecl);
        final Intent in=new Intent(splashscreen.this,MainActivity.class);

        Thread timer =new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    boolean isFirsttime;
                    SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editors = sharedPreferences.edit();
                    isFirsttime = sharedPreferences.getBoolean("isFirsttime", true);
                    if (isFirsttime) {
                        editors.putBoolean("isFirsttime", false);
                        editors.apply();
                        Intent intent = new Intent(splashscreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        SharedPreferences preferences = getSharedPreferences("checkbox_student", MODE_PRIVATE);
                        String checkbox_student = preferences.getString("remember", "");
                        SharedPreferences preferencess = getSharedPreferences("checkbox_faculty", MODE_PRIVATE);
                        String checkbox_faculty = preferencess.getString("remember", "");
                        if (checkbox_student.equals("true")) {
                            Intent intent = new Intent(splashscreen.this, student_profile.class);
                            startActivity(intent);
                            finish();
                        }else if(checkbox_faculty.equals("true")){
                            Intent intent=new Intent(splashscreen.this, faculty_profile.class);
                            startActivity(intent);
                            finish();
                        } else{
                            Intent intent = new Intent(splashscreen.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }

            }
        };
        timer.start();



    }
}
