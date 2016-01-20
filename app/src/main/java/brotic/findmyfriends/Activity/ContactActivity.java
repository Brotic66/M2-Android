package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import brotic.findmyfriends.AsyncTask.GetDemandsTask;
import brotic.findmyfriends.Event.ConfigClickListener;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

public class ContactActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        try {
            BroticCommunication com = new BroticCommunication("getDemands");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());

            GetDemandsTask task = new GetDemandsTask();
            this.addTask(task);
            task.execute(com);


        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }
}
