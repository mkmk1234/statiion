package com.kun.station;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kun on 16/6/23.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent1 = new Intent(context, FirstActivity.class);
        myIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent1);
    }

}
