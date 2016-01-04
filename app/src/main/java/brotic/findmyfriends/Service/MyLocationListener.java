package brotic.findmyfriends.Service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
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

    @Override
    public void onLocationChanged(Location location) {

            LocationUtils utils = new LocationUtils();
        Log.d("==== ALERT fuck", "fuck !");

            try {
                FileInputStream file = MyActivity.getAct().openFileInput("saveForLocation");
                ObjectInputStream is = new ObjectInputStream(file);
                DataSaveForLocation s = (DataSaveForLocation) is.readObject();
                file.close();

                BroticCommunication com = new BroticCommunication("position");
                SendPositionTask task = new SendPositionTask();

                Log.d("==== ALERT", s.toString());

                if (s != null) {
                    com.addParamGet("id", String.valueOf(s.getId()));
                    com.addParamGet("token", s.getToken());
                    com.addParamGet("latitude", utils.getLatitude(location));
                    com.addParamGet("longitude", utils.getLongitude(location));

                    task.execute(com);

                    Log.d("==== ALERT", com.getParamsGet().get("latitude"));
                }
            } catch (ClassNotFoundException | IOException e) {
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
