package com.example.serly.ghtoll;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.serly.ghtoll.models.Vehicle;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AddVehicleFragment extends Fragment {
    private View view;
    private RecyclerView myvehiclesRecyclerView;
    private FloatingActionMenu addmore;
    FirebaseAuth firebaseAuth;
    String userId;
    FirebaseUser firebaseUser;
    DatabaseReference vehicleDbRef;
    Dialog myDialog;
    FirebaseRecyclerAdapter<Vehicle, VehicleViewHolder> adapter;


    public AddVehicleFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_vehicle, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        setUpRecycler();
    }

    private void setUpRecycler() {
        myDialog = new Dialog(getActivity());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        userId = firebaseUser.getUid();


        myvehiclesRecyclerView = view.findViewById(R.id.AddVehicleRecycler);
        myvehiclesRecyclerView.setVisibility(View.VISIBLE);
       myvehiclesRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myvehiclesRecyclerView.setLayoutManager(layoutManager);


        addmore = view.findViewById(R.id.fabaddmorevehicle);
        addmore.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addavehicle=new Intent(getActivity(),AddVehicleActivity.class);
                startActivity(addavehicle);
            }
        });



        vehicleDbRef = FirebaseDatabase.getInstance().getReference().child("VehicleAdded");
        vehicleDbRef.keepSynced(true);

        Query query = vehicleDbRef.orderByChild("userId").equalTo(userId);

        FirebaseRecyclerOptions<Vehicle> options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(query, Vehicle.class).build();


        adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleViewHolder holder, int position, @NonNull Vehicle model) {

//                holder.ShowImage(model.getUserImage());
                holder.ShowName(model.getUserName());
                holder.ShowChasisNumber(model.getVehicleChasisNumber());
                holder.ShowDvLicense(model.getVehicleLicenseNumber());
                holder.ShowVehicleNumber(model.getVehicleNumber());

                holder.btonviewmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* Intent i = new Intent(getActivity(),Viewmore.class);
                        startActivity(i);*/

                        FragmentManager fm = getFragmentManager();
                        Viewmore dialogFragment = new Viewmore ();
                        dialogFragment.show(fm, "Sample Fragment");
                    }
                });

            }

            @NonNull
            @Override
            public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = getLayoutInflater().inflate(R.layout.vehiclehistorylayout2, viewGroup, false);

                return new VehicleViewHolder(view);
            }
        };

        myvehiclesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    //Inner class
    private class VehicleViewHolder extends RecyclerView.ViewHolder {
        View view;
       // public   ConstraintLayout vehiclelayoutdetails;
        public Button btonviewmore;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
           // vehiclelayoutdetails= itemView.findViewById(R.id.vehiclelayoutdetails);
            btonviewmore=itemView.findViewById(R.id.buttonviewmore);

        }

//
//        void ShowImage(String urlOfImage) {
//            ImageView imageView = view.findViewById(R.id.imageViewShowUserProfile);
//
//            Glide.with(view).load(urlOfImage).into(imageView);
//        }

        void ShowName(String userName) {
            TextView txtName = view.findViewById(R.id.textViewShowFullName);
            txtName.setText(userName);
        }

        void ShowVehicleNumber(String vehicleNumber) {
            TextView vehicleNumb = view.findViewById(R.id.textViewShowVehicleId);
            vehicleNumb.setText(vehicleNumber);
        }

        void ShowDvLicense(String license) {
            TextView txtLicense = view.findViewById(R.id.textViewShowLicense);
            txtLicense.setText(license);
        }

        void ShowChasisNumber(String chasisNumber) {
            TextView txtChasisNumber = view.findViewById(R.id.textViewShowChasisNumber);
            txtChasisNumber.setText(chasisNumber);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
