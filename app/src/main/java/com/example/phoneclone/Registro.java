package com.example.phoneclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseDatabase db;
    EditText nombre, corrreo, contra1;
    Button registrarse,login;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        nombre = findViewById(R.id.nombre);
        corrreo = findViewById(R.id.correo);
        registrarse = findViewById(R.id.registrarse);
        registrarse.setOnClickListener(this);
        contra1 = findViewById(R.id.contra1);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.registrarse:
            auth.createUserWithEmailAndPassword(corrreo.getText().toString(), contra1.getText().toString()).addOnCompleteListener(
                    task->{
                        if(task.isSuccessful()){
                            id = auth.getCurrentUser().getUid();
                            DatabaseReference reference = db.getReference().child("users").child(id);
                            User userCurrent = new User(id, nombre.getText().toString(), corrreo.getText().toString(), contra1.getText().toString());
                            reference.setValue(userCurrent).addOnCompleteListener(
                                    task1 -> {
                                        if (task1.isSuccessful()){
                                            Intent i = new Intent(this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                            );

                        }else {
                            Toast.makeText(this,task.getException().toString() ,Toast.LENGTH_LONG).show();
                        }
                    }
            );
        break;
        case R.id.login:
            Intent i = new Intent(this, MainActivity.class);
            break;
    }
    }
}