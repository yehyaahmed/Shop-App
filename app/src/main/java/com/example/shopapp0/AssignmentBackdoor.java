package com.example.shopapp0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AssignmentBackdoor extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseFirestore firestore;
    static Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_backdoor);

        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = getSharedPreferences("flag", MODE_PRIVATE).edit();
        editor.putBoolean("flag" , flag);
        editor.apply();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Button showData = findViewById(R.id.showAllDataBackdoorBtn);
        Button disabledData = findViewById(R.id.disabledAllDataBackdoorBtn);

        showData.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
               flag = true;
                editor.putBoolean("flag" , flag);
                editor.commit();
            }
        });

        disabledData.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
                flag = false;
                editor.putBoolean("flag" , flag);
                editor.commit();
             }
        });
    }
}
