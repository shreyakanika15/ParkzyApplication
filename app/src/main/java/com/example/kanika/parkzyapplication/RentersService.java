package com.example.kanika.parkzyapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class RentersService extends AppCompatActivity {

    private EditText nUserName;
    private EditText nLat;
    private EditText nLong;
    private EditText nAvail;
    private EditText nPrice;
    private Button nAddName;
    private Button nLogout;
    private Button nSelectImage;
    private Firebase nRootRef;
    private StorageReference nStorage;
    private static final int GALLERY_INTENT = 2;

    private ProgressDialog nProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renters);
        nStorage = FirebaseStorage.getInstance().getReference();
        nRootRef = new Firebase("https://parkzyapplication.firebaseio.com/Users");
        nUserName = (EditText) findViewById(R.id.nameValue);
        nLat = (EditText) findViewById(R.id.latValue);
        nLong = (EditText) findViewById(R.id.longVal);
        nAvail = (EditText) findViewById(R.id.availValue);
        nPrice = (EditText) findViewById(R.id.priceValue);
        nAddName = (Button) findViewById(R.id.addName);
        nSelectImage = (Button) findViewById(R.id.addImage);
        nLogout = (Button) findViewById(R.id.logout);
        nProgressDialog = new ProgressDialog(this);

        nSelectImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });





        nAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pushValue();

            }
        });

        nLogout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (view == nLogout) {
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileService.class));

                }
            }

        });
    }

    public void pushValue() {

        onDetailsAdd();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            nProgressDialog.setMessage("Uploading ...");
            nProgressDialog.show();

            Uri uri = data.getData();

            StorageReference filePath = nStorage.child("Images").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(RentersService.this, "Upload Done.",Toast.LENGTH_LONG).show();
                    nProgressDialog.dismiss();
                }
            });
        }
    }

    private void onDetailsAdd(){

        Map<String, String> userMap = new HashMap<String, String>();
        Firebase childRef=nRootRef.push();

        try {
            userMap.put("name", nUserName.getText().toString());
            userMap.put("latitude", nLat.getText().toString());
            userMap.put("longitude", nLong.getText().toString());
            userMap.put("availability", nAvail.getText().toString());
            userMap.put("price", nPrice.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

            childRef.setValue(userMap);

    }









}
