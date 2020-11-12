package com.example.icatch;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class GridVAdap_SP extends BaseAdapter {
    private Context context;
    private ArrayList<GridVEleman_SP> arrayList;


    public GridVAdap_SP(Context context, ArrayList<GridVEleman_SP> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;

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
        final Button button;


        if(convertView == null)
        {

            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(arrayList.get(position).getWidth(),arrayList.get(position).getHeight()));
            button.setPadding(0,20,2,20);
            button.setText(arrayList.get(position).getName());
            button.setBackgroundColor(arrayList.get(position).getBgcolor());
            button.setTextColor(arrayList.get(position).txtcolor);
            final String _Name = arrayList.get(position).getName();
            final String _ID  =  arrayList.get(position).id;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Kart_Bilgileri_SP.class);
                    intent.putExtra("Kart Numarası",_ID);
                    intent.putExtra("Kart Adı",_Name);
                    context.startActivity(intent);

                }
            });
        }
        else
        {
            button = (Button)convertView;
        }
        return button;
    }
}
