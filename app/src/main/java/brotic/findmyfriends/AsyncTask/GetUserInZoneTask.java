package brotic.findmyfriends.AsyncTask;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.Activity.GeoActivity;
import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class GetUserInZoneTask extends BroticAsyncTask {

    private GeoActivity geo;

    @Override
    protected JSONObject doInBackground(BroticCommunication... coms) {
        JSONObject toRtn = null;

        for (BroticCommunication com : coms) {
            this.geo = (GeoActivity) com.getArgs().get("this");

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
        MainAfterLoginPresenter.geoNext(rcv, this.geo);
    }
}
