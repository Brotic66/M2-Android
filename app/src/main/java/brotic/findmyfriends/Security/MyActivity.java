/**
 * @author brice
 * @version 1.0.0
 * @date 04/11/2015.
 */

package brotic.findmyfriends.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.HashSet;

import brotic.findmyfriends.Activity.ConnexionActivity;
import brotic.findmyfriends.Activity.InscriptionActivity;
import brotic.findmyfriends.Activity.LauncherActivity;
import brotic.findmyfriends.Activity.MainActivity;

public class MyActivity extends Activity {

    private static MyActivity act;
    protected static HashSet<AsyncTask> tasks;

    public static SecurityContext getSecurity() {
        return SecurityContext.getInstance();
    }

    public static void setAct(MyActivity a) {
        act = a;
    }

    public static MyActivity getAct() {
        return act;
    }

    public MyActivity() {
        super();
        tasks = new HashSet<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = this;

        if (!getSecurity().isGranted(act)) {
            Intent intent = new Intent(MyActivity.getAct().getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MyActivity.getAct().startActivity(intent);
        }

        if (this instanceof LauncherActivity || this instanceof MainActivity || this instanceof ConnexionActivity || this instanceof InscriptionActivity) {

        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        act = this;

        if (!getSecurity().isGranted(act))
        {
            //ActivityLauncher.createMainActivity();
        }
    }

    public void addTask(AsyncTask t) {
        if (!tasks.contains(t))
            tasks.add(t);
    }

    public void removeTask(AsyncTask t) {
        if (tasks.contains(t))
            tasks.remove(t);
    }

    public HashSet<AsyncTask> getTasks()
    {
        return tasks;
    }

    public void removeAllTasks() {
        for (AsyncTask t : tasks)
        {
            if (!t.isCancelled())
                t.cancel(true);
        }

        tasks.clear();
    }
}
