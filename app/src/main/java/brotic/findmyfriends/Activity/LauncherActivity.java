package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import brotic.findmyfriends.Presenter.MainPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

public class LauncherActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        MainPresenter.launcher();
    }
}
