package brotic.findmyfriends.Service.Builders;

import org.json.JSONException;
import org.json.JSONObject;

import brotic.findmyfriends.Model.User;

/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public class UserBuilder extends AbstractBuilder {

    @Override
    public void createFromJSON(JSONObject obj) {
        if (obj != null) {
            try {

                this.obj = new User(
                        obj.getInt("userId"),
                        obj.getString("username"),
                        obj.getString("userPhone")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSimpleFromJSON(JSONObject obj) {
        if (obj != null) {
            try {

                this.obj = new User(
                        obj.getInt("id"),
                        obj.getString("username")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getObj() {
        return (User) super.getObj();
    }
}
