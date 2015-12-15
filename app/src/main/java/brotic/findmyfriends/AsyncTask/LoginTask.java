package brotic.findmyfriends.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.Presenter.SecurityPresenter;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class LoginTask extends BroticAsyncTask {

    @Override
    protected void onPostExecute(JSONObject rcv) {
        SecurityPresenter.loginNext(rcv);
    }
}
