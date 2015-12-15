package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import brotic.findmyfriends.Event.MainClickEvent;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;


public class LoginActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        ImageView suivant = (ImageView)this.findViewById(R.id.suivant_connexion);

        suivant.setOnClickListener(new MainClickEvent());
    }
}
