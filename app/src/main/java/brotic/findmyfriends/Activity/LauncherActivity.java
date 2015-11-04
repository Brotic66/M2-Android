package brotic.findmyfriends.Activity;

import android.os.Bundle;

import brotic.findmyfriends.Presenter.MainPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

public class LauncherActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        MainPresenter.launcher();
    }
}
