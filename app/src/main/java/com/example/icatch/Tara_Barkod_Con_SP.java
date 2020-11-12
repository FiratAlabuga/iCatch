package com.example.icatch;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
public class Tara_Barkod_Con_SP extends AppCompatActivity {
    ImageView qrImage;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    public FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    Button geridon;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tara_barkod_con);

        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser();
        UserID = currentuser.getUid();
        qrImage = (ImageView) findViewById(R.id.Code);
        geridon = (Button)findViewById(R.id.geridoner);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        qrgEncoder = new QRGEncoder(
                UserID, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try {
            Log.d("UID",UserID);
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
        }

        geridon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Hesap_SP.class));
            }
        });
    }
}
