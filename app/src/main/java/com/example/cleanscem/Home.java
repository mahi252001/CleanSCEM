package com.example.cleanscem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    FloatingActionButton add;
    ImageView noItemTextView;
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<Complaint> arrayList = new ArrayList<>();
    public static String email,name;
    ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        name=Verify.name;
        email=Verify.email;
        add=findViewById(R.id.add);
        noItemTextView=findViewById(R.id.noItemTextView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Verify.name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.getKey().equals("Name") || dataSnapshot1.getKey().equals("Email"))
                    {

                    }
                    else
                    {
                        Complaint complaint = dataSnapshot1.getValue(Complaint.class);
                        arrayList.add(complaint);

                        if(arrayList.size() != 0)
                        {
                            noItemTextView.setVisibility(View.GONE);
                        }
                    }
                }
                recyclerViewAdapter = new RecyclerViewAdapter(arrayList,Home.this);
                recyclerView.setAdapter(recyclerViewAdapter);

                if(arrayList.size() == 0)
                {
                    noItemTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(arrayList.size() == 0)
        {
            noItemTextView.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(view -> startActivity(new Intent(Home.this,Add.class)));
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}