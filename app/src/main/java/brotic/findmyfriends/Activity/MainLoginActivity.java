package brotic.findmyfriends.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import brotic.findmyfriends.Event.MainClickEvent;
import brotic.findmyfriends.Presenter.MainAfterLoginPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.PositionService;

public class MainLoginActivity extends MyActivity {

    private boolean create = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vide);

        MainAfterLoginPresenter.main();

        Intent intent = new Intent(this, PositionService.class);
        startService(intent);

        this.create = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!this.create)
            MainAfterLoginPresenter.main();
    }
}