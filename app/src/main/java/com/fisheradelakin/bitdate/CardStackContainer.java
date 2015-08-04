package com.fisheradelakin.bitdate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Fisher on 8/3/15.
 */
public class CardStackContainer extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "CardStackContainer";

    private float mLastTouchX;
    private float mLastTouchY;
    private float mPositionX;
    private float mPositionY;

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
            cardView.setOnTouchListener(this);
            addView(cardView);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();

                mPositionX = v.getX();
                mPositionY = v.getY();

                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float xMove = event.getX();
                float yMove = event.getY();

                float changeX = xMove - mLastTouchX;
                float changeY = yMove - mLastTouchY;

                mPositionX += changeX;
                mPositionY += changeY;

                v.setX(mPositionX);
                v.setY(mPositionY);

                break;
        }
        return true;
    }
}
