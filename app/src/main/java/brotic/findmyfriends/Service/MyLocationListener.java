package brotic.findmyfriends.Service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.AsyncTask.SendPositionTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;
import brotic.findmyfriends.Security.MyActivity;

/**
 * Created by brice on 27/12/15.
 */
public class MyLocationListener implements LocationListener {

    private static User user = null;
    private static String token = null;

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            LocationUtils utils = new LocationUtils();

            if (token == null && user == null) {
                try {

                    /**
                     * ==========
                     * Ici penser à effectuer un enregistrement et lecture dans SQLite !!!!
                     * ==========
                     */

                    Log.d("==== ALERT", "coucou ma gueule");
                    token = MyActivity.getSecurity().getSid();
                    user = MyActivity.getSecurity().getUtilisateur();
                } catch (SecurityContextException e) {
                    e.printStackTrace();
                    return;
                }

            }

            BroticCommunication com = new BroticCommunication("position");
            SendPositionTask task = new SendPositionTask();

            if (user != null) {
                com.addParamGet("id", String.valueOf(user.getId()));
                com.addParamGet("token", token);
                com.addParamGet("latitude", utils.getLatitude(location));
                com.addParamGet("longitude", utils.getLongitude(location));

                task.execute(com);

                Log.d("==== ALERT", com.getParamsGet().get("position"));
            }
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
