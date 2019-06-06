package com.example.serly.ghtoll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference UserRef;
    private static final String TAG = "MainActivity";
    private TextView FullName,Email;
    private CircleImageView userImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navigationHeader = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        FullName = navigationHeader.findViewById(R.id.txtfullname);
        Email = navigationHeader.findViewById(R.id.txtemail);
        userImage = navigationHeader.findViewById(R.id.userprofileimg);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null)
        {
            return;
        }
        String userId = firebaseUser.getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        UserRef.keepSynced(true);



        BottomNavigationView bottomNavView =findViewById(R.id.bottomview);
       // final PayFragment paymentfrag=new PayFragment();
        final PaymentHistoryFragment historyfrag =new PaymentHistoryFragment();
        final AddVehicleFragment addvehicle =new AddVehicleFragment();

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if (id==R.id.Vehicles)
                {
                    toolbar.setTitle("Vehicles");
                    setfragment(addvehicle);
                    return true;
                }
                else if (id==R.id.PayToll)
                {
                    //toolbar.setTitle("PayToll");
                   // setfragment(paymentfrag);
                   // return true;

                    toolbar.setTitle("Vehicles");
                    setfragment(addvehicle);
                    startActivity(new Intent(MainActivity.this, PaymentActivity.class));
                    return true;
                }
                else if (id==R.id.History)
                {
                    toolbar.setTitle("History");
                    setfragment(historyfrag);
                    return true;
                }

                return false;
            }
        });
        bottomNavView.setSelectedItemId(R.id.Vehicles);



    }

    public void setfragment(Fragment fragment)
    {
        FragmentTransaction fragmenttransaction=getSupportFragmentManager().beginTransaction();
        fragmenttransaction.replace(R.id.frame,fragment);
        fragmenttransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_terms) {

            return true;
        }
        else if (id ==R.id.action_help) {


            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        switch (item.getItemId()) {
            case R.id.action_History:
                startActivity(new Intent(this, MainActivity.class));
                break;


            case R.id.action_Editprofile:
                startActivity(new Intent(this, Editprofile.class));
                break;

            case R.id.action_setting:
               // startActivity(new Intent(this, customlayoutjava.class));
                break;

            case R.id.action_logout:

                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("Do you really want to Logout")
                        .setCancelable(true)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                try{
                                    if (firebaseUser!=null)
                                    {
                                        mAuth.signOut();
                                        Intent Login = new Intent(MainActivity.this, Splashscreen.class);
                                        Login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(Login);
                                        finish();

                                    }
                                }
                                catch( Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert= a_builder.create();
                alert.setTitle("Alert!!!");
                alert.show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SendUserToLoginActivity() {
        Intent Login = new Intent(MainActivity.this, Splashscreen.class);
        startActivity(Login);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            assert firebaseUser != null;

            if (mAuth.getCurrentUser() == null || !firebaseUser.isEmailVerified()) {
                SendUserToLoginActivity();
            } else {
                Log.d(TAG, "onStart: successful");
                retrieveUserDetails();

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void retrieveUserDetails() {

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        Log.d(TAG, "onDataChange: user profile exists");
                        //retrieve the details and set the on the users profile
                        String Fullname = (String) dataSnapshot.child("fullName").getValue();
                        String email = (String) dataSnapshot.child("email").getValue();
                        String showImage = (String) dataSnapshot.child("thumbImage").getValue();
                        FullName.setText(Fullname);
                        Email.setText(email);
                        Glide.with(getApplicationContext()).load(showImage).into(userImage);


                    } else {
                        Log.d(TAG, "No details: a default photo has be replaced");
                        Glide.with(getApplicationContext()).load(R.drawable.defaultavatar).into(userImage);
                    }}
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                try {
                    Log.d(TAG, "Error : " + databaseError.getMessage());
                }
                catch (Exception e )
                { e.printStackTrace();}
            }
        });

    }

}
