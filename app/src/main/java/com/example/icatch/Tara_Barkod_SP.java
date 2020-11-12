package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
public class Tara_Barkod_SP extends AppCompatActivity implements View.OnClickListener {
    String cardnumber = "";
    String cardname = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tara_barkod);
        cardname = getIntent().getStringExtra("Kart Adı");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Tara_Barkod_SP.this);
        builder1.setMessage("Kart Numarasını El İle Giriş Veya Taratarak Mı Yapmak İstersiniz ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Tara",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        scannow();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "El İle Giriş",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Tara_Barkod_SP.this,Kart_EkleM_SP.class);
                        intent.putExtra("Kart Adı",cardname);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents() == null)
            {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                alertdialogbuilder.setMessage("Sonuç Bulunamadı, Kart Numaranızı Yazmak İster Misiniz?");
                alertdialogbuilder.setTitle("El İle Giriş");
                alertdialogbuilder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Tara_Barkod_SP.this,Kart_EkleM_SP.class);
                        intent.putExtra("Kart Adı",cardname);
                        startActivity(intent);

                    }
                });
                alertdialogbuilder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannow();
                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();
            }
            else
            {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                cardnumber = result.getContents();
                alertdialogbuilder.setMessage(result.getContents() + "\n\nTekrar Taramak İster Misin?");
                alertdialogbuilder.setTitle("Sonucu Tara");
                alertdialogbuilder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannow();
                    }
                });
                alertdialogbuilder.setNeutralButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Tara_Barkod_SP.this,Kart_EkleM_SP.class);
                        intent.putExtra("Kart Adı",cardname);
                        intent.putExtra("Kart Numarası",cardnumber);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();
            }
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void scannow()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(Portre_SP.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Kartınızı Tarayınız");
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onClick(View v) {
        scannow();
    }

    public void clickbutton(View v)
    {
        Intent intent = new Intent(Tara_Barkod_SP.this,Kart_EkleM_SP.class);
        intent.putExtra("Kart Adı",cardname);
        startActivity(intent);
    }
}
