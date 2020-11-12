package com.example.icatch;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FilterReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class ListVAdap_SP extends BaseAdapter implements Filterable {
    LayoutInflater layoutInflater;
    ArrayList<ListVEleman_SP> orginiallist;
    ArrayList<ListVEleman_SP> templist;
    Context c;
    CustomFilter cs;

    public ListVAdap_SP(Context c, ArrayList<ListVEleman_SP> orginiallist) {
        this.c = c;
        this.orginiallist = orginiallist;
        this.templist = orginiallist;

    }

    @Override
    public int getCount() {
        return orginiallist.size();
    }

    @Override
    public Object getItem(int position) {
        return orginiallist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View elementView;
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        elementView = layoutInflater.inflate(R.layout.act_kart_eleman, null);
        ImageView iv = (ImageView) elementView.findViewById(R.id.CardImageView);
        TextView tv = (TextView) elementView.findViewById(R.id.CardNameTextView);


        tv.setText(orginiallist.get(position).getName());
        iv.setImageResource(orginiallist.get(position).getImage());

        return elementView;
    }

    @Override
    public Filter getFilter() {
        if(cs == null)
        {
            cs = new CustomFilter();
        }
        return cs;
    }
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint != null && constraint.length()> 0) {
                constraint = constraint.toString().toUpperCase();

                ArrayList<ListVEleman_SP> filters = new ArrayList<>();
                for (int i = 0; i < templist.size(); i++) {
                    if (templist.get(i).getName().toUpperCase().contains(constraint)) {
                        ListVEleman_SP listViewElement = new ListVEleman_SP(templist.get(i).getName(), templist.get(i).getImage());
                        filters.add(listViewElement);
                    }
                }
                filterResults.count = filters.size();
                filterResults.values = filters;
            }
            else
            {
                filterResults.count = templist.size();
                filterResults.values = templist;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            orginiallist = (ArrayList<ListVEleman_SP>)results.values;
            notifyDataSetChanged();
        }
    }
}
