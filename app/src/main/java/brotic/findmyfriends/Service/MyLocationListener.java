package brotic.findmyfriends.Service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;
import brotic.findmyfriends.Security.MyActivity;

/**
 * Created by brice on 27/12/15.
 */
public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            LocationUtils utils = new LocationUtils();
            String token = "";
            User user = null;

            try {
                token = MyActivity.getSecurity().getSid();
                user = MyActivity.getSecurity().getUtilisateur();
            } catch (SecurityContextException e) {
                e.printStackTrace();
                return;
            }

            BroticCommunication com = new BroticCommunication("position");
            if (user != null) {
                com.addParamGet("id", String.valueOf(user.getId()));
                com.addParamGet("token", token);
                com.addParamGet("position", utils.convertFromLocation(location));

              /*  try {
                    com.run();
                    Log.d("==== ALERT", com.getJson().toString());
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    e.printStackTrace();
                }*/

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
