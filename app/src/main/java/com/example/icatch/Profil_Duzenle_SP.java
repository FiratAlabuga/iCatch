package com.example.icatch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class Profil_Duzenle_SP extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    FirebaseUser currentuseremail;
    EditText nameText,surnameText,birthdateText,phoneText;
    Spinner spinner;
    String gender;
    String DocumentId;
    DatabaseReference myRef;
    private DatePickerDialog.OnDateSetListener mDatePicker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_prof_duzenle);
        spinner = findViewById(R.id.spinner);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        myRef = database.getReference("Kullanicilar");
        currentuseremail = firebaseAuth.getCurrentUser();
        nameText = findViewById(R.id.editTextName);
        surnameText = findViewById(R.id.editTextSurname);
        birthdateText = findViewById(R.id.editTextBirthDate);
        phoneText = findViewById(R.id.editTextPhone);

        birthdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Profil_Duzenle_SP.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatePicker,
                        year,month,day);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });




        mDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String date = month + "." + day + "." + year;
                birthdateText.setText(date);
            }
        };

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setSelectedItemId(R.id.navigation_account);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(Profil_Duzenle_SP.this,AnaHome_SP.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent Kart_Secilen_Liste_SP = new Intent(Profil_Duzenle_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(Kart_Secilen_Liste_SP);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Profil_Duzenle_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent Hesap_SP = new Intent(Profil_Duzenle_SP.this,Hesap_SP.class);
                        startActivity(Hesap_SP);

                        break;
                }
                return false;
            }
        });
    }


    public void Kaydet(View view) {

        String ad = nameText.getText().toString();
        String soyad = surnameText.getText().toString();
        String dtarih = birthdateText.getText().toString();
        String telefon = phoneText.getText().toString();
        if(ad.matches("")){
            // if Name field is empty
            Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(soyad.matches("")){
            Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if(dtarih.matches("")){
            Toast.makeText(getApplicationContext(), "Please enter birth date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(telefon.matches("")){
            Toast.makeText(getApplicationContext(), "Please enter birth date", Toast.LENGTH_SHORT).show();
            return;
        }
        myRef.child(currentuseremail.getUid()).child("Ad").setValue(String.valueOf(ad));
        myRef.child(currentuseremail.getUid()).child("Soyad").setValue(String.valueOf(soyad));
        myRef.child(currentuseremail.getUid()).child("DoğumTarihi").setValue(String.valueOf(dtarih));
        myRef.child(currentuseremail.getUid()).child("TelefonNumarası").setValue(String.valueOf(telefon)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Successfully written to the database
                    finish();
                    Toast.makeText(getApplicationContext(),"Profil Güncellendi ", Toast.LENGTH_SHORT).show();

                    Intent homepage = new Intent(getApplicationContext(), Hesap_SP.class); //Redirect to Profile if successful scan
                    startActivity(homepage);
                } else {
                    Toast.makeText(getApplicationContext(),"Profil Güncellenemedi ", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }
}
