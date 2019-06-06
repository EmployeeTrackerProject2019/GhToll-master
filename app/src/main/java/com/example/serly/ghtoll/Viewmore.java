package com.example.serly.ghtoll;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Viewmore extends DialogFragment {

    private CircleImageView profileImage;
    private DatabaseReference userDbRef,vehicledbref;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    String uid;
    TextView txtName ;
    TextView vehicleNumb ;
    TextView txtLicense ;
    TextView txtChasisNumber ;
    private static final String TAG = "View More";
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Simple Dialog");
        return inflater.inflate(R.layout.activity_viewmore, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;

        setupdisplay();

    }

    private void setupdisplay() {

        txtName = view.findViewById(R.id.textViewShowFullName);
        vehicleNumb = view.findViewById(R.id.textViewShowVehicleIdf);
        txtLicense =view.findViewById(R.id.textViewShowLicensef);
        txtChasisNumber = view.findViewById(R.id.textViewShowChasisNumberf);
        profileImage=view.findViewById(R.id.profileimage);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            return;
        }
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();

        vehicledbref = FirebaseDatabase.getInstance().getReference().child("VehicleAdded");
        vehicledbref.keepSynced(true);
        // mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        userDbRef.keepSynced(true);

        retrievevehicledetails();
        retrieveuserdetails();


    }


    private void retrievevehicledetails() {

        vehicledbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String showlicense = (String) dataSnapshot.child("vehicleLicenseNumber").getValue();
                    String showchassisnumber = (String) dataSnapshot.child("vehicleChasisNumber").getValue();
                    String showplate = (String) dataSnapshot.child("vehicleNumber").getValue();
                    Log.d(TAG, "Result : " +showchassisnumber);
                    vehicleNumb.setText(showplate);
                    txtChasisNumber.setText(showchassisnumber);
                    txtLicense.setText(showlicense);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast toast = Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });


    }

    private void retrieveuserdetails() {

        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String showFirstName = (String) dataSnapshot.child("firstName").getValue();
                    String showLastName = (String) dataSnapshot.child("lastName").getValue();
                    String showImage = (String) dataSnapshot.child("thumbImage").getValue();
                    /* display the values into the required fields */
                    String fullName = showFirstName+" "+showLastName;
                    txtName.setText(fullName);
                    Glide.with(getActivity()).load(showImage).into(profileImage);
                } else {
                    Glide.with(getActivity()).load(R.drawable.defaultavatar).into(profileImage);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast toast = Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });

    }

}
