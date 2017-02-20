package com.reservation.reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Home on 2/19/2017.
 */

public class BoxAdapter extends BaseAdapter {
    private final Context mContext;
    public final ImageView[] box;

    // 1
    public BoxAdapter(Context context, ImageButton[] box) {
        this.mContext = context;
        this.box = box;
    }

    // 2
    @Override
    public int getCount() {
        return box.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView im1 = box[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.boxpic, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_favorite);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);
        imageView.setImageResource(R.color.backbrate);
        return convertView;
    }
}

