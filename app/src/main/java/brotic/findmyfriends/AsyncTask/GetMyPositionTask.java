package brotic.findmyfriends.AsyncTask;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;
import brotic.findmyfriends.Service.CircleTransform;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class GetMyPositionTask extends BroticAsyncTask {

    private GoogleMap map;

    @Override
    protected JSONObject doInBackground(BroticCommunication... coms) {
        JSONObject toRtn = null;

        for (BroticCommunication com : coms) {
            this.map = (GoogleMap) com.getArgs().get("map");

            try {
                com.run();
                toRtn = com.getJson();
            } catch (IOException | ParserConfigurationException | SAXException e) {
                if (e instanceof SocketTimeoutException || e instanceof ConnectException)
                    MyActivity.getAct().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyActivity.getAct().getBaseContext(), "Erreur : Problème de connexion", Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                else
                    e.printStackTrace();
                break;
            }

            if (this.isCancelled())
                break;
        }

        return toRtn;
    }

    @Override
    protected void onPostExecute(JSONObject rcv) {
        LatLng marker = null;
        try {
            marker = new LatLng(
                    Double.parseDouble(rcv.getString("latitude").replace(',', '.')),
                    Double.parseDouble(rcv.getString("longitude").replace(',', '.')));

            this.map.addMarker(new MarkerOptions()
                    .position(marker)
                    .title("Me"));
            this.map.moveCamera(CameraUpdateFactory.newLatLng(marker));

            CircleOptions circleOptions = new CircleOptions()
                    .center(marker)
                    .fillColor(Color.parseColor("#f67e0d"))
                    .radius(1000);

            Circle circle = this.map.addCircle(circleOptions);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.unknowPosition), Toast.LENGTH_SHORT).show();
        }
    }
}
