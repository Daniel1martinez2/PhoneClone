package com.example.phoneclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase db;
    private EditText name, contra;
    private Button next;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        name = findViewById(R.id.name);
        next =  findViewById(R.id.next);
        next.setOnClickListener(this);
        contra = findViewById(R.id.contra);
        auth = FirebaseAuth.getInstance();

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.next:
                auth.signInWithEmailAndPassword(name.getText().toString(), contra.getText().toString()).addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){
                                Intent i = new Intent(this, Contacts.class);
                                startActivity(i);
                            }
                        }
                );
                break;
        }
    }
}