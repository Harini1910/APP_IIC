package com.example.appiic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyLogin extends AppCompatActivity {
    private EditText t1,t2;
    private Button b1;
    CheckBox checkBox_faculty;
    private FirebaseAuth mAuth;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        t1=findViewById(R.id.editText);
        t2=findViewById(R.id.editText2);
        b1=findViewById(R.id.button2);
        checkBox_faculty=findViewById(R.id.checkBox);

        SharedPreferences preferencess=getSharedPreferences("checkbox_faculty",MODE_PRIVATE);
        String checkbox_faculty=preferencess.getString("remember","");
        if (checkbox_faculty.equals("true")){
            Intent intent=new Intent(FacultyLogin.this,faculty_profile.class);
            startActivity(intent);
            finish();
        }

        final MediaPlayer mediaPlayer1=MediaPlayer.create(this,R.raw.buttonsound2);
        mAuth = FirebaseAuth.getInstance();

        checkBox_faculty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferencess=getSharedPreferences("checkbox_faculty",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferencess.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    //Toast.makeText(FacultyLogin.this,"Checked",Toast.LENGTH_SHORT).show();
                }else if(!compoundButton.isChecked()){
                    SharedPreferences preferencess=getSharedPreferences("checkbox_faculty",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferencess.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    //Toast.makeText(FacultyLogin.this,"Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = t1.getText().toString();
                password = t2.getText().toString();

                mediaPlayer1.start();

                if (t1.length() == 0) {
                    t1.setError("Enter Mail-Id!");
                } else if (t2.length() == 0) {
                    t2.setError("Enter Password!");
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(FacultyLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(FacultyLogin.this, faculty_profile.class);
                                        startActivity(intent);
                                        Toast.makeText(FacultyLogin.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        Toast.makeText(FacultyLogin.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent=new Intent(FacultyLogin.this,Loginactivity.class);
        startActivity(intent);
    }
}
