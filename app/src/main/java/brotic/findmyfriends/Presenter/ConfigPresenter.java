package brotic.findmyfriends.Presenter;

import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import brotic.findmyfriends.Activity.ChangeMdpActivity;
import brotic.findmyfriends.Activity.ChangePictureActivity;
import brotic.findmyfriends.Activity.MainLoginActivity;
import brotic.findmyfriends.AsyncTask.ChangeMdpTask;
import brotic.findmyfriends.AsyncTask.ChangePictureTask;
import brotic.findmyfriends.AsyncTask.FriendsListTask;
import brotic.findmyfriends.AsyncTask.LoginTask;
import brotic.findmyfriends.Event.ConfigClickListener;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Form.ChangeMdpForm;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BitmapServices;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 19/01/2016
 */
public class ConfigPresenter {

    public static void sendPicture() {
        if (!(MyActivity.getAct() instanceof ChangePictureActivity))
            return;

        final ChangePictureActivity act = (ChangePictureActivity)MyActivity.getAct();
        BitmapServices bitmapServices = new BitmapServices();

        if (act.image == null)
        {
            Toast.makeText(MyActivity.getAct().getBaseContext(), "N'oubliez pas d'envoyer une photo !", Toast.LENGTH_SHORT).show();
            act.findViewById(R.id.suivant_picture).setOnClickListener(new ConfigClickListener());
            return;
        }

        try
        {
            final byte[] imgBytes = Base64.encode(bitmapServices.envoyerImgInFile(act.image), Base64.DEFAULT);
            int id = 0;
            try {
                id = MyActivity.getSecurity().getUtilisateur().getId();
            } catch (SecurityContextException e) {
                e.printStackTrace();
            }
            final String sid = MyActivity.getSecurity().getSid();

            final BroticCommunication com = new BroticCommunication("changePicture");
            com.addParamGet("id", String.valueOf(id));
            com.addParamGet("sid", sid);
            com.setParamPost(imgBytes);

            ChangePictureTask t = new ChangePictureTask();
            MyActivity.getAct().addTask(t);
            t.execute(com);
        }
        catch (SecurityContextException e)
        {
            e.printStackTrace();
        }
    }

    public static void changeMdp() {

        if (!(MyActivity.getAct() instanceof ChangeMdpActivity))
            return;

        final ChangeMdpActivity act = (ChangeMdpActivity)MyActivity.getAct();

        final EditText oldMdp = (EditText) act.findViewById(R.id.ancien_mdp);
        final EditText newMdp = (EditText) act.findViewById(R.id.nouveau_mdp);
        final ChangeMdpForm form = new ChangeMdpForm(oldMdp, newMdp);

        try {
            BroticCommunication com = new BroticCommunication("changeMdp");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("old", oldMdp.getText().toString());
            com.addParamGet("new", newMdp.getText().toString());

            ChangeMdpTask task = new ChangeMdpTask();
            act.addTask(task);
            task.execute(com);

        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }
}
