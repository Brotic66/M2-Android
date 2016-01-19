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

    private EditText oldMdp;
    private EditText newMdp;

    public ChangeMdpForm(EditText old, EditText mdp) {
        this.oldMdp = old;
        this.newMdp = mdp;
    }

    @Override
    public boolean isValid() {
        boolean toRtn = true;

        if (this.oldMdp.getText().toString().isEmpty()) {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.pseudoFull, Toast.LENGTH_SHORT)
                    .show();
            this.oldMdp.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }

        if (this.newMdp.getText().toString().isEmpty()) {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.mdpFull, Toast.LENGTH_SHORT)
                    .show();
            this.newMdp.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }

        return toRtn;
    }
}
