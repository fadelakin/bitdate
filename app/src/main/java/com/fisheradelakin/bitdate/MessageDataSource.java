package com.fisheradelakin.bitdate;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Fisher on 8/10/15.
 */
public class MessageDataSource {

    private static final Firebase sRef = new Firebase("https://fishdate.firebaseio.com/messages");

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG = "MessageDataSource";

    public static void saveMessage(Message message, String conversationId) {

        Date date = message.getDate();

        String key = sDateFormat.format(date);

        HashMap<String, String> msg = new HashMap<>();
        msg.put("text", message.getText());
        msg.put("sender", message.getSender());
        sRef.child(conversationId).child(key).setValue(msg);
    }

    public static MessagesListener addMessageListener(String convoId,  MessagesCallbacks messagesCallbacks) {
        MessagesListener listener = new MessagesListener(messagesCallbacks);
        sRef.child(convoId).addChildEventListener(listener);
        return listener;
    }

    public static void stop(MessagesListener listener) {
        sRef.removeEventListener(listener);
    }

    public static class MessagesListener implements ChildEventListener {

        private MessagesCallbacks messagesCallbacks;

        MessagesListener(MessagesCallbacks callbacks) {
            messagesCallbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String, String> msg = (HashMap) dataSnapshot.getValue();
            Message message = new Message();
            message.setSender(msg.get("sender"));
            message.setText(msg.get("text"));
            try {
                message.setDate(sDateFormat.parse(dataSnapshot.getKey()));
            } catch (ParseException e) {
                Log.d(TAG, "Something went wrong");
                e.printStackTrace();
            }

            if(messagesCallbacks != null) {
                messagesCallbacks.onMessageAdded(message);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    public interface MessagesCallbacks {
        void onMessageAdded(Message message);
    }
}
