package com.example.phoneclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity implements View.OnClickListener {
    private EditText name, number;
    private Button addContact, exit;
    private FirebaseDatabase db;
    private ContactAdaptor adaptor;
    private ListView contactsList;
    private  String id;
    FirebaseAuth auth;
    User  user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser() ==null){
            goToLogin();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.CAMERA
            }, 1);

            addContact = findViewById(R.id.addContact);
            name = findViewById(R.id.name);
            number = findViewById(R.id.number);


            contactsList = findViewById(R.id.contactList);
            adaptor = new ContactAdaptor();
            adaptor.getActivityRef(this);
            recoverUser();
            addContact.setOnClickListener(this);
            exit = findViewById(R.id.logout);
            exit.setOnClickListener(this);

        }



    }

    private void goToLogin(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    void recoverUser(){
        if(auth.getCurrentUser()!=null){

            String id = auth.getCurrentUser().getUid();


             db.getReference().child("users").child(id).addListenerForSingleValueEvent(
                     new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           user = snapshot.getValue(User.class);

                            adaptor.getUserRef(user.getId());
                            contactsList.setAdapter(adaptor);
                            loadDatabase();



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        }

    }
    private void loadDatabase(){

        DatabaseReference ref = db.getReference().child("contacts").child(user.getId());
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        adaptor.clear();
                        for(DataSnapshot child: data.getChildren()){
                            Contact contact = child.getValue(Contact.class);
                            adaptor.addCOntact(contact);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addContact:

                id = db.getReference().child("contacts").push().getKey();
                DatabaseReference reference = db.getReference().child("contacts").child(user.getId()).child(id);
                Contact contact = new Contact(id, name.getText().toString(), number.getText().toString());
                reference.setValue(contact);

                break;
            case R.id.logout:
                    auth.signOut();
                    finish();

                break;
        }

    }

    public void call(Intent i){
        startActivity(i);
    }
}