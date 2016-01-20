package brotic.findmyfriends.AsyncTask;

import org.json.JSONObject;

import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class GetDemandsTask extends BroticAsyncTask {

    @Override
    protected void onPostExecute(JSONObject rcv) {
        MainAfterLoginPresenter.contact(rcv);
    }
}
