package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.widget.Button;

import brotic.findmyfriends.Event.MainClickEvent;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;


public class MainActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connexion = (Button)this.findViewById(R.id.button_connexion);
        Button inscription = (Button)this.findViewById(R.id.button_inscription);

        connexion.setOnClickListener(new MainClickEvent());
        inscription.setOnClickListener(new MainClickEvent());
    }
}
