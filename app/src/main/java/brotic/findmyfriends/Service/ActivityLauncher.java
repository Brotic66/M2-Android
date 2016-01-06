package brotic.findmyfriends.Service;

import android.content.Intent;

import java.util.ArrayList;

import brotic.findmyfriends.Activity.FriendDetailsActivity;
import brotic.findmyfriends.Activity.InscriptionActivity;
import brotic.findmyfriends.Activity.LoginActivity;
import brotic.findmyfriends.Activity.MainActivity;
import brotic.findmyfriends.Activity.MainLoginActivity;
import brotic.findmyfriends.Security.MyActivity;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 04/11/2015
 */
public class ActivityLauncher {

    public static void create(String name, boolean finish, ArrayList<Object> args) {
        MyActivity.getAct().removeAllTasks();
        Intent it = new Intent();

        switch (name) {
            case "MainActivity":
                it.setClass(MyActivity.getAct().getBaseContext(), MainActivity.class);
                break;
            case "LoginActivity":
                it.setClass(MyActivity.getAct().getBaseContext(), LoginActivity.class);
                break;
            case "InscriptionActivity":
                it.setClass(MyActivity.getAct().getBaseContext(), InscriptionActivity.class);
                break;
            case "MainLogin":
                it.setClass(MyActivity.getAct().getBaseContext(), MainLoginActivity.class);
                break;
            case "FriendDetailsActivity":
                if (args.size() > 0)
                    it.putExtra("friendId", (int)args.get(0));
                it.setClass(MyActivity.getAct().getBaseContext(), FriendDetailsActivity.class);
                break;
        }

        if (finish)
            MyActivity.getAct().finish();

        MyActivity.getAct().startActivity(it);
    }
}
