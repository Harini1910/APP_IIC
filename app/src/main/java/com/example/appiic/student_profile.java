package com.example.appiic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

public class student_profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TextView t1,t2,t3,t4,t5,t6,t7;
    private  String Username,IDNo,Dept,Email,MobileNumber,Year,Section;
    public CircleImageView circle;
    private static final int PICK_IMAGE = 1;
    private static final String TAG ="Profile_page";
    public Uri imageUri;
    public Uri resultUri;
    public  Bitmap profilepic;
    DatabaseReference databaseReference,profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        circle = (CircleImageView) findViewById(R.id.circle);
        t1 = findViewById(R.id.username);
        t2 = findViewById(R.id.idno);
        t3 = findViewById(R.id.dept);
        t4 = findViewById(R.id.year);
        t5 = findViewById(R.id.section);
        t6=findViewById(R.id.mail);
        t7=findViewById(R.id.mobileno);

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(student_profile.this)
                        .load(user.getPhotoUrl())
                        .into(circle);
            }
        }

        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(user.getUid());
            profile = FirebaseDatabase.getInstance().getReference().child("profile").child("images");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Username = (Objects.requireNonNull(dataSnapshot.child("Username").getValue())).toString();
                    IDNo = (Objects.requireNonNull(dataSnapshot.child("IDNo").getValue())).toString();
                    Dept = (Objects.requireNonNull(dataSnapshot.child("Dept").getValue())).toString();
                    Year = (Objects.requireNonNull(dataSnapshot.child("Year").getValue())).toString();
                    Section = (Objects.requireNonNull(dataSnapshot.child("Section").getValue())).toString();
                    Email = (Objects.requireNonNull(dataSnapshot.child("Email").getValue())).toString();
                    MobileNumber = (Objects.requireNonNull(dataSnapshot.child("MobileNumber").getValue())).toString();

                    t1.setText(Username);
                    t2.setText(IDNo);
                    t3.setText(Dept);
                    t4.setText(Year);
                    t5.setText(Section);
                    t6.setText(Email);
                    t7.setText(MobileNumber);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(gallery, "select picture"), PICK_IMAGE);


                }

            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode  == RESULT_OK && data != null) {
            imageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(result==null){
                Toast.makeText(student_profile.this,"You cancelled uploading profile image!",Toast.LENGTH_SHORT).show();
            }else {
                resultUri = result.getUri();
                if(resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                        circle.setImageBitmap(bitmap);
                        profilepic=bitmap;
                        handleUpload(bitmap);
                        Toast.makeText(student_profile.this,"Updated Successfully!",Toast.LENGTH_LONG).show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child( ""+ ".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e.getCause() );
                    }
                });
    }
    public void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        setUserProfileUrl(uri);
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setUserProfileUrl(Uri uri) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        Objects.requireNonNull(user).updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(student_profile.this, "Profile image failed...", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void Signout(View v){
        PopupMenu popupMenu=new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_student);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        SharedPreferences preferences = getSharedPreferences("checkbox_student", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();

        SharedPreferences sharedPreferences= getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editors=sharedPreferences.edit();
        editors.putBoolean("isFirsttime",true);
        editors.apply();

        Intent intent=new Intent(student_profile.this,MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent=new Intent(student_profile.this,student_qr.class);
        startActivity(intent);
    }


}
