package brotic.findmyfriends.Security;

import java.io.Serializable;

import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public abstract class Etat implements Serializable {

    protected SecurityContext security;

    public boolean isGranted(MyActivity a) throws SecurityContextException {
        throw new SecurityContextException("Accés refusé");
    }

    public User getUtilisateur() throws SecurityContextException {
        throw new SecurityContextException("Vous n'êtes pas authentifié");
    }


    public String getSid() throws SecurityContextException {
        throw new SecurityContextException("Vous n'êtes pas authentifié");
    }

    public void login(User u, String s) throws SecurityContextException {
        throw new SecurityContextException("Authentification déjà effectuée");
    }

    public void logout() throws SecurityContextException {
        throw new SecurityContextException("Vous n'êtes pas authentifié");
    }
}
