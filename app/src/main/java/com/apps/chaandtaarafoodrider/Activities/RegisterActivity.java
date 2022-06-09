package com.apps.chaandtaarafoodrider.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.chaandtaarafoodrider.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_username_signup, edit_email_signup, edit_pass_signup,phone_number;
    private Button button_create_account_signup;
    private TextView login;
    private ImageButton cancel;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edit_username_signup = findViewById(R.id.editText);
        edit_email_signup = findViewById(R.id.emailText);
        edit_pass_signup = findViewById(R.id.passText);
        phone_number=findViewById(R.id.phoneNumberText);
        login=findViewById(R.id.textView5);
        button_create_account_signup = findViewById(R.id.button);


        mAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        cancel=findViewById(R.id.imageButton);


        // On click listeners on buttons started
        button_create_account_signup.setOnClickListener(v -> validate());
        login.setOnClickListener(view -> {
            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    // Validite method to check the if user have left fields empty

    private void validate() {

        dialog.show();
        String name = edit_username_signup.getText().toString().trim();
        String email = edit_email_signup.getText().toString().trim();
        String pass = edit_pass_signup.getText().toString().trim();
        String number =phone_number.getText().toString().trim();

        if (name.isEmpty()) {
            edit_username_signup.setError("Name must contain value");
            edit_username_signup.requestFocus();
            dialog.dismiss();
        }

        else if (email.isEmpty()) {
            edit_email_signup.setError("Not a valid email");
            edit_email_signup.requestFocus();
            dialog.dismiss();
        }
        else if(!isEmailValid(email)){
            edit_email_signup.setError("Not a valid email");
            edit_email_signup.requestFocus();
            dialog.dismiss();
        }
        else if(number.isEmpty()){
            phone_number.setError("Phone number must contain value");
            phone_number.requestFocus();
            dialog.dismiss();
        }
        else if(number.length()!=11){
            phone_number.setError("Not a valid phone number");
            phone_number.requestFocus();
            dialog.dismiss();
        }
        else if (pass.isEmpty()) {
            edit_pass_signup.setError("Password must contain a value");
            edit_pass_signup.requestFocus();
            dialog.dismiss();
        } else if (pass.length() < 6) {
            edit_pass_signup.setError("Password must be greater then 6 digit");
            edit_pass_signup.requestFocus();
            dialog.dismiss();

        } else {
            createUser(email, pass,number);

        }

    }

    private void createUser(String email, String pass,String phoneNumber) {


        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                user = mAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                dataUpload(email, uid,phoneNumber);


            } else {

                dialog.dismiss();

                Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();

                Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void dataUpload(String email, String uid, String phoneNumber) {


        String Username = edit_username_signup.getText().toString();


        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        userMap.put("username", Username);
        userMap.put("phone", phoneNumber);
        userMap.put("id", uid);
        FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Intent intent = new Intent(RegisterActivity.this, EmailVerification.class);
                    startActivity(intent);
                    finish();
                } else {

                    dialog.dismiss();

                    Toast.makeText(RegisterActivity.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}