package brotic.findmyfriends.Event;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import brotic.findmyfriends.Presenter.SecurityPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Service.ActivityLauncher;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class MainClickListener implements View.OnClickListener {

    private int friendId;

    public MainClickListener() {
        friendId = 0;
    }

    public MainClickListener(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public void onClick(View v) {
        if (this.friendId > 0) {
            ArrayList<Object> arr = new ArrayList<>();
            arr.add(this.friendId);
            ActivityLauncher.create("FriendDetailsActivity", false, arr);
        } else {
            switch (v.getId()) {
                case R.id.button_connexion :
                    ActivityLauncher.create("LoginActivity", false, null);
                    break;
                case R.id.button_inscription :
                    ActivityLauncher.create("InscriptionActivity", false, null);
                    break;
                case R.id.suivant_inscription:
                    SecurityPresenter.register();
                    break;
                case R.id.suivant_connexion:
                    SecurityPresenter.login();
                    break;
            }
        }
    }
}
