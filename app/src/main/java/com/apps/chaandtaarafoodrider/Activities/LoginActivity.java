package com.apps.chaandtaarafoodrider.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_text_email_or_username, edit_text_password;

    private TextView forgot_your_password, text_view_create_account;
    private Button button_login;
    private ImageButton cancel;
    FirebaseDatabase db;
    Dialog dialog;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edit_text_email_or_username = findViewById(R.id.editText);
        edit_text_password = findViewById(R.id.passText);
        forgot_your_password = findViewById(R.id.textView3);
        text_view_create_account = findViewById(R.id.textView5);
        button_login = findViewById(R.id.button);
        cancel = findViewById(R.id.imageButton);
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        forgot_your_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));

            }
        });

        text_view_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));


            }
        });


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void validate() {

        dialog.show();
        if (edit_text_email_or_username.getText().toString().isEmpty()) {

            edit_text_email_or_username.setError("Email is Empty");
            edit_text_email_or_username.requestFocus();
            dialog.dismiss();

        } else if (edit_text_password.getText().toString().isEmpty()) {
            edit_text_password.setError("Password is Empty");
            edit_text_password.requestFocus();
            dialog.dismiss();

        } else if (!isEmailValid(edit_text_email_or_username.getText().toString().trim())) {
            edit_text_email_or_username.setError("Invalid email!");
            edit_text_email_or_username.requestFocus();
            dialog.dismiss();
        } else {

            authentication(edit_text_email_or_username.getText().toString().trim(), edit_text_password.getText().toString().trim());

        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void authentication(String email, String pass) {


        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    user = mAuth.getInstance().getCurrentUser();
                    if (user!= null) {
                        String uid = user.getUid();
                        SharedPreference sharedPreference = new SharedPreference(LoginActivity.this);
                        sharedPreference.setUserId(uid);
                    }
                    dialog.dismiss();
//                    SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
//                    editor.putString("profileid", user.getUid());
//                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, EmailVerification.class));
                    finish();


                } else {
                    dialog.dismiss();

                    Toast.makeText(LoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}