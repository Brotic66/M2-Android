package brotic.findmyfriends.AsyncTask;

import android.util.Log;

import org.json.JSONObject;

import brotic.findmyfriends.Presenter.SecurityPresenter;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 15/12/2015
 */
public class SendPositionTask extends BroticAsyncTask {

    @Override
    protected void onPostExecute(JSONObject rcv) {
        Log.d("==== ALERT", rcv.toString());
    }
}
