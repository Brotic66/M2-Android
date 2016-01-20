package brotic.findmyfriends.Model;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 20/01/2016
 */
public class Demand {

    private User demandeur;
    private User demande;

    public Demand(User demandeur, User demande) {
        this.demandeur = demandeur;
        this.demande = demande;
    }

    public User getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(User demandeur) {
        this.demandeur = demandeur;
    }

    public User getDemande() {
        return demande;
    }

    public void setDemande(User demande) {
        this.demande = demande;
    }
}
