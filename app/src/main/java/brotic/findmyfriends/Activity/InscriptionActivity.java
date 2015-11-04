package brotic.findmyfriends.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;


public class InscriptionActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        ImageView suivant = (ImageView)this.findViewById(R.id.suivant_inscription);

        //suivant.setOnClickListener(new SecurityClickEvent());
    }
}
