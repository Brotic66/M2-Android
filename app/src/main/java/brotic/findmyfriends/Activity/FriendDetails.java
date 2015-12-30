package brotic.findmyfriends.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

public class FriendDetails extends FragmentActivity implements OnMapReadyCallback {

    private int friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_friend_details);

        Intent it = this.getIntent();
        this.friendId = it.getIntExtra("friendId", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (this.friendId != 0) {
            BroticCommunication com = new BroticCommunication("getPosition");

            try {
                com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
                com.addParamGet("token", MyActivity.getSecurity().getSid());
                com.addParamGet("friendId", String.valueOf(this.friendId));
                com.addArg("map", map);
            } catch (SecurityContextException e) {
                e.printStackTrace();
            }
        }

        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
