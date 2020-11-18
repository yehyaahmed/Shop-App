package com.example.shopapp0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class LogInActivity extends AppCompatActivity {

    FirebaseAuth auth;
    public static String name;
    ProgressDialog pd;
    int timer = 0;
    TextInputEditText emailEt;
    TextInputEditText passwordEt;
    Button logIn;
    Button signUp;
    TextView timerTv;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        auth = FirebaseAuth.getInstance();
        timerTv = findViewById(R.id.timerTv);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        emailEt = findViewById(R.id.emailEtEditText);
        passwordEt = findViewById(R.id.passwordEtEditText);
        logIn = findViewById(R.id.logInBtn);
        signUp = findViewById(R.id.createAccountBtn);
        timerTv.setVisibility(View.INVISIBLE);

        editor = getSharedPreferences("timer", MODE_PRIVATE).edit();
        editor.putInt("timer" , timer);
        editor.apply();


        if (firebaseUser != null){
            Intent intent = new Intent(getApplicationContext() , ContentActivity.class);
            startActivity(intent);
            name = firebaseUser.getEmail();
        }else {
          //  Toast.makeText(getApplicationContext() , "User Null" , Toast.LENGTH_SHORT).show();
        }

        pd = new ProgressDialog(this , R.style.CustomPD);



        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setMessage("Loading . . .");
                pd.show();

                String name = Objects.requireNonNull(emailEt.getText()).toString();
                String password = Objects.requireNonNull(passwordEt.getText()).toString();

                if (!name.isEmpty() && !password.isEmpty()) {

                    if (name.equals("admin") && password.equals("admin")) {
                        Intent intent = new Intent(getApplicationContext(), AssignmentBackdoor.class);
                        startActivity(intent);
                    }

                    if (password.length() >8){
                        Toast.makeText(getApplicationContext() , "Your Password is Less Than 8 Characters" , Toast.LENGTH_SHORT).show();
                    }else {
                    Task<AuthResult> task = auth.signInWithEmailAndPassword(name, password);
                    task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                                startActivity(intent);
                                pd.dismiss();
                            }
                        }
                    });
                    task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Email or Password Error", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                timer++;
                                editor.putInt("timer" , timer);
                                editor.commit();
                                if (timer == 3) {
                                    Timer();
                                }
                            }
                        });
                }
            }else {
                    Toast.makeText(getApplicationContext() , "Email or Password Empty" , Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    timer++;
                    editor.putInt("timer" , timer);
                    editor.commit();
                    if (timer == 3) {
                        Timer();
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext() , ContentActivity.class);
            startActivity(intent);

        }

    }
    public void Timer(){

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timerTv.setVisibility(View.VISIBLE);
                        logIn.setEnabled(false);
                        signUp.setEnabled(false);

                    }
                });

                for (int i=10;i>=1;i--){
                   final int t=i;
                    timerTv.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            timerTv.setText(t+"");

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timerTv.setVisibility(View.INVISIBLE);
                        logIn.setEnabled(true);
                        signUp.setEnabled(true);

                    }
                });
            }
        }).start();
        timer=0;
        editor.putInt("timer" , timer);
        editor.commit();
    }
}

