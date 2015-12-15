package brotic.findmyfriends.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import brotic.findmyfriends.Activity.MainLoginActivity;
import brotic.findmyfriends.AsyncTask.FriendsListTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.User;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;
import brotic.findmyfriends.Service.BuilderFactory;
import brotic.findmyfriends.Service.Builders.UserBuilder;
import brotic.findmyfriends.Service.CircleTransform;

/**
 * @author Brice VICO
 * @date 15/12/2015
 */
public class MainAfterLoginPresenter {

    public static void main() {
        if (!(MyActivity.getAct() instanceof MainLoginActivity))
            return;

        try {
            final MainLoginActivity act = (MainLoginActivity) MyActivity.getAct();
            BroticCommunication com = new BroticCommunication("friendsList");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());

            FriendsListTask task = new FriendsListTask();
            act.addTask(task);
            task.execute(com);

        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }

    public static void MainNext(JSONObject rcv) {
        if (!(MyActivity.getAct() instanceof MainLoginActivity))
            return;

        try {
            final MainLoginActivity act = (MainLoginActivity) MyActivity.getAct();
            UserBuilder builder = (UserBuilder) BuilderFactory.create("User");
            if (rcv.getInt("response") == 1) {
                JSONArray array = rcv.getJSONArray("friendList");

                LayoutInflater inflater = (LayoutInflater) MyActivity.getAct().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout parent = (LinearLayout) MyActivity.getAct().findViewById(R.id.layoutFriends);
                parent.removeAllViews();

                for (int i = 0; i < array.length(); i++) {
                    builder.createSimpleFromJSON(array.getJSONObject(i));
                    User friend = builder.getObj();

                    RelativeLayout custom = (RelativeLayout) inflater.inflate(R.layout.element_friend, null);
                    TextView username = (TextView) custom.findViewById(R.id.pseudo);

                    Picasso.with(act)
                            .load("http://149.202.51.217/getProfilPicture/" + friend.getId())
                            .resize(40, 40)
                            .transform(new CircleTransform())
                            .into((ImageView) custom.findViewById(R.id.picture));

                    username.setText(friend.getPseudo());

                    parent.addView(custom);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
