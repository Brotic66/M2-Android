package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import brotic.findmyfriends.Event.MainClickListener;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;


public class LoginActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        ImageView suivant = (ImageView)this.findViewById(R.id.suivant_connexion);

        suivant.setOnClickListener(new MainClickListener());
    }
}
