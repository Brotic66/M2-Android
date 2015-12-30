package brotic.findmyfriends.Service;

import java.io.Serializable;

/**
 * Created by brice on 30/12/15.
 */
public class DataSaveForLocation implements Serializable {

    private String token;
    private int id;

    public DataSaveForLocation(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
