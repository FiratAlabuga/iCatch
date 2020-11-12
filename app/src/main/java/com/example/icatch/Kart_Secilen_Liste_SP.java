package com.example.icatch;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
public class Kart_Secilen_Liste_SP extends AppCompatActivity{
    ListView lsttop;
    ListView lstall;
    ListVAdap_SP adapter_top;
    ListVAdap_SP adapter_all;
    EditText SearchFilter;
    ArrayList<ListVEleman_SP> allcards = new ArrayList<ListVEleman_SP>();
    ArrayList<ListVEleman_SP> topcards = new ArrayList<ListVEleman_SP>();
    String[] topname = {"Migros","Teknosa"};
    String[] allname = {"Migros","Teknosa","Mavi","Gratis","Opet","Shell"};
    int[] topimages = {R.drawable.migros,R.drawable.teknosa};
    int[] allimages = {R.drawable.migros,R.drawable.teknosa,R.drawable.mavi,R.drawable.gratis,R.drawable.opet,R.drawable.shell};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_secilen_kart_list);
        lsttop = (ListView) findViewById(R.id.toplistview);
        lstall = (ListView) findViewById(R.id.alllistview);
        SearchFilter = (EditText)findViewById(R.id.editText);
        FillListView();
        adapter_top = new ListVAdap_SP(this,topcards);
        adapter_all = new ListVAdap_SP(this,allcards);
        lstall.setAdapter(adapter_all);
        lsttop.setAdapter(adapter_top);
        SearchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Kart_Secilen_Liste_SP.this).adapter_all.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lsttop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ChoosenElement = topname[position].toString();
                Intent intent = new Intent(Kart_Secilen_Liste_SP.this,Tara_Barkod_SP.class);
                intent.putExtra("Kart Adı",ChoosenElement);
                startActivity(intent);

            }
        });
        lstall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListVEleman_SP ChoosenElement = (ListVEleman_SP) adapter_all.getItem(position);
                Intent intent = new Intent(Kart_Secilen_Liste_SP.this,Tara_Barkod_SP.class);
                intent.putExtra("Kart Adı",ChoosenElement.getName().toLowerCase());
                startActivity(intent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setSelectedItemId(R.id.navigation_add_card);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent AccountPageActivity = new Intent(Kart_Secilen_Liste_SP.this,AnaHome_SP.class);
                        startActivity(AccountPageActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(Kart_Secilen_Liste_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Kart_Secilen_Liste_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent profil = new Intent(Kart_Secilen_Liste_SP.this,Hesap_SP.class);
                        startActivity(profil);

                        break;
                }
                return false;
            }
        });


    }
    public void FillListView()
    {
        for(int i = 0; i < allname.length;i++)
        {
            allcards.add(new ListVEleman_SP(allname[i],allimages[i]));
        }
        for(int i = 0; i < topname.length;i++)
        {
            topcards.add(new ListVEleman_SP(topname[i],topimages[i]));
        }

    }
}
