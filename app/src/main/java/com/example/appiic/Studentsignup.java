package com.example.appiic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Studentsignup extends AppCompatActivity {

    private TextView textView5;
    private EditText editText4;
    private EditText editText5;
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    private EditText editText6;
    private EditText editText7;
    private EditText editText8;
    private Button button5;
    private String dept;
    private String Year;
    private String section;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsignup);

        button5=(Button)findViewById(R.id.button5);
        textView5=(TextView)findViewById(R.id.textView5);
        editText4=(EditText)findViewById(R.id.editText4);
        editText5=(EditText)findViewById(R.id.editText5);
        editText6=(EditText)findViewById(R.id.editText6);
        editText7=(EditText)findViewById(R.id.editText7);
        editText8=(EditText)findViewById(R.id.editText8);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
        final MediaPlayer mediaPlayer1=MediaPlayer.create(this,R.raw.buttonsound2);

        List<String> categories=new ArrayList<>();
        categories.add(0,"Department");
        categories.add("CSE");
        categories.add("AUTO");
        categories.add("MECH");
        categories.add("CIVIL");
        categories.add("EEE");
        categories.add("EIE");
        categories.add("ECE");
        categories.add("IT");

        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("Department")){
                    //do nothing
                    dept=null;
                }
                else{
                    //on selecting sppiner item
                    String item=parent.getItemAtPosition(position).toString();
                    //show spiner item
                    Toast.makeText(parent.getContext(),"selected:"+item,Toast.LENGTH_SHORT).show();
                    //anything u want do here
                    dept=spinner.getSelectedItem().toString();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO AUTOGENERATED METHOD DO HERE

            }
        });

        List<String> year=new ArrayList<>();
        year.add(0,"Year");
        year.add("I");
        year.add("II");
        year.add("III");
        year.add("IV");

        ArrayAdapter<String> yearAdapter;
        yearAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,year);

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(yearAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("Year")){
                    //do nothing
                    Year=null;
                }
                else{
                    //on selecting sppiner item
                    String item=parent.getItemAtPosition(position).toString();
                    //show spiner item
                    Toast.makeText(parent.getContext(),"selected:"+item,Toast.LENGTH_SHORT).show();
                    //anything u want do here
                    Year=spinner2.getSelectedItem().toString();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO AUTOGENERATED METHOD DO HERE

            }
        });

        List<String> Section=new ArrayList<>();
        Section.add(0,"Section");
        Section.add("A");
        Section.add("B");
        Section.add("C");


        ArrayAdapter<String> SectionAdapter;
        SectionAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,Section);

        SectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setAdapter(SectionAdapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("Section")){
                    //do nothing
                    section=null;
                }
                else{
                    //on selecting sppiner item
                    String item=parent.getItemAtPosition(position).toString();
                    //show spiner item
                    Toast.makeText(parent.getContext(),"selected:"+item,Toast.LENGTH_SHORT).show();
                    //anything u want do here
                    section=spinner3.getSelectedItem().toString();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO AUTOGENERATED METHOD DO HERE

            }
        });
        mAuth = FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("student");

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Username = editText4.getText().toString();
                final String IDNo = editText5.getText().toString();
                final String Email = editText6.getText().toString();
                final String MobileNumber = editText8.getText().toString();
                email = editText6.getText().toString();
                password = editText7.getText().toString();

                mediaPlayer1.start();

                if (editText4.length() == 0) {
                    editText4.setError("Enter Username!");
                } else if (editText5.length() == 0) {
                    editText5.setError("Enter ID-no!");
                } else if (editText6.length() == 0) {
                    editText6.setError("Enter Mail-id!");
                } else if (editText7.length() == 0) {
                    editText7.setError("Enter Password!");
                } else if (editText8.length() == 0 || editText8.length() != 10) {
                    editText8.setError("Enter Mobile-no!");
                }
                else if(dept==null){
                    Toast.makeText(Studentsignup.this, " Select Department",
                            Toast.LENGTH_SHORT).show();
                }
                else if(Year==null){
                    Toast.makeText(Studentsignup.this, " Select Year",
                            Toast.LENGTH_SHORT).show();
                }
                else if(section==null){
                    Toast.makeText(Studentsignup.this, " Select Section",
                            Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Studentsignup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        final student information = new student(
                                                Username,
                                                IDNo,
                                                dept,
                                                Year,
                                                section,
                                                Email,
                                                MobileNumber
                                        );

                                        FirebaseDatabase.getInstance().getReference("student")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                Toast.makeText(Studentsignup.this, "Authentication success.",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Studentsignup.this, student_qr.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                    } else {
                                        Toast.makeText(Studentsignup.this, "Authentication failed.",
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
        Intent intent=new Intent(Studentsignup.this,Register.class);
        startActivity(intent);
    }
}