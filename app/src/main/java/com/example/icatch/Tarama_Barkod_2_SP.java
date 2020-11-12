package com.example.icatch;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Tarama_Barkod_2_SP extends AppCompatActivity {
    SurfaceView surfaceView;
    int scancounter = 0;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    public FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseUser currentuser;
    DatabaseReference myRef;
    int toplampuan; //Puanların toplamı
    int hafizdanokunandeger; //score that is read from memory
    FileOutputStream outputStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tara_barkod_2);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        currentuser = firebaseAuth.getCurrentUser();
        myRef = database.getReference("users");



        surfaceView = (SurfaceView) findViewById(R.id.CameraP);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding

                    return;
                }
                try{
                    cameraSource.start(holder);
                }
                catch(IOException e){
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() { //processes the data
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // qr okunduktan sonra
                final SparseArray<Barcode> qrcodes =detections.getDetectedItems(); //detect the value of detetcted item

                if ((qrcodes.size()!=0) && scancounter == 0)
                {
                    scancounter++;
                    Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(10);
                    toplampuan = Integer.parseInt(qrcodes.valueAt(0).displayValue);


                    //Used for Reading Points data from database
                    myRef.child(currentuser.getUid()).child("points").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("UID",currentuser.getUid());
                            String textscoreString = dataSnapshot.getValue(String.class);
                            hafizdanokunandeger = Integer.parseInt(textscoreString);
                            toplampuan += hafizdanokunandeger; //veritabanın eklenen toplam
                            myRef.child(currentuser.getUid()).child("points").setValue(String.valueOf(toplampuan))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Successfully written to the database
                                                finish();
                                                Toast.makeText(getApplicationContext(),"Veri Yüklendi ", Toast.LENGTH_SHORT).show();

                                                Intent homepage = new Intent(getApplicationContext(), Hesap_SP.class); //Redirect to homepage if succesful scan
                                                startActivity(homepage);
                                            } else {
                                                Toast.makeText(getApplicationContext(),"Veri Yüklenemedi ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Failed to read value

                        }
                    });

                }

            }

        });
    }


}
