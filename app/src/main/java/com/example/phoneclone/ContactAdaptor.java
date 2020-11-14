package com.example.phoneclone;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactAdaptor extends BaseAdapter {
    private ArrayList<Contact> contacts;
    private String userRef;
    private Contacts app;
    public ContactAdaptor() {
        contacts = new ArrayList<>();
    }
    public void addCOntact(Contact contact){

        contacts.add(contact);
        notifyDataSetChanged();
    }
    public void getUserRef(String userRef){

        this.userRef = userRef;
    }
    public void getActivityRef(Contacts app){
        this.app = app;
    }
    @Override
    public int getCount() {
        return contacts.size();
    }
    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int pos, View renglon, ViewGroup lista) {
        LayoutInflater inflater = LayoutInflater.from(lista.getContext());
        View renglonView = inflater.inflate(R.layout.row, null);
        Contact contact = contacts.get(pos);
        TextView name = renglonView.findViewById(R.id.contactName);
        TextView number = renglonView.findViewById(R.id.contactNumber);
        Button delete = renglonView.findViewById(R.id.delete);
        Button call = renglonView.findViewById(R.id.call);
        //---->
        delete.setOnClickListener(
                (v)->{
                    String id = contact.getId();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("contacts").child(userRef).child(id);
                    reference.setValue(null);
                }
        );
        name.setText(contact.getName());
        number.setText(contact.getNumber());
        call.setOnClickListener(
                (v)->{
                    String s = "tel:"+number.getText().toString();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(s));
                  //app.call(i);
                    lista.getContext().startActivity(i);

                }
        );
        return renglonView;
    }
    public void clear() {
        contacts.clear();
        notifyDataSetChanged();
    }
}
