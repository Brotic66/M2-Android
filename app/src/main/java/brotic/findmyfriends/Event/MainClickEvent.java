package brotic.findmyfriends.Event;

import android.view.View;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Service.ActivityFactory;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class MainClickEvent implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_connexion :
                ActivityFactory.create("ConnexionActivity", true, null);
                break;
            case R.id.button_inscription :
                ActivityFactory.create("InscriptionActivity", true, null);
                break;
        }
    }
}
