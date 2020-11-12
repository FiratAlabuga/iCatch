package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Hesap_SP extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    String currentuseremail;
    TextView useremailtw;
    ImageView useraccountimage;
    Button tara;
    public FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hesap);
        useremailtw = (TextView)findViewById(R.id.useremail);
        useraccountimage = (ImageView) findViewById(R.id.UserAvatarImage);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        useremailtw.setText(currentuseremail);
        useraccountimage.setImageResource(R.drawable.user);
        tara = (Button) findViewById(R.id.button18);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setSelectedItemId(R.id.navigation_account);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent AccountPageActivity = new Intent(Hesap_SP.this,AnaHome_SP.class);
                        startActivity(AccountPageActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(Hesap_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Hesap_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:


                        break;
                }
                return false;
            }
        });
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F); //AlphaAnimation can be used as a fader. In this case, it will be used to fade a button when clicked.

        tara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick); // When the Scan button is pressed, the AlphaAnimation runs simulating button click effect by fading
                Intent taras = new Intent(getApplicationContext(), Tara_Barkod_Con_SP.class);
                startActivity(taras);
            }
        });
    }
    public void ProfDuzenle(View view)
    {
        Intent a = new Intent(Hesap_SP.this,Profil_Duzenle_SP.class);
        startActivity(a);
    }
    public void VeriKaldir(View view)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Hesap_SP.this);
        builder1.setMessage("Kart Silinecek Emin Misiniz ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Evet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FindDocumentIdAndDelete("userData");
                        FindDocumentIdAndDelete("Kart Verileri");
                        FindDocumentIdAndDelete("Alışveriş Verileri");
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Hayır",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public void DeleteDocument(String DocumentId,String ColletionPath)
    {
        DocumentReference UserDataRef = firebaseFirestore.collection(ColletionPath).document(DocumentId);

        UserDataRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Hesap_SP.this,"Silindi",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Hesap_SP.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void FindDocumentIdAndDelete(final String ColletionPath)
    {
        CollectionReference collectionReference = firebaseFirestore.collection(ColletionPath);
        collectionReference.whereEqualTo("userEmail",currentuseremail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {
                        DeleteDocument(d.getId().toString(),ColletionPath);
                    }
                }
            }
        });
    }
    public void HesabiSil(View view)
    {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(Hesap_SP.this);
        builder1.setMessage("Hesap Verilerini Tümü Silinecek Emin Misiniz?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Evet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FindDocumentIdAndDelete("userData");
                        FindDocumentIdAndDelete("Kart Verileri");
                        FindDocumentIdAndDelete("Alışveriş Verileri");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent OfferActivity = new Intent(Hesap_SP.this,GirisLogin_SP.class);
                                            startActivity(OfferActivity);
                                            finish();
                                        }
                                    }
                                });
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Hayır",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void destek(View view) {
        Intent chat = new Intent(Hesap_SP.this,Chatbot_SP.class);
        startActivity(chat);
    }

}
