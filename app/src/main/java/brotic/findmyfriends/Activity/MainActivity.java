package brotic.findmyfriends.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import brotic.findmyfriends.Event.MainClickListener;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.GCM.RegistrationIntentService;


public class MainActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        Button connexion = (Button)this.findViewById(R.id.button_connexion);
        Button inscription = (Button)this.findViewById(R.id.button_inscription);

        connexion.setOnClickListener(new MainClickListener());
        inscription.setOnClickListener(new MainClickListener());
    }
}
