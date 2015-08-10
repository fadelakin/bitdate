package com.fisheradelakin.bitdate;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Fisher on 8/10/15.
 */
public class MessageDataSource {
    private static final Firebase sRef = new Firebase("https://fishdate.firebaseio.com/messages");

    public static void saveMessage(Message message, String conversationId) {

        Date date = message.getDate();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddmmss");
        String key = df.format(date);

        HashMap<String, String> msg = new HashMap<>();
        msg.put("text", message.getText());
        msg.put("sender", message.getSender());
        sRef.child(conversationId).child(key).setValue(msg);
    }
}
