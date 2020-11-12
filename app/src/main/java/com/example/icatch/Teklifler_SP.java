package com.example.icatch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
public class Teklifler_SP extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    public SliderAdap_SP sliderAdapter;
    private Button mNextBtn;
    private Button mBackBtn;
    private int myCurrentPage;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    int flag = 0;
    String currentuseremail;
    String _CardName;
    String _CardNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teklifler);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNextBtn=(Button)findViewById(R.id.nextBtn);
        mBackBtn=(Button)findViewById(R.id.prevBtn);
        sliderAdapter =new SliderAdap_SP(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        flag = 0;

        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(myCurrentPage+1);
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(myCurrentPage-1);
            }
        });
    }
    public void addDotsIndicator(int position){
        mDots=new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++)
        {
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.cardview_light_background));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));

        }
    }

    public void getdataFromFirebase(String _cardname)
    {

        CollectionReference collectionReference = firebaseFirestore.collection("Kart Verileri");
        collectionReference.whereEqualTo("Kullanıcı Mail Adresi",currentuseremail).whereEqualTo("Kart Adı",_cardname).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {

                    for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                        flag = 1;
                        _CardNumber = d.get("Kart Numarası").toString();
                        Intent CardDetailsActivity = new Intent(Teklifler_SP.this, Kart_Bilgileri_SP.class);
                        CardDetailsActivity.putExtra("Kart Adı", _CardName);
                        CardDetailsActivity.putExtra("Kart Numarası", _CardNumber);
                        startActivity(CardDetailsActivity);
                    }
                    if(flag == 0)
                    {
                        Toast.makeText(Teklifler_SP.this,"Bu İsimli Karta: "+ _CardName.toUpperCase() +" Sahip Değilsiniz",Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    public void TeklifiKullan(View view)
    {
        _CardName = sliderAdapter.slide_headings[myCurrentPage].toLowerCase();
        getdataFromFirebase(_CardName);
    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            myCurrentPage=position;
            if(position==0)
            {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("İleri");
                mBackBtn.setText("");
            }
            else if(position==mDots.length-1)
            {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Bitir");
                mBackBtn.setText("Geri");
            }
            else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("İleri");
                mBackBtn.setText("Geri");

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void bitir(View view) {
        if (mNextBtn.getText().toString()=="Bitir"){
            Intent OfferActivity = new Intent(Teklifler_SP.this,AnaHome_SP.class);
            startActivity(OfferActivity);
        }
    }
}
