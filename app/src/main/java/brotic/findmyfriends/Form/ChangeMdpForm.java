package brotic.findmyfriends.Form;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.Toast;

import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

/**
 * Created by brice on 15/12/15.
 */
public class ChangeMdpForm implements IForm {

    private EditText username;
    private EditText mdp;

    public ChangeMdpForm(EditText username, EditText mdp) {
        this.username = username;
        this.mdp = mdp;
    }

    @Override
    public boolean isValid() {
        boolean toRtn = true;

        if (this.username.getText().toString().isEmpty()) {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.pseudoFull, Toast.LENGTH_SHORT)
                    .show();
            this.username.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }

        if (this.mdp.getText().toString().isEmpty()) {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.mdpFull, Toast.LENGTH_SHORT)
                    .show();
            this.mdp.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }

        return toRtn;
    }
}
