package brotic.findmyfriends.Service.Builders;

import org.json.JSONException;
import org.json.JSONObject;

import brotic.findmyfriends.Model.Demand;
import brotic.findmyfriends.Model.User;

/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public class DemandeBuilder extends AbstractBuilder {

    @Override
    public void createFromJSON(JSONObject obj) {
        if (obj != null) {
            try {

                this.obj = new Demand(
                        new User(
                                obj.getInt("demandeurId"),
                                obj.getString("demandeurUsername")
                        ),
                        new User(
                                obj.getInt("demandeId"),
                                obj.getString("demandeUsername")
                        )
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSimpleFromJSON(JSONObject obj) {
        if (obj != null) {
            try {

                this.obj = new Demand(
                        new User(
                                obj.getInt("demandeurId"),
                                obj.getString("demandeurUsername")
                        ),
                        new User(
                                obj.getInt("demandeId"),
                                obj.getString("demandeUsername")
                        )
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Demand getObj() {
        return (Demand) super.getObj();
    }
}
