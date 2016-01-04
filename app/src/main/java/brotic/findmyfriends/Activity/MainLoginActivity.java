package brotic.findmyfriends.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import brotic.findmyfriends.Event.MainClickEvent;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.DataSaveForLocation;
import brotic.findmyfriends.Service.MyLocationListener;
import brotic.findmyfriends.Service.PositionService;
import brotic.findmyfriends.Service.ShakeEventManager;

public class MainLoginActivity extends MyActivity implements ShakeEventManager.ShakeListener {

    private boolean create = true;
    private ShakeEventManager sm;
    private boolean isShaked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vide);

        MainAfterLoginPresenter.main();

       /* Intent intent = new Intent(this, PositionService.class);
        startService(intent);*/

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
        stopService(new Intent(this, PositionService.class));
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
            Toast.makeText(this, getString(R.string.locationSend), Toast.LENGTH_SHORT).show();

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener listener = new MyLocationListener();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);

            locationManager.removeUpdates(listener);
            isShaked = false;
        }
    }
}