package com.example.serly.ghtoll.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.serly.ghtoll.R;
import com.example.serly.ghtoll.models.ReceiptModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PaymentHistoryAdapter extends FirebaseRecyclerAdapter<ReceiptModel, PaymentHistoryAdapter.ReceiptViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PaymentHistoryAdapter(@NonNull FirebaseRecyclerOptions<ReceiptModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position, @NonNull ReceiptModel model) {
        holder.ShowNetwork(model.getMoneyNetwork());
        holder.ShowAmount(model.getAmount());
        holder.ShowStatus(model.getStatus());
        holder.ShowVehicleref(model.getTransactionref());
        holder.Showtransactionref(model.getTransactionref());
        holder.ShowSenderNumber(model.getMobileNumber());
        holder.ShowVehicleLicense1(model.getVehiclelicense());
        holder.showQrCode(model.getQrCode());
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receipt_item, viewGroup, false);

        return new ReceiptViewHolder(view);
    }

    public class ReceiptViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        void ShowNetwork(String Network) {
            TextView txtName = view.findViewById(R.id.txtNetwork);
            txtName.setText(Network);
        }

        void ShowAmount(String Amount) {
            TextView vehicleNumb = view.findViewById(R.id.Txtamountofrequest);
            vehicleNumb.setText(Amount);
        }

        void ShowSenderNumber(String fromNumber) {
            TextView txtLicense = view.findViewById(R.id.TxtNumber);
            txtLicense.setText(fromNumber);
        }

        void ShowVehicleref(String vehicleref) {
            TextView txtChasisNumber = view.findViewById(R.id.Txttransactionid);
            txtChasisNumber.setText(vehicleref);
        }

        void ShowVehicleLicense1(String vehiclelicense1) {
            TextView txtChasisNumber = view.findViewById(R.id.txtvehicleplatenumber);
            txtChasisNumber.setText(vehiclelicense1);
        }

        void ShowStatus(String status) {
            TextView txtName = view.findViewById(R.id.txtstatusofrequest);
            txtName.setText(status);
        }

        void Showtransactionref(String transactionref) {
            TextView vehicleNumb = view.findViewById(R.id.Txttransactionid);
            vehicleNumb.setText(transactionref);
        }

        void showQrCode(String qrCode) {
            ImageView imgQrCode = view.findViewById(R.id.qrcodepicture);
            Glide.with(view).load(qrCode).into(imgQrCode);
        }


    }

}
