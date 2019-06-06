package com.example.serly.ghtoll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serly.ghtoll.adapters.PaymentHistoryAdapter;
import com.example.serly.ghtoll.models.ReceiptModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PaymentHistoryFragment extends Fragment {

    private RecyclerView RecyclerViewHistoryofpayment;
    private View view;
    FirebaseAuth firebaseAuth;
    String userId;
    FirebaseUser firebaseUser;
    DatabaseReference ReceiptDbRef;
    private static final String TAG = "Receipt";

    //adapter
    PaymentHistoryAdapter adapter;

    public PaymentHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_history, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        SetUpPaymentRecycler();

    }


    private void SetUpPaymentRecycler() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        userId = firebaseUser.getUid();

        RecyclerViewHistoryofpayment = view.findViewById(R.id.RecyclerViewPayment);

        //Setting the layout for data bring printed from Firebase
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerViewHistoryofpayment.setLayoutManager(layoutManager);

        ReceiptDbRef = FirebaseDatabase.getInstance().getReference().child("Receiptoftransactions");
        ReceiptDbRef.keepSynced(true);

        //Query to retrieve information
        Query query = ReceiptDbRef.orderByChild("userId").equalTo(userId);
//
        FirebaseRecyclerOptions<ReceiptModel> options = new FirebaseRecyclerOptions.Builder<ReceiptModel>().setQuery(query, ReceiptModel.class).build();

        adapter = new PaymentHistoryAdapter(options);

        RecyclerViewHistoryofpayment.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
