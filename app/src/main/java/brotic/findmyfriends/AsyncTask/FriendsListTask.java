package brotic.findmyfriends.AsyncTask;

import org.json.JSONObject;

import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.Presenter.SecurityPresenter;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class FriendsListTask extends BroticAsyncTask {

    @Override
    protected void onPostExecute(JSONObject rcv) {
        MainAfterLoginPresenter.MainNext(rcv);
    }
}
