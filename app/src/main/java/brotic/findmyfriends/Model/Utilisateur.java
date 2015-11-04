package brotic.findmyfriends.Model;

import java.io.Serializable;

/**
 * @author Brice VICO
 * @date 04/11/2015
 * @version 1.0.0
 */
public class Utilisateur implements Serializable {

    private int id;
    private String pseudo;
    private String num;

    public Utilisateur() {
    }

    public Utilisateur(int id, String p) {
        this.id = id;
        this.pseudo = p;
    }

    public Utilisateur(int i, String p, String n) {
        this.id = i;
        this.pseudo = p;
        this.num = n;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public int getId() {
        return this.id;
    }
}
