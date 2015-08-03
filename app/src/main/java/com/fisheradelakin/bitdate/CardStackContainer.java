package com.fisheradelakin.bitdate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Fisher on 8/3/15.
 */
public class CardStackContainer extends RelativeLayout {

    private CardAdapter mCardAdapter;

    public CardStackContainer(Context context) {
        this(context, null, 0);
    }

    public CardStackContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardStackContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCardAdapter(CardAdapter cardAdapter) {
        mCardAdapter = cardAdapter;

        if(mCardAdapter.getCount() > 0) {
            CardView cardView = mCardAdapter.getView(0, null, this);
            addView(cardView);
        }
    }
}
