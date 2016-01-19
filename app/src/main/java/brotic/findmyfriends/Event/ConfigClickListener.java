package brotic.findmyfriends.Event;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Presenter.ConfigPresenter;
import brotic.findmyfriends.Presenter.SecurityPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.ActivityLauncher;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class ConfigClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deco:
                try {
                    MyActivity.getSecurity().logout();
                } catch (SecurityContextException e) {
                    Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.logoutImpossible), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.changeMdp:
                ActivityLauncher.create("ChangeMdpActivity", false, null);

                break;
            case R.id.changePhoto:
                ActivityLauncher.create("ChangePictureActivity", false, null);
                break;
            case R.id.suivant_picture:
                ConfigPresenter.sendPicture();
                v.setOnClickListener(null);
                break;
            case R.id.changeMdp_valider:
                ConfigPresenter.changeMdp();
        }
    }
}
