package brotic.findmyfriends.Security;

import java.io.Serializable;

import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class SecurityContext implements Serializable {

    private static SecurityContext instance;
    private Etat e;

    private SecurityContext() {
        instance = this;
        this.e = new EtatNonAuthentifie(this);
    }

    public static void setInstance(SecurityContext s) {
        instance = s;
    }

    public static SecurityContext getInstance() {
        if (instance == null)
            new SecurityContext();

        return instance;
    }

    public boolean isGranted(MyActivity a) {
        try {
            return this.e.isGranted(a);
        } catch (SecurityContextException e) {
            return false;
        }
    }

    public void login(User u, String s) throws SecurityContextException {
        this.e.login(u, s);

        // Création de l'activité : ActivityLauncher.createActualiteActivityLogin();
    }

    public void logout() throws SecurityContextException {
        this.e.logout();
    }

    public void changeEtat(Etat et) {
        this.e = et;
    }

    public User getUtilisateur() throws SecurityContextException {
       return e.getUtilisateur();
    }

    public String getSid() throws SecurityContextException {
        return this.e.getSid();
    }
}
