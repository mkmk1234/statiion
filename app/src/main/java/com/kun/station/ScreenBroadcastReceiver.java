package com.kun.station;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kun on 16/6/28.
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    Activity activity;

    public ScreenBroadcastReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Toast.makeText(context, "ACTION_SCREEN_ON", Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Toast.makeText(context, "ACTION_SCREEN_OFF", Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            Toast.makeText(context, "ACTION_USER_PRESENT", Toast.LENGTH_SHORT).show();
            Intent myIntent1 = new Intent(context, MainActivity.class);
            myIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(myIntent1);
        } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
            Toast.makeText(context, "ACTION_CLOSE_SYSTEM_DIALOGS", Toast.LENGTH_SHORT).show();
        }
//        Intent myIntent1 = new Intent(context, FirstActivity.class);
//        myIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(myIntent1);
    }

}
