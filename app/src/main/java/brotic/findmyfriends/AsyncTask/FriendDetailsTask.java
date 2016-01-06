package brotic.findmyfriends.AsyncTask;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;
import brotic.findmyfriends.Service.CircleTransform;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class FriendDetailsTask extends BroticAsyncTask {

    private GoogleMap map;
    private ImageView profilPicture;
    private TextView pseudo;
    private int friendId;

    @Override
    protected JSONObject doInBackground(BroticCommunication... coms) {
        JSONObject toRtn = null;

        for (BroticCommunication com : coms) {
            this.map = (GoogleMap) com.getArgs().get("map");
            this.profilPicture = (ImageView) com.getArgs().get("profilPicture");
            this.pseudo = (TextView) com.getArgs().get("pseudo");
            this.friendId = (int) com.getArgs().get("friendId");

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
            marker = new LatLng(rcv.getDouble("latitude"), rcv.getDouble("longitude"));

            this.map.addMarker(new MarkerOptions()
                    .position(marker)
                    .title("Your friend"));
            this.map.moveCamera(CameraUpdateFactory.newLatLng(marker));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.unknowPosition), Toast.LENGTH_SHORT).show();
        } finally {
            Picasso.with(MyActivity.getAct().getBaseContext())
                    .load("http://149.202.51.217/Server/web/app_dev.php/getProfilPicture/" + this.friendId + "/")
                    .resize(100, 100)
                    .transform(new CircleTransform())
                    .into(this.profilPicture);

            try {
                this.pseudo.setText(rcv.getString("pseudo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
