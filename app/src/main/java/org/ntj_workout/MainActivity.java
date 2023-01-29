package org.ntj_workout;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NetworkReceiver.Callback {

    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentContainerView navHostFragment = findViewById(R.id.nav_host_fragment);
        toolbar.setNavigationOnClickListener(view -> {
            Snackbar snackbar = Snackbar.make(view, R.string.back_home_sentence, Snackbar.LENGTH_LONG)
                    .setAction(R.string.i_restart, onClick -> NavHostFragment.findNavController(navHostFragment.getFragment()).navigate(R.id.home));
            snackbar.getView().setBackgroundColor(Color.YELLOW);
            snackbar.show();
        });
        this.networkReceiver = new NetworkReceiver(this);
        registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (id == R.id.action_about) {
            Snackbar snackbar = Snackbar.make(toolbar, this.getString(R.string.action_about_copyright, BuildConfig.VERSION_NAME), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.YELLOW);
            snackbar.setTextMaxLines(3);
            snackbar.show();
            return true;
        } else if (id == R.id.action_about_data) {
            Snackbar snackbar = Snackbar.make(toolbar,  R.string.action_about_data_warn, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.YELLOW);
            snackbar.setTextMaxLines(3);
            snackbar.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void connectivityChanged(boolean hasConnectivity) {
        if (hasConnectivity) {
            findViewById(R.id.textView_offlineBanner).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView_offlineBanner).setVisibility(View.VISIBLE);
        }
    }

}