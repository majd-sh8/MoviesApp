package application.example.com.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import application.example.com.moviesapp.R;
import application.example.com.moviesapp.activites.ImgBack_Activity;
import application.example.com.moviesapp.activites.ImgPoster_Activity;
import application.example.com.moviesapp.models.Slide;

public class SlidePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Slide> mList;


    public SlidePagerAdapter(Context context, List<Slide> listSlides) {
        this.mContext = context;
        this.mList = listSlides;
    }

    public SlidePagerAdapter(Context context, List<Slide> listSlides, int m) {
        this.mContext = context;
        this.mList = listSlides;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_item, null);

        ImageView image_slid = view.findViewById(R.id.image_slid);
        TextView text_title = view.findViewById(R.id.text_title);
        image_slid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImgBack_Activity.class);
                intent.putExtra("imgBack", mList.get(position).getImage());
                mContext.startActivity(intent);
            }
        });

        Picasso.get()
                .load(mList.get(position).getImage()).placeholder(R.drawable.ic_image_black_24dp)
                .into(image_slid);
        text_title.setText(mList.get(position).getTitle());

        container.addView(view);
        return view;


    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}