package brotic.findmyfriends.Presenter;

import brotic.findmyfriends.AsyncTask.AcceptFriendTask;
import brotic.findmyfriends.AsyncTask.AskFriendByUsernameTask;
import brotic.findmyfriends.AsyncTask.AskFriendTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 06/01/2016
 */
public class UtilisateurPresenter {

    public static void getFriendDetails(int friendId) {

        if (friendId == 0)
            return;

        BroticCommunication com = new BroticCommunication("test");
    }

    public static void acceptFriend(int friendId) {
        try {
            BroticCommunication com = new BroticCommunication("acceptFriend");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("friendId", String.valueOf(friendId));

            AcceptFriendTask task = new AcceptFriendTask();
            MyActivity.getAct().addTask(task);
            task.execute(com);
        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }

    public static void askFriend(int friendId) {
        try {
            BroticCommunication com = new BroticCommunication("addFriend");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("friendId", String.valueOf(friendId));

            AskFriendTask task = new AskFriendTask();
            MyActivity.getAct().addTask(task);
            task.execute(com);
        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }

    public static void askFriendByUsername(String username) {
        try {
            BroticCommunication com = new BroticCommunication("addFriendByUsername");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("username", username);

            AskFriendByUsernameTask task = new AskFriendByUsernameTask();
            MyActivity.getAct().addTask(task);
            task.execute(com);
        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }
}
