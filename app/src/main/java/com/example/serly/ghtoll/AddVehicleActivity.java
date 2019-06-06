package com.example.serly.ghtoll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout inputvehiclenumber, inputchassisnumber, inputdriverslicense;
    private DatabaseReference AddingReference, usersDbRef;
    private ProgressDialog loading;
    private String getUsersId;
    private static final String TAG = "Add Vehicle ";
    Button btnadd;
    String username,userImage;
    private Spinner spinner1;
    String spinnercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        Toolbar toolbar = findViewById(R.id.addvehicletoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            return;
        }
        assert mFirebaseUser != null;
        getUsersId = mFirebaseUser.getUid();


        AddingReference = FirebaseDatabase.getInstance().getReference("VehicleAdded");
        usersDbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUsersId);

        usersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    username= (String) dataSnapshot.child("fullName").getValue();
                    userImage = (String) dataSnapshot.child("image").getValue();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnadd = findViewById(R.id.buttonaddvehicle);
        loading = new ProgressDialog(this);
        inputvehiclenumber = findViewById(R.id.txtvehiclenumber);
        inputchassisnumber = findViewById(R.id.txtchasisnumber);
        inputdriverslicense = findViewById(R.id.txtdriverslicense);

        btnadd.setOnClickListener(this);
        //spinner code
        spinner1 =  findViewById(R.id.spinner1);
       // addListenerOnButton();
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());


    }

  /*  private void addListenerOnButton() { }*/


    private void AddnewVehicle() {

                    String vnumber = spinnercode +"-" + inputvehiclenumber.getEditText().getText().toString();
                    String vchassisnumber = inputchassisnumber.getEditText().getText().toString();
                    String vdriverslicense = inputdriverslicense.getEditText().getText().toString();

                    if (!TextUtils.isEmpty(vnumber) && !TextUtils.isEmpty(vchassisnumber) && !TextUtils.isEmpty(vdriverslicense) ) {
                        loading.setMessage("Loading Please wait...");
                        loading.setCancelable(false);

                        loading.show();

                        HashMap<String, Object> Vehicledetails = new HashMap<>();
                        Vehicledetails.put("userName",username);
                        Vehicledetails.put("vehicleNumber", vnumber);
                        Vehicledetails.put("vehicleChasisNumber", vchassisnumber);
                        Vehicledetails.put("userId", getUsersId);
                        Vehicledetails.put("vehicleLicenseNumber",vdriverslicense);
                        Vehicledetails.put("userImage", userImage);
                        Vehicledetails.put("timeStamp", ServerValue.TIMESTAMP);


                        Intent intent1 = new Intent(this, PaymentActivity.class);
                        intent1.putExtra("vehicleLicenseNumber",vdriverslicense);
                        startActivity(intent1);

                        String uploadID = AddingReference.push().getKey();
                        //assert if uid!=null
                        assert uploadID != null;

                        Log.d(TAG, "onClick: " + username);

                        AddingReference.child(uploadID).setValue(Vehicledetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(AddVehicleActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                                   /*Intent OpenMainActivity = new Intent(AddVehicleActivity.this, MainActivity.class);
                                    startActivity(OpenMainActivity);
                                    finish();*/
                                } else
                                    {
                                    loading.dismiss();
                                    Toast.makeText(AddVehicleActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.dismiss();
                                Toast.makeText(AddVehicleActivity.this, "oops" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        loading.dismiss();
                        Toast.makeText(AddVehicleActivity.this, "Please enter all details to Add a vehicle", Toast.LENGTH_SHORT).show();
                    }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonaddvehicle:
                AddnewVehicle();
                break;
        }

    }

    private class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        spinnercode = parent.getItemAtPosition(pos).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}
