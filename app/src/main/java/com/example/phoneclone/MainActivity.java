package com.example.phoneclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase db;
    private EditText name;
    private Button next;
    private  String id;
    private    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        name = findViewById(R.id.name);
        next =  findViewById(R.id.next);
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.next:
                db.getReference().child("users").orderByChild("userName").equalTo(name.getText().toString()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot data) {
                                if(data.getValue()==null){
                                    id = db.getReference().child("users").push().getKey();
                                    DatabaseReference reference = db.getReference().child("users").child(id);
                                    User userCurrent = new User(id, name.getText().toString());
                                    reference.setValue(userCurrent);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        }
                );
                username =  name.getText().toString();
                Intent i = new Intent(this, Contacts.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
        }
    }
}