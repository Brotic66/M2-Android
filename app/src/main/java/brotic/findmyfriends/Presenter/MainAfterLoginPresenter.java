package brotic.findmyfriends.Presenter;

import android.widget.Toast;

import org.json.JSONObject;

import brotic.findmyfriends.Activity.MainLoginActivity;
import brotic.findmyfriends.AsyncTask.FriendsListTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public class MainAfterLoginPresenter {

    public static void main() {
        if (!(MyActivity.getAct() instanceof MainLoginActivity))
            return;

        try {
            final MainLoginActivity act = (MainLoginActivity) MyActivity.getAct();
            BroticCommunication com = new BroticCommunication("friendsList");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());

            FriendsListTask task = new FriendsListTask();
            act.addTask(task);
            task.execute(com);

        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }

    public static void MainNext(JSONObject rcv) {

    }
}
