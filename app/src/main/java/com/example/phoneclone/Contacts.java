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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity implements View.OnClickListener {
    private EditText name, number;
    private Button addContact;
    private String currentUser;
    private FirebaseDatabase db;
    private ContactAdaptor adaptor;
    private ListView contactsList;
    private  String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA

        }, 1);

        addContact = findViewById(R.id.addContact);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);

        db = FirebaseDatabase.getInstance();
        contactsList = findViewById(R.id.contactList); 
        currentUser=  getIntent().getExtras().getString("username");
        adaptor = new ContactAdaptor();
        adaptor.getActivityRef(this);
        adaptor.getUserRef(currentUser);
        contactsList.setAdapter(adaptor);
        addContact.setOnClickListener(this);
        loadDatabase();
    }
    private void loadDatabase(){
        DatabaseReference ref = db.getReference().child("contacts").child(currentUser);
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
                DatabaseReference reference = db.getReference().child("contacts").child(currentUser).child(id);
                Contact contact = new Contact(id, name.getText().toString(), number.getText().toString());
                reference.setValue(contact);

                break;
        }
    }

    public void call(Intent i){
        startActivity(i);
    }
}