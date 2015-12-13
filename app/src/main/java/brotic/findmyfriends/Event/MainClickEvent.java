package brotic.findmyfriends.Event;

import android.view.View;

import brotic.findmyfriends.Presenter.SecurityPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Service.ActivityLauncher;

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
                ActivityLauncher.create("ConnexionActivity", true, null);
                break;
            case R.id.button_inscription :
                ActivityLauncher.create("InscriptionActivity", true, null);
                break;
            case R.id.suivant_inscription:
                SecurityPresenter.register();
        }
    }
}
