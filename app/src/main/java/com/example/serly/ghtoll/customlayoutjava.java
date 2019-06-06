package com.example.serly.ghtoll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import androidmads.library.qrgenearator.QRGEncoder;

public class customlayoutjava extends AppCompatActivity {
    String messagetoprint, price, custnetworkprovider, status, custphone, transactionreference, vehicledriverslicensefromdatabase;
    FirebaseAuth firebaseAuth;
    String userId;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabaseReference;
    private static final String TAG = "Receipt";
    private StorageReference mStorageReference;
    Bitmap bitmap;
    float amount;
    Button btnsaveqr;
    ImageView iv;
    QRGEncoder qrgEncoder;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRIMAGES/";
    private ProgressDialog progressDialog;
    public final static int QRcodeWidth = 500;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
    DatabaseReference addingReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custompopup);
        TextView txtclose;
        iv = findViewById(R.id.iv);
        txtclose = findViewById(R.id.txtclose);
        btnsaveqr = findViewById(R.id.btnsaveqr);
        progressDialog = new ProgressDialog(this);


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenMainActivity = new Intent(customlayoutjava.this, MainActivity.class);
                startActivity(OpenMainActivity);
                finish();

            }
        });


        Intent intent = getIntent();
        vehicledriverslicensefromdatabase = intent.getStringExtra("vehlicense");
        amount = intent.getFloatExtra("Amount", amount);
        price = Float.toString(amount);
        custphone = "0240825978";
        status = "Successful ";
        custnetworkprovider = "MTN";
        transactionreference = "trail log";
        messagetoprint = "You have been deducted" + price + "ghc from" + custphone + "and your transaction reference is trail log";

        //firebase savedata
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() == null) {
            return;
        }
        assert firebaseUser != null;
        userId = firebaseUser.getUid();
        addingReference = FirebaseDatabase.getInstance().getReference("Receiptoftransactions");
        addingReference.keepSynced(true);

        mStorageReference = FirebaseStorage.getInstance().getReference("userPhotos");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        mDatabaseReference.keepSynced(true);



/*
        addingReference.child(uploadID).setValue(Receiptdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(customlayoutjava.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(customlayoutjava.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(customlayoutjava.this, "oops" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




*/

        try {
            bitmap = TextToImageEncode(messagetoprint);
            iv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        btnsaveqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String path = saveImage(bitmap);  //give read write permission
                //makeToast("QRCode saved to -> "+path);


//starting from here
                String path = saveImage(bitmap);
                Uri name = Uri.parse("file:///" + path);
                Log.d(TAG, "name of image : " + name);
                progressDialog.setMessage("loading");
                progressDialog.show();


                //                file path for the image
                final StorageReference fileReference = mStorageReference.child(userId + "." + name);


                fileReference.putFile(name).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            //throw task.getException();
                            Log.d(TAG, "then: " + task.getException().getMessage());
                            makeToast(task.getException().getMessage());
                            progressDialog.dismiss();

                        }
                        return fileReference.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            Uri downLoadUri = task.getResult();
                            assert downLoadUri != null;
                            String getImageUri = downLoadUri.toString();


                            //saving to database
                            HashMap<String, Object> Receiptdetails = new HashMap<>();
                            Receiptdetails.put("transactionref", transactionreference);
                            Receiptdetails.put("Amount", price);
                            Receiptdetails.put("MobileNumber", custphone);
                            Receiptdetails.put("Status", status);
                            Receiptdetails.put("userId", userId);
                            Receiptdetails.put("vehiclelicense", vehicledriverslicensefromdatabase);
                            Receiptdetails.put("moneyNetwork", custnetworkprovider);
                            Receiptdetails.put("timeStamp", ServerValue.TIMESTAMP);
                            Receiptdetails.put("qrCode", getImageUri);


                            String uploadID = addingReference.push().getKey();
                            //assert if uid!=null
                            assert uploadID != null;

                            addingReference.child(uploadID).setValue(Receiptdetails);
                            progressDialog.dismiss();
                            makeToast("Successful");
                        }

                    }
                });

            }
        });

    }

    void makeToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.


        Log.d(TAG, "Wall Paper: " + wallpaperDirectory.getName());

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");


            f.createNewFile();   //give read write permission
            Log.d(TAG, "saveImage: " + f);
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());


            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath(); // changed from f.getAbsolutePath
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }


    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


}


