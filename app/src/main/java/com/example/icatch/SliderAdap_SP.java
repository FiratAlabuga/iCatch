package com.example.icatch;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
public class SliderAdap_SP extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdap_SP(Context context) {

        this.context = context;
    }

    //Arrays
    public int[] slide_images = {

            R.drawable.kampanya1,
            R.drawable.kampanya2,
            R.drawable.kampanya3
    };

    public String[] slide_headings = {

            "D&R",
            "MIGROS",
            "OPET"
    };

    public String[] slide_descs = {

            "D&R Kartını Kullanarak Kampanyadan Yararlan ",
            "MIGROS Kartını Kullanarak Kampanyadan Yararlan",
            "OPET Kartını Kullanarak Kampanyadan Yararlan"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.act_slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);
    }
}
