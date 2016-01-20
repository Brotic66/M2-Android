package brotic.findmyfriends.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import brotic.findmyfriends.Event.MenuClickListener;
import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.ActivityLauncher;
import brotic.findmyfriends.Service.GCM.RegistrationIntentService;
import brotic.findmyfriends.Service.MyLocationListener;
import brotic.findmyfriends.Service.ShakeEventManager;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 05/01/2016
 */
public class MainLoginActivity extends MyActivity implements ShakeEventManager.ShakeListener {

    private boolean create = true;
    private ShakeEventManager sm;
    private boolean isShaked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_vide);
        setContentView(R.layout.activity_main_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        myToolbar.inflateMenu(R.menu.menu_login);
        //myToolbar.setNavigationOnClickListener(new MenuClickListener());
        myToolbar.setOnMenuItemClickListener(new MenuClickListener());

        MainAfterLoginPresenter.main();

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

        isShaked = false;
        sm = new ShakeEventManager();
        sm.setListener(this);
        sm.init(this);

        this.create = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.register();

        if (!this.create)
            MainAfterLoginPresenter.main();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.deregister();
    }

    @Override
    public void onShake() {
        if (!isShaked)
        {
            isShaked = true;

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener listener = new MyLocationListener(locationManager);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(this.getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);

            Toast.makeText(this, getString(R.string.locationSend), Toast.LENGTH_SHORT).show();
            isShaked = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                ActivityLauncher.create("ConfigActivity", false, null);
                return true;
            case R.id.contact:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}