package brotic.findmyfriends.Form;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.Toast;

import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class RegisterForm implements IForm {

    private EditText pseudo;
    private EditText mdp;
    private EditText phoneNumber;

    public RegisterForm(EditText pseudo, EditText mdp, EditText phoneNumber) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean isValid() {
        boolean toRtn = true;

        if (this.pseudo.getText().toString().isEmpty())
        {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.pseudoFull, Toast.LENGTH_SHORT).show();
            pseudo.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }
        if (this.mdp.getText().toString().isEmpty())
        {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.mdpFull, Toast.LENGTH_SHORT).show();
            mdp.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }
        if (this.phoneNumber.getText().toString().isEmpty())
        {
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.phoneFull, Toast.LENGTH_SHORT).show();
            this.phoneNumber.setBackgroundColor(Color.parseColor("#EE0000"));
            toRtn = false;
        }

        return toRtn;
    }
}
