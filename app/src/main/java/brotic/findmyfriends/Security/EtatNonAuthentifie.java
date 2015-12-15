package brotic.findmyfriends.Security;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import brotic.findmyfriends.Activity.ConnexionActivity;
import brotic.findmyfriends.Activity.InscriptionActivity;
import brotic.findmyfriends.Activity.LauncherActivity;
import brotic.findmyfriends.Activity.MainActivity;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;
import brotic.findmyfriends.Service.ActivityLauncher;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class EtatNonAuthentifie extends Etat {

    public EtatNonAuthentifie(SecurityContext s) {
        this.security = s;
    }


    @Override
    public boolean isGranted(MyActivity a) throws SecurityContextException {
        if (a instanceof LauncherActivity || a instanceof MainActivity || a instanceof ConnexionActivity || a instanceof InscriptionActivity)
            return true;
        else
            throw new SecurityContextException("Accés non authorisé");
    }

    @Override
    public void login(User u, String s) throws SecurityContextException {
        Etat e = new EtatAuthentifie(this.security, u, s);
        this.security.changeEtat(e);

        String filename = "utilisateur";

        try {
            FileOutputStream file = MyActivity.getAct().getBaseContext().openFileOutput(filename, Context.MODE_PRIVATE);

            ObjectOutputStream os = new ObjectOutputStream(file);

            os.writeObject(this.security);

            os.close();
            file.close();

            ActivityLauncher.create("MainLogin", true, null);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
