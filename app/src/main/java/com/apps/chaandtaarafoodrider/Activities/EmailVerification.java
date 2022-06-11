package com.apps.chaandtaarafoodrider.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerification extends AppCompatActivity {

    private static final String TAG = EmailVerification.class.getSimpleName();

    private Button sendVerifyLink, verifyEmail, logoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    SharedPreference sharedPreference;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);


        sendVerifyLink = findViewById(R.id.send_verification_link);
        verifyEmail = findViewById(R.id.verify_btn);
        logoutBtn = findViewById(R.id.logout_btn_email_verification);
        sharedPreference=new SharedPreference(this);

        mAuth = FirebaseAuth.getInstance();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);



        if(mAuth.getCurrentUser().isEmailVerified()){

            mAuth.getCurrentUser().reload();

            Intent intent=new Intent(EmailVerification.this, BottomNavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sharedPreference.setLoggedIn(true);
            startActivity(intent);
            finish();

        }







        sendVerifyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialog!=null)
                dialog.show();
                if(!mAuth.getCurrentUser().isEmailVerified()){
                    mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (dialog!=null)
                                dialog.dismiss();
                            Toast.makeText(EmailVerification.this, "We have send the link to your registered email address please check it", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(dialog!=null)
                            dialog.dismiss();
                            Toast.makeText(EmailVerification.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }





            }
        });


        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog!=null)
                    dialog.show();

                mAuth = FirebaseAuth.getInstance();


                mAuth.getCurrentUser().reload();

                if(!mAuth.getCurrentUser().isEmailVerified()){

                    mAuth.getCurrentUser().reload();

                    if(mAuth.getCurrentUser().isEmailVerified()){

                        Toast.makeText(EmailVerification.this, "Your Email is verified now", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(EmailVerification.this, BottomNavigationActivity.class);
                        SharedPreference sharedPreference = new SharedPreference(EmailVerification.this);
                        sharedPreference.setUserId(mAuth.getCurrentUser().getUid());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        if (dialog!=null)
                            dialog.dismiss();
                        finish();



                    }else{
                        if (dialog!=null)
                            dialog.dismiss();
                        Toast.makeText(EmailVerification.this, "Email not Verified", Toast.LENGTH_SHORT).show();



                    }


                }

                else{
                    if (dialog!=null)
                    dialog.dismiss();
                    SharedPreference sharedPreference = new SharedPreference(EmailVerification.this);
                    sharedPreference.setUserId(mAuth.getCurrentUser().getUid());
                    Toast.makeText(EmailVerification.this, "Your Email is verified now", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EmailVerification.this, BottomNavigationActivity.class));

                    finish();



                }



            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(EmailVerification.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                if(sharedPreference!=null)
                sharedPreference.clearSharedPrefs();
                startActivity(new Intent(EmailVerification.this, LoginActivity.class));
                finish();
            }
        });











    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.getCurrentUser().reload();
        if(mAuth.getCurrentUser().isEmailVerified()){
            SharedPreference sharedPreference = new SharedPreference(EmailVerification.this);
            sharedPreference.setUserId(mAuth.getCurrentUser().getUid());
            startActivity(new Intent(EmailVerification.this, BottomNavigationActivity.class));
            finish();

        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.getCurrentUser().reload();
        if(mAuth.getCurrentUser().isEmailVerified()){
            SharedPreference sharedPreference = new SharedPreference(EmailVerification.this);
            sharedPreference.setUserId(mAuth.getCurrentUser().getUid());
            startActivity(new Intent(EmailVerification.this, BottomNavigationActivity.class));
            finish();

        }
    }
}