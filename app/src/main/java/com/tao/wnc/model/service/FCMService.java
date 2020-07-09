package com.tao.wnc.model.service;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FCMService extends FirebaseMessagingService {

    private final static String TAG = FCMService.class.getName();
//    @Override
//    public void onNewToken(@NonNull String s) {
//        super.onNewToken(s);
//        Log.d(TAG, "new token - "+s);
//        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token", s);
//        editor.commit();
//    }

}
