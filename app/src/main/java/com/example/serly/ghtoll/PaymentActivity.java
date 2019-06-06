package com.example.serly.ghtoll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {


    private RadioGroup optionsselected;
    private Button btnproceed, btnpaywithcc;
    private RadioButton daily, weekly, monthly;
    private TextView displayamount;
    float amount ;
    String vehlicense;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        optionsselected = findViewById(R.id.options);
        btnproceed = findViewById(R.id.paywithmobilemoney);
        btnpaywithcc = findViewById(R.id.paywithcreditcard);
        daily = findViewById(R.id.Radiobutton_daily);
        weekly = findViewById(R.id.Radiobutton_Weekly);
        monthly = findViewById(R.id.Radiobutton_Monthly);
        displayamount = findViewById(R.id.Txtamountofrequest);
        optionsselected.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == daily.getId()) {
                    displayamount.setText("1 GHC");
                } else if (checkedId == weekly.getId()) {
                    displayamount.setText("7 GHC");
                } else if (checkedId == monthly.getId()) {
                    displayamount.setText("27 GHC");
                }

            }
        });
        proceedtopayment();

    }

    private void proceedtopayment() {

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = optionsselected.getCheckedRadioButtonId();

                if (selectedId==daily.getId())
                {
                    btnproceed.setEnabled(true);
                    amount= (float) 1;
                    makepayment();



                } else if (selectedId==weekly.getId()) {
                    btnproceed.setEnabled(true);
                    amount = 7;
                    makepayment();

                }

                else if  (selectedId==monthly.getId()) {
                    btnproceed.setEnabled(true);
                    amount = 27;
                    makepayment();

                }

                else {
                    Toast.makeText(PaymentActivity.this, "Please choose an option first ", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnpaywithcc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int selectedId = optionsselected.getCheckedRadioButtonId();

                if (selectedId==daily.getId())
                {
                    btnproceed.setEnabled(true);
                    amount= (float) 1;
                    pusingcc();



                } else if (selectedId==weekly.getId()) {
                    btnproceed.setEnabled(true);
                    amount = 7;
                    pusingcc();

                }

                else if  (selectedId==monthly.getId()) {
                    btnproceed.setEnabled(true);
                    amount = 27;
                    pusingcc();

                }

                else {
                    Toast.makeText(PaymentActivity.this, "Please choose an option first ", Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    private void pusingcc() {


        new RavePayManager(this).setAmount(amount)
                .setCountry("GH")
                .setCurrency("GHS")
                .setEmail("florenthouetchekpo@gmail.com")
                .setfName("Florent")
                .setlName("Houetchekpo")
                .setPublicKey("FLWPUBK-35a099c19b8733eb5e78953312fa0cf9-X")
                .setEncryptionKey("0b62740b0e21a979ac42f1ca")
                .setTxRef("trail log")
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(false)
                .acceptAchPayments(false)
                .acceptGHMobileMoneyPayments(false)
                .acceptUgMobileMoneyPayments(false)
                .onStagingEnv(false)
                .allowSaveCardFeature(false)
                .isPreAuth(true)
                .initialize();



    }

    private void makepayment() {
        new RavePayManager(this).setAmount(amount)
                .setCountry("GH")
                .setCurrency("GHS")
                .setEmail("florenthouetchekpo@gmail.com")
                .setfName("Florent")
                .setlName("Houetchekpo")
                .setPublicKey("FLWPUBK-35a099c19b8733eb5e78953312fa0cf9-X")
                .setEncryptionKey("0b62740b0e21a979ac42f1ca")
                .setTxRef("trail log")
                .acceptAccountPayments(true)
                .acceptCardPayments(false)
                .acceptMpesaPayments(false)
                .acceptAchPayments(false)
                .acceptGHMobileMoneyPayments(true)
                .acceptUgMobileMoneyPayments(false)
                .onStagingEnv(false)
                .allowSaveCardFeature(false)
                .isPreAuth(true)
                .initialize();



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {


               // Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "An error occured.sorry  " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {

                Intent intent1 = getIntent();
                vehlicense=intent1.getStringExtra("vehicleLicenseNumber");
                Intent intent = new Intent(this, customlayoutjava.class);
                intent.putExtra("Amount", amount);
                intent.putExtra("vehlicense", vehlicense);
                intent.putExtra("name", "Florent Houetchekpo");
                startActivity(intent);
                Toast.makeText(this, "The transaction has been cancelled .Ooops.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}