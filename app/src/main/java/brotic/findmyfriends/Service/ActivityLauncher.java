package brotic.findmyfriends.Service;

import android.content.Intent;

import java.util.ArrayList;

import brotic.findmyfriends.Activity.ConnexionActivity;
import brotic.findmyfriends.Activity.InscriptionActivity;
import brotic.findmyfriends.Activity.MainActivity;
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
            case "ConnexionActivity":
                it.setClass(MyActivity.getAct().getBaseContext(), ConnexionActivity.class);
            case "InscriptionActivity":
                it.setClass(MyActivity.getAct().getBaseContext(), InscriptionActivity.class);
        }

        if (finish)
            MyActivity.getAct().finish();

        MyActivity.getAct().startActivity(it);
    }
}
