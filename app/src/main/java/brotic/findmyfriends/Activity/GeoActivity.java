package brotic.findmyfriends.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import brotic.findmyfriends.AsyncTask.FriendDetailsTask;
import brotic.findmyfriends.AsyncTask.GetMyPositionTask;
import brotic.findmyfriends.Event.MenuClickListener;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.ActivityLauncher;
import brotic.findmyfriends.Service.BroticCommunication;
import brotic.findmyfriends.Service.MyLocationListener;
import brotic.findmyfriends.Service.PositionService;
import brotic.findmyfriends.Service.ShakeEventManager;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 19/01/2016
 */
public class GeoActivity extends FragmentActivity implements OnMapReadyCallback {

    private boolean create = true;
    private ShakeEventManager sm;
    private boolean isShaked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        myToolbar.inflateMenu(R.menu.menu_login);
        myToolbar.setOnMenuItemClickListener(new MenuClickListener());

        MainAfterLoginPresenter.geo(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        BroticCommunication com = new BroticCommunication("getPosition");

        try {
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("friendId", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addArg("map", map);

            GetMyPositionTask task = new GetMyPositionTask();
            task.execute(com);

            MyActivity.getAct().addTask(task);
        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }
}