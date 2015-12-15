package brotic.findmyfriends.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public class BroticAsyncTask extends AsyncTask<BroticCommunication, Integer, JSONObject> {

    @Override
    protected JSONObject doInBackground(BroticCommunication... coms) {
        JSONObject toRtn = null;

        for (BroticCommunication com : coms) {
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
}
