package brotic.findmyfriends.Service;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.AsyncTask.SendPositionTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;
import brotic.findmyfriends.Security.MyActivity;

/**
 * Created by brice on 27/12/15.
 */
public class MyLocationListener implements LocationListener {

    private LocationManager manager;

    /**
     * @param locationManager
     */
    public MyLocationListener(LocationManager locationManager) {
        this.manager = locationManager;
    }

    @Override
    public void onLocationChanged(Location location) {

        LocationUtils utils = new LocationUtils();

        try {
            BroticCommunication com = new BroticCommunication("position");
            SendPositionTask task = new SendPositionTask();

            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("latitude", utils.getLatitude(location));
            com.addParamGet("longitude", utils.getLongitude(location));

            task.execute(com);

            manager.removeUpdates(this);
            } catch (SecurityContextException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
