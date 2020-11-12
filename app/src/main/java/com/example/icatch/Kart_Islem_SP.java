package com.example.icatch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Kart_Islem_SP extends AppCompatActivity {
    ListView CardTransaction;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String currentuseremail;
    String CardName;
    String CardNumber;
    ImageView cardlogo;
    ArrayList<Kart_Islem_Eleman_SP> transactionlist = new ArrayList<Kart_Islem_Eleman_SP>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_kart_islemleri);
        CardTransaction = (ListView)findViewById(R.id.transaction_view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        CardName = getIntent().getStringExtra("Kart Adı");
        CardNumber = getIntent().getStringExtra("Kart Numarası");
        cardlogo = (ImageView)findViewById(R.id.cardLogo);
        GetUrl();
        getdataFromFirebase();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(Kart_Islem_SP.this,AnaHome_SP.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(Kart_Islem_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Kart_Islem_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(Kart_Islem_SP.this,Hesap_SP.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });
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


    public void getdataFromFirebase()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("Alışveriş Verileri");
        collectionReference.whereEqualTo("Kullanıcı Mail Adresi",currentuseremail).whereEqualTo("Kart Adı",CardName).whereEqualTo("Kart Numarası",CardNumber).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {
                    transactionlist.add(new Kart_Islem_Eleman_SP("Tarih","Tutar"));
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {

                        transactionlist.add(new Kart_Islem_Eleman_SP(d.get("Tarih").toString(),d.get("Tutar").toString()));

                    }

                    Kart_Islem_Adap_SP adapter = new Kart_Islem_Adap_SP(Kart_Islem_SP.this,transactionlist);
                    CardTransaction.setAdapter(adapter);
                }
            }
        });
    }
}
