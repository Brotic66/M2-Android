package brotic.findmyfriends.Security;

import android.content.Intent;
import brotic.findmyfriends.Activity.LauncherActivity;
import brotic.findmyfriends.Activity.MainActivity;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.Utilisateur;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class EtatAuthentifie extends Etat {

    private Utilisateur util;
    private String sid;

    public EtatAuthentifie(SecurityContext se, Utilisateur u, String s) {
        this.security = se;
        this.util = u;
        this.sid = s;
    }


    @Override
    public boolean isGranted(MyActivity a) throws SecurityContextException {
        if (MyActivity.getAct() instanceof LauncherActivity)
            this.comparerSid();

        return true;
    }

    private boolean comparerSid() {
            /**
             * Comparaison des SID
             * SID de l'application existe - il dans le tableau de SID de la BDD
             */

        return true;
    }

    @Override
    public Utilisateur getUtilisateur() {
        return this.util;
    }

    @Override
    public String getSid() throws SecurityContextException {
        return this.sid;
    }

    @Override
    public void logout() {
        Etat e = new EtatNonAuthentifie(this.security);
        this.security.changeEtat(e);

        String filename = "utilisateur";

        MyActivity.getAct().deleteFile(filename);
        SecurityContext.setInstance(null);

        Intent intent = new Intent(MyActivity.getAct().getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MyActivity.getAct().startActivity(intent);
    }
}
