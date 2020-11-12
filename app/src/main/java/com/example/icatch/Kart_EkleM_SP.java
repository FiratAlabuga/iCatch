package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
public class Kart_EkleM_SP extends AppCompatActivity{
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    EditText cardnumberText,descriptionText;
    ImageView cardimage;
    String cardName;
    String cardNumber;
    String currentuseremail;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_man_kart_ekle);
        cardnumberText = findViewById(R.id.cardnumber);
        descriptionText = findViewById(R.id.carddescription);
        cardimage = findViewById(R.id.CardImageView);
        cardName = getIntent().getStringExtra("Kart Adı").toLowerCase();
        cardNumber = getIntent().getStringExtra("Kart Numarası");
        if(cardNumber != null && cardNumber != "")
        {
            cardnumberText.setText(cardNumber);
        }
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        GetUrl();
        setFlag(0);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(Kart_EkleM_SP.this,AnaHome_SP.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(Kart_EkleM_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Kart_EkleM_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(Kart_EkleM_SP.this,Hesap_SP.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });
    }

    public void GetUrl()
    {
        StorageReference urlreferance = FirebaseStorage.getInstance().getReference("logo/"+cardName.toLowerCase()+".png");
        urlreferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String dowlandurl = uri.toString();
                Picasso.get().load(dowlandurl).into(cardimage);
            }
        });
    }

    public void getdataFromFirebase()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("Kart Verileri");
        collectionReference.whereEqualTo("Kullanıcı Mail Adresi",currentuseremail).whereEqualTo("Kart Adı",cardName.toLowerCase()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {
                    Toast.makeText(Kart_EkleM_SP.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if(queryDocumentSnapshots != null)
                {
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {
                        setFlag(1);
                        break;
                    }
                    if(flag == 0)
                    {
                        if(cardnumberText.getText().toString() != null && cardnumberText.getText().toString() != "" )
                        {
                            HashMap<String,Object> _CardData = new HashMap<>();
                            firebaseFirestore = FirebaseFirestore.getInstance();
                            String currentuseremail = firebaseAuth.getCurrentUser().getEmail();
                            _CardData.put("Kullanıcı Mail Adresi",currentuseremail);
                            _CardData.put("Kart Adı",cardName.toLowerCase());
                            _CardData.put("Kart Numarası",cardnumberText.getText().toString());
                            _CardData.put("Kart Açıklamaı",descriptionText.getText().toString());
                            firebaseFirestore.collection("Kart Verileri").add(_CardData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Kart_EkleM_SP.this, "Kart Eklendi",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Kart_EkleM_SP.this,AnaHome_SP.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Kart_EkleM_SP.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    else
                    {

                        Toast.makeText(Kart_EkleM_SP.this,"Bu Kart Sistemde Mevcut !",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void KartEkle(View view)
    {
        checkandaddcard();

    }
    public int checkandaddcard()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("Kart Verileri");
        collectionReference.whereEqualTo("Kullanıcı Mail Adresi",currentuseremail).whereEqualTo("Kart Adı",cardName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        setFlag(1);
                        break;
                    }
                    if(flag == 0)
                    {
                        if(cardnumberText.getText().toString() != null && cardnumberText.getText().toString() != "" )
                        {
                            HashMap<String,Object> _CardData = new HashMap<>();
                            firebaseFirestore = FirebaseFirestore.getInstance();
                            String currentuseremail = firebaseAuth.getCurrentUser().getEmail();
                            _CardData.put("Kullanıcı Mail Adresi",currentuseremail);
                            _CardData.put("Kart Adı",cardName.toLowerCase());
                            _CardData.put("Kart Numarası",cardnumberText.getText().toString());
                            _CardData.put("Kart Açıklaması",descriptionText.getText().toString());
                            firebaseFirestore.collection("Kart Verileri").add(_CardData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(Kart_EkleM_SP.this,AnaHome_SP.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Kart_EkleM_SP.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    else
                    {

                        Toast.makeText(Kart_EkleM_SP.this,"Bu Kart Sistemde Mevcut !",Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Kart_EkleM_SP.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return flag;
    }
    public void iptalEt(View view)
    {
        Intent intent = new Intent(Kart_EkleM_SP.this,AnaHome_SP.class);
        finish();
        startActivity(intent);
    }
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
