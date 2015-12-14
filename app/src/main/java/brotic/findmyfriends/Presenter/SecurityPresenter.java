package brotic.findmyfriends.Presenter;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import brotic.findmyfriends.Activity.InscriptionActivity;
import brotic.findmyfriends.AsyncTask.RegisterTask;
import brotic.findmyfriends.Form.RegisterForm;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 13/12/2015
 */
public class SecurityPresenter {

    public static void register() {

        if (!(MyActivity.getAct() instanceof InscriptionActivity))
            return;

        final InscriptionActivity act = (InscriptionActivity) MyActivity.getAct();
        final EditText pseudo = (EditText) act.findViewById(R.id.inscription_pseudo);
        final EditText mdp = (EditText) act.findViewById(R.id.inscription_mdp);
        final EditText numTel = (EditText) act.findViewById(R.id.inscription_numero);
        final RegisterForm form = new RegisterForm(pseudo, mdp, numTel);

        if (form.isValid()) {
            BroticCommunication com = new BroticCommunication("register");
            com.addParamGet("pseudo", pseudo.getText().toString());
            com.addParamGet("mdp", mdp.getText().toString());
            com.addParamGet("phoneNumber", numTel.getText().toString());

            RegisterTask task = new RegisterTask();
            act.addTask(task);
            task.execute(com);
        }
    }

    public static void registerNext(JSONObject rcv) {
        try {
            if (rcv.getInt("response") == 1) {
                Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.registerOk, Toast.LENGTH_SHORT).show();
                MyActivity.getAct().finish();
            } else {
                Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.registerError, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyActivity.getAct().getBaseContext(), R.string.registerError, Toast.LENGTH_SHORT).show();
        }
    }

    public static void login() {

    }

    public static void loginNext() {

    }
}
