package com.example.serly.ghtoll;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.serly.ghtoll.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private TextInputLayout UserFirstname, UserLastname, UserEmail, UserPassword, UserVPassword, UserNumber;
    private Button createaccountbutton;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private ProgressDialog loadingbar;
    private DatabaseReference UserRef;
    String CurrentUserid, firstname, lastname, email, password,confirmpassword,number, fullname;
    private static final String TAG = "Register";
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();




//vibration
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        UserRef = FirebaseDatabase.getInstance().getReference("Users");

        UserFirstname = findViewById(R.id.TxtFirstName);
        UserLastname = findViewById(R.id.TxtLastName);
        UserEmail = findViewById(R.id.txtEmailsignup);
        UserPassword = findViewById(R.id.TxtPasswordsignup);
        UserVPassword = findViewById(R.id.TxtComfirmPasswordSignup);
        UserNumber = findViewById(R.id.TxtMobileNumberSignup);
        createaccountbutton = findViewById(R.id.btnregisterSignup);
        loadingbar = new ProgressDialog(this);


        createaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createnewaccount();

            }
        });
    }

    private void createnewaccount() {
        firstname = UserFirstname.getEditText().getText().toString();
        lastname = UserLastname.getEditText().getText().toString();
        email = UserEmail.getEditText().getText().toString();
        password = UserPassword.getEditText().getText().toString();
        confirmpassword = UserVPassword.getEditText().getText().toString();
        number = UserNumber.getEditText().getText().toString();
        fullname = firstname + " " + lastname;

        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(this, "pls enter First Name", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(this, "pls enter Last Name", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "pls enter valid Email", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "pls enter Password", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(this, "pls Confirm Password", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "pls enter Phone number", Toast.LENGTH_LONG).show();
        }





        if (!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(number) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirmpassword))
        {
            if (password.equals(confirmpassword) && password.length()<8  )
            {
                Toast.makeText(this, "pls enter a password with at least 8 characters ", Toast.LENGTH_LONG).show();
            }


            if (!TextUtils.isEmpty(number) && number.length()<10 ) {
                Toast.makeText(this, "Please ,phone number field only receives 10 digits", Toast.LENGTH_LONG).show();
            }
            if (!TextUtils.isEmpty(number) && number.length()>10 ) {
                Toast.makeText(this, "Please ,phone number field only receives 10 digits", Toast.LENGTH_LONG).show();
            }

            if (password.equals(confirmpassword) && password.length()>=8 && number.length()==10 )
            {

                loadingbar.setMessage("Please wait while we  create your new account");
                loadingbar.show();
                loadingbar.setCancelable(false);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            firebaseUser = mAuth.getCurrentUser();
                            CurrentUserid = firebaseUser.getUid();

                            //class created within the files
                            final User user = new User(CurrentUserid,firstname, lastname, email, number, fullname);

                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {

                                        UserRef.child(CurrentUserid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful())
                                                {
                                                    loadingbar.dismiss();
                                                    //vibrates to alert success for android M and above
                                                    if (Build.VERSION.SDK_INT >= 26) {
                                                        vibrator.vibrate(VibrationEffect.createOneShot
                                                                (2000, VibrationEffect.DEFAULT_AMPLITUDE));
                                                    } else {
                                                        //vibrate below android M
                                                        vibrator.vibrate(2000);

                                                    }
                                                    new AlertDialog.Builder(Register.this)
                                                            .setMessage("Hello" + " " + firstname + " "
                                                                    + lastname + " " + "\n" + "an email verification link has been sent to " + email + "\n" +
                                                                    "please verify to continue")
                                                            .setPositiveButton("OK",
                                                                    new DialogInterface.OnClickListener
                                                                            () {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
//
                                                                            dialog.dismiss();

                                                                            startActivity(new Intent(Register.this,LogInActivity.class));
                                                                            finish();


                                                                        }
                                                                    })
                                                            .create()
                                                            .show();

                                                } else {
                                                    loadingbar.dismiss();
                                                    Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                                       }

                                            }
                                        });


                                    } else {
                                        Toast toast = Toast.makeText(Register.this, " Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                    }
                                }
                            });


                        } else
                            {
                            String message = task.getException().getMessage();
                            Toast.makeText(Register.this, "error occurred" + message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                           }


                    }
                });

            } else {
                Toast toast = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();}


        }



    }

   /* @Override
    protected void onPause() {
        super.onPause();
        try
        {
            SharedPreferences preferences = getSharedPreferences("RegistrationDetails", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firstname", UserFirstname.getEditText().getText().toString());
            editor.putString("lastname", UserLastname.getEditText().getText().toString());
            editor.putString("email", UserEmail.getEditText().getText().toString());
            editor.putString("number", UserNumber.getEditText().getText().toString());
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            SharedPreferences sharedPreferences = getSharedPreferences("RegistrationDetails", MODE_PRIVATE);
            String fname = sharedPreferences.getString("firstname", "");
            String lname = sharedPreferences.getString("lastname", "");
            String email = sharedPreferences.getString("email", "");
            String unumber =sharedPreferences.getString("number","");

            UserFirstname.getEditText().setText(fname);
            UserLastname.getEditText().setText(lname);
            UserEmail.getEditText().setText(email);
            UserNumber.getEditText().setText(unumber);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
}
