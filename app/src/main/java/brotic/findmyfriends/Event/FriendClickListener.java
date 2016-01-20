package brotic.findmyfriends.Event;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import brotic.findmyfriends.AsyncTask.FriendDetailsTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Presenter.ConfigPresenter;
import brotic.findmyfriends.Presenter.UtilisateurPresenter;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.ActivityLauncher;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class FriendClickListener implements View.OnClickListener {

    private String phoneNum;
    private int id;
    private EditText username;

    public FriendClickListener(int id) {
        this.id = id;
        this.username = null;
    }
    public FriendClickListener() {
        this.id = 0;
        this.username = null;
    }

    public FriendClickListener(EditText s) {
        this.id = 0;
        this.username = s;
    }

    public FriendClickListener(String phoneNo) {
        this.phoneNum = phoneNo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAccept:
                UtilisateurPresenter.acceptFriend(this.id);
                break;
            case R.id.addFriend:
                UtilisateurPresenter.askFriend(this.id);
                break;
            case R.id.suivant_add:
                if (this.username != null)
                    UtilisateurPresenter.askFriendByUsername(this.username.getText().toString());
                else
                    Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.pleasePseudo), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonSms:
                if (this.phoneNum != null && !this.phoneNum.isEmpty()) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(this.phoneNum, null, MyActivity.getAct().getString(R.string.sms), null, null);
                    Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.smsSend), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
