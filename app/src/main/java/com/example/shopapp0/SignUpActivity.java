package com.example.shopapp0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        auth = FirebaseAuth.getInstance();

        final TextInputEditText createEmailEt = findViewById(R.id.createEmailEtEditText);
        final TextInputEditText createPasswordEt = findViewById(R.id.createPasswordEtEditText);

        Button signUp = findViewById(R.id.signUpBtn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Objects.requireNonNull(createEmailEt.getText()).toString();
                String password = Objects.requireNonNull(createPasswordEt.getText()).toString();

                if (!name.isEmpty() && !password.isEmpty()) {
                    if (password.length()<8){
                        Toast.makeText(getApplicationContext() , "Enter Your Password More 8 Characters" , Toast.LENGTH_SHORT).show();
                    }else {
                    Task<AuthResult> task = auth.createUserWithEmailAndPassword(name, password);
                    task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }else {
                    Toast.makeText(getApplicationContext() , "Email or Password Empty" , Toast.LENGTH_SHORT).show();
                }
            }
        });













    }
}
