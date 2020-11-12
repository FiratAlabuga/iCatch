package com.example.icatch;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnaHome_SP extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    GridView cardslist;
    int gridviewheight;
    int getGridviewwidth;
    String currentuseremail;
    ArrayList<GridVEleman_SP> array_characters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        cardslist = (GridView)findViewById(R.id.cardlist);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        array_characters = new ArrayList<>();
        getdataFromFirebase();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(AnaHome_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(AnaHome_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);
                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(AnaHome_SP.this,Hesap_SP.class);
                        startActivity(AccountPageActivity);
                        break;
                }
                return false;
            }
        });
    }


    public void addcard(View view)
    {
        Intent intent = new Intent(AnaHome_SP.this,Kart_Secilen_Liste_SP.class);
        startActivity(intent);
    }

    public void getdataFromFirebase()
    {
        array_characters.clear();
        CollectionReference collectionReference = firebaseFirestore.collection("Kart Verileri");
        collectionReference.whereEqualTo("Kullanıcı Mail Adresi",currentuseremail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {

                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {



                        switch (d.get("Kart Adı").toString()) {

                            case "teknosa":
                                array_characters.add(new GridVEleman_SP(d.get("Kart Adı").toString(),d.get("Kart Numarası").toString(), Color.rgb(255, 165, 0), Color.WHITE, 525, 525));
                                break;
                            case "migros":
                                array_characters.add(new GridVEleman_SP(d.get("Kart Adı").toString(),d.get("Kart Numarası").toString(), Color.rgb(255, 140, 0), Color.WHITE, 525, 525));
                                break;
                            case "shell":
                                array_characters.add(new GridVEleman_SP(d.get("Kart Adı").toString(),d.get("Kart Numarası").toString(), Color.RED, Color.WHITE, 525, 525));
                                break;
                            case "opet":
                                array_characters.add(new GridVEleman_SP(d.get("Kart Adı").toString(),d.get("Kart Numarası").toString(), Color.rgb(0, 0, 139), Color.WHITE, 525, 525));
                                break;
                            case "mavi":
                                array_characters.add(new GridVEleman_SP(d.get("Kart Adı").toString(),d.get("Kart Numarası").toString(), Color.BLUE, Color.WHITE, 525, 525));
                                break;
                            case "gratis":
                                array_characters.add(new GridVEleman_SP(d.get("Kart Adı").toString(),d.get("Kart Numarası").toString(), Color.rgb(204, 204, 0), Color.WHITE, 525, 525));
                                break;
                        }


                    }
                    GridVAdap_SP adapter = new GridVAdap_SP(AnaHome_SP.this,array_characters);
                    cardslist.setAdapter((ListAdapter) adapter);
                }
            }
        });




    }
}
