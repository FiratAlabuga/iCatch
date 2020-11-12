package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
public class Kart_Duzenle_SP extends AppCompatActivity {
    EditText cardnumber;
    EditText carddescription;
    String CardDescription;
    String CardNumber;
    String CardName;
    String currentuseremail;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String DocumentId;
    ImageView cardlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_kart_duzenle);
        cardnumber = (EditText)findViewById(R.id.cardnumber);
        carddescription = (EditText)findViewById(R.id.carddescription);
        cardlogo = (ImageView)findViewById(R.id.cardImageView2);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail().toString();
        CardName = getIntent().getStringExtra("Kart Adı");
        CardNumber = getIntent().getStringExtra("Kart Numarası");
        DocumentId = getIntent().getStringExtra("DocumentId");
        CardDescription = getIntent().getStringExtra("Kart Açıklaması");
        carddescription.setText(CardDescription);
        cardnumber.setText(CardNumber);
        GetUrl();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(Kart_Duzenle_SP.this,AnaHome_SP.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(Kart_Duzenle_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Kart_Duzenle_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(Kart_Duzenle_SP.this,Hesap_SP.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });


    }
    public void Update(View view)
    {
        DocumentReference UserDataRef = firebaseFirestore.collection("Kart Verileri").document(DocumentId);
        if(cardnumber.getText().toString() != "") {
            UserDataRef.update("Kart Numarası", cardnumber.getText().toString(), "Kart Açıklamaları", carddescription.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Kart_Duzenle_SP.this, "Başarılı", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Kart_Duzenle_SP.this, Kart_Bilgileri_SP.class);
                        intent.putExtra("Kart Adı", CardName);
                        intent.putExtra("Kart Numarası", cardnumber.getText().toString());
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(Kart_Duzenle_SP.this, task.getException().toString(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public void GetUrl()
    {
        StorageReference urlreferance = FirebaseStorage.getInstance().getReference("logo/"+CardName.toLowerCase()+".png");
        urlreferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String dowlandurl = uri.toString();
                Picasso.get().load(dowlandurl).into(cardlogo);
            }
        });
    }
}
