package com.fisheradelakin.bitdate;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChoosingFragment extends Fragment {

    private CardStackContainer mCardStack;

    public ChoosingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mCardStack = (CardStackContainer) v.findViewById(R.id.card_stack);
        User user = new User();
        user.setFirstName("Fisher");
        List<User> users = new ArrayList<>();
        users.add(user);
        CardAdapter cardAdapter = new CardAdapter(getActivity(), users);
        mCardStack.setCardAdapter(cardAdapter);

        ImageButton yesButton = (ImageButton) v.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStack.swipeRight();
            }
        });

        ImageButton noButton = (ImageButton) v.findViewById(R.id.no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStack.swipeLeft();
            }
        });

        return v;
    }
}
