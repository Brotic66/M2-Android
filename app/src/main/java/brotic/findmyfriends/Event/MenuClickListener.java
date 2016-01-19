package brotic.findmyfriends.Event;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
public class MenuClickListener implements Toolbar.OnMenuItemClickListener {

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings:
                ActivityLauncher.create("ConfigActivity", false, null);
                break;
            case R.id.contact:
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                ActivityLauncher.create("ConfigActivity", false, null);
                return true;
            case R.id.contact:
                return true;
            case R.id.geo:
                ActivityLauncher.create("GeoActivity", false, null);
                return true;
            default:
                return true;
        }
    }
}
