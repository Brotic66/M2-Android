package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import brotic.findmyfriends.Event.MainClickEvent;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

public class MainLoginActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}