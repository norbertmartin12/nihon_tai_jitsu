package org.ntj_workout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver {

    private final Callback callback;

    public NetworkReceiver(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : conn.getAllNetworks()) {
            if (conn.getNetworkInfo(network).getState().equals(NetworkInfo.State.CONNECTED)) {
                this.callback.connectivityChanged(true);
                return;
            }
        }
        this.callback.connectivityChanged(false);
    }

    public interface Callback {
        void connectivityChanged(boolean hasConnectivity);
    }
}