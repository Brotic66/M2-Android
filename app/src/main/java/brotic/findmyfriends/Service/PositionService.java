package brotic.findmyfriends.Service;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Security.SecurityContext;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PositionService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized (this) {
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
                return -1;
            }

            try {
                Log.d("==== ALERT", "coucou ma gueule");
                DataSaveForLocation save = new DataSaveForLocation(MyActivity.getSecurity().getSid(), MyActivity.getSecurity().getUtilisateur().getId());

                MyActivity.getAct().deleteFile("saveForLocation");

                FileOutputStream file = MyActivity.getAct().getBaseContext().openFileOutput("saveForLocation", Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(file);
                os.writeObject(save);
                os.close();
                file.close();
            } catch (SecurityContextException | IOException e) {
                e.printStackTrace();
            }
            Log.d("==== ALERT", "coucou ma gueule");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listener);

            return START_STICKY;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}