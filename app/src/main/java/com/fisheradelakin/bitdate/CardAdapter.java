package com.fisheradelakin.bitdate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Fisher on 8/3/15.
 */
public class CardAdapter extends ArrayAdapter<User> {

    CardAdapter(Context context, List<User> users) {
        super(context, R.layout.card, R.id.name, users);
    }

    @Override
    public CardView getView(int position, View convertView, ViewGroup parent) {
        CardView cardView = (CardView) super.getView(position, convertView, parent);

        TextView userNameView = (TextView) cardView.findViewById(R.id.name);
        userNameView.setText(getItem(position).getFirstName());

        return cardView;
    }
}
