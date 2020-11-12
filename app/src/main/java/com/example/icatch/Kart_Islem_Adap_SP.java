package com.example.icatch;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Kart_Islem_Adap_SP extends BaseAdapter {
    private Context context;
    private ArrayList<Kart_Islem_Eleman_SP> arrayList;
    Context c;


    public Kart_Islem_Adap_SP(Context context, ArrayList<Kart_Islem_Eleman_SP> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
        c = context;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View row;
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.act_kart_islem_liste,null);
        TextView date = (TextView)row.findViewById(R.id.cardtransactiondate);
        TextView amount = (TextView)row.findViewById(R.id.cardtransactionamount);
        Kart_Islem_Eleman_SP _CardTransactionlist = arrayList.get(position);

        date.setText(_CardTransactionlist.getDate());
        amount.setText(_CardTransactionlist.getAmount());
        return row;
    }

}
