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
                User user = new User(
                        obj.getInt("userId"),
                        obj.getString("username"),
                        obj.getString("userPhone")
                );

                this.obj = user;
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
