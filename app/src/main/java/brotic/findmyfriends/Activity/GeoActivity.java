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
import brotic.findmyfriends.Service.MyLocationListener;
import brotic.findmyfriends.Service.PositionService;
import brotic.findmyfriends.Service.ShakeEventManager;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 19/01/2016
 */
public class GeoActivity extends MyActivity {

    private boolean create = true;
    private ShakeEventManager sm;
    private boolean isShaked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        myToolbar.inflateMenu(R.menu.menu_login);
        myToolbar.setOnMenuItemClickListener(new MenuClickListener());

        MainAfterLoginPresenter.geo();
    }
}