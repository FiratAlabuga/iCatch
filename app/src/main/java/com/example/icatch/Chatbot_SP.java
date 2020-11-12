package com.example.icatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.applozic.mobicommons.commons.core.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.kommunicate.KmChatBuilder;
import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;
public class Chatbot_SP extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chatbot);

        ImageButton ulas = (ImageButton) findViewById(R.id.imageButton);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

        Kommunicate.init(this, "3490a0ac9cafb20c54327f4db8955a4c"); // APP KEY Required for chatbot. Can be extracted from Kommunicate website. afyer

        List<String> agentList = new ArrayList();
        agentList.add("J.Fırat Alabuga");

        List<String> botList = new ArrayList();
        botList.add("icatcher-knf1p");


        Kommunicate.openConversation(Chatbot_SP.this, null, new KmCallback() {
            @Override
            public void onSuccess(Object message) {
                Utils.printLog(Chatbot_SP.this, "ChatTest", "Launch Success : " + message);
            }

            @Override
            public void onFailure(Object error) {
                Utils.printLog(Chatbot_SP.this, "ChatTest", "Launch Failure : " + error);
            }
        });


        ulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/j-f%C4%B1rat-alabuga-3591961a4/")));

            }
        });
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent anasayfa = new Intent(Chatbot_SP.this,AnaHome_SP.class);
                        startActivity(anasayfa);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(Chatbot_SP.this,Kart_Secilen_Liste_SP.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(Chatbot_SP.this,Teklifler_SP.class);
                        startActivity(OfferActivity);
                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(Chatbot_SP.this,Hesap_SP.class);
                        startActivity(AccountPageActivity);
                        break;
                }
                return false;
            }
        });

    }
}
