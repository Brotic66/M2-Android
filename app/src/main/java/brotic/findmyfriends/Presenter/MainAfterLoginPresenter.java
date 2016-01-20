package brotic.findmyfriends.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import brotic.findmyfriends.Activity.ContactActivity;
import brotic.findmyfriends.Activity.GeoActivity;
import brotic.findmyfriends.Activity.InscriptionActivity;
import brotic.findmyfriends.Activity.MainLoginActivity;
import brotic.findmyfriends.AsyncTask.FriendsListTask;
import brotic.findmyfriends.AsyncTask.GetUserInZoneTask;
import brotic.findmyfriends.Event.FriendClickListener;
import brotic.findmyfriends.Event.MainClickListener;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.Model.Demand;
import brotic.findmyfriends.Model.User;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;
import brotic.findmyfriends.Service.BuilderFactory;
import brotic.findmyfriends.Service.Builders.DemandeBuilder;
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
            UserBuilder builder = (UserBuilder) BuilderFactory.create("user");
            if (rcv.getInt("response") == 1) {
                JSONArray array = rcv.getJSONArray("friendsList");

                //act.setContentView(R.layout.activity_main_login);
                RelativeLayout loading = (RelativeLayout)act.findViewById(R.id.loading);
                LinearLayout root = (LinearLayout)act.findViewById(R.id.root);

                root.removeView(loading);

                LayoutInflater inflater = (LayoutInflater) MyActivity.getAct().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout parent = (LinearLayout) MyActivity.getAct().findViewById(R.id.layoutFriends);
                parent.removeAllViews();

                for (int i = 0; i < array.length(); i++) {
                    builder.createSimpleFromJSON(array.getJSONObject(i));
                    User friend = builder.getObj();

                    RelativeLayout custom = (RelativeLayout) inflater.inflate(R.layout.element_friend, null);
                    custom.findViewById(R.id.relFriend).setOnClickListener(new MainClickListener(friend.getId()));
                    TextView username = (TextView) custom.findViewById(R.id.pseudo);

                    Picasso.with(act)
                            .load("http://149.202.51.217/Server/web/app_dev.php/getProfilPicture/" + friend.getId() + "/")
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

    public static void geo(GeoActivity geoActivity) {
        try {
            final MainLoginActivity act = (MainLoginActivity) MyActivity.getAct();
            BroticCommunication com = new BroticCommunication("getUserInZone");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addArg("this", geoActivity);

            GetUserInZoneTask task = new GetUserInZoneTask();
            act.addTask(task);
            task.execute(com);

        } catch (SecurityContextException e) {
            e.printStackTrace();
        }
    }

    public static void geoNext(JSONObject rcv, GeoActivity geo) {

        try {
            UserBuilder builder = (UserBuilder) BuilderFactory.create("user");
            if (rcv.getInt("response") == 1) {
                JSONArray array = rcv.getJSONArray("friendsList");

                RelativeLayout loading = (RelativeLayout)geo.findViewById(R.id.loading);
                LinearLayout root = (LinearLayout)geo.findViewById(R.id.root);

                root.removeView(loading);

                LayoutInflater inflater = (LayoutInflater) geo.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout parent = (LinearLayout) geo.findViewById(R.id.layoutFriends);
                parent.removeAllViews();

                for (int i = 0; i < array.length(); i++) {
                    builder.createSimpleFromJSON(array.getJSONObject(i));
                    User user = builder.getObj();

                    RelativeLayout custom = (RelativeLayout) inflater.inflate(R.layout.element_friend, null);
                    custom.findViewById(R.id.relFriend).setOnClickListener(new MainClickListener(user.getId()));
                    TextView username = (TextView) custom.findViewById(R.id.pseudo);

                    Picasso.with(geo)
                            .load("http://149.202.51.217/Server/web/app_dev.php/getProfilPicture/" + user.getId() + "/")
                            .resize(40, 40)
                            .transform(new CircleTransform())
                            .into((ImageView) custom.findViewById(R.id.picture));

                    username.setText(user.getPseudo());
                    parent.addView(custom);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void contact(JSONObject rcv) {
        if (!(MyActivity.getAct() instanceof ContactActivity))
            return;

        try {
            final ContactActivity act = (ContactActivity) MyActivity.getAct();
            DemandeBuilder builder = (DemandeBuilder) BuilderFactory.create("demand");
            if (rcv.getInt("response") == 1) {
                JSONArray array = rcv.getJSONArray("demandeDemande");

                RelativeLayout loading = (RelativeLayout)act.findViewById(R.id.loading);
                LinearLayout root = (LinearLayout)act.findViewById(R.id.root);

                root.removeView(loading);

                LayoutInflater inflater = (LayoutInflater) MyActivity.getAct().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout parent = (LinearLayout) MyActivity.getAct().findViewById(R.id.layoutDemande);
                parent.removeAllViews();

                for (int i = 0; i < array.length(); i++) {
                    builder.createSimpleFromJSON(array.getJSONObject(i));
                    Demand demand = builder.getObj();

                    RelativeLayout custom = (RelativeLayout) inflater.inflate(R.layout.element_demand, null);
                    custom.findViewById(R.id.buttonAccept).setOnClickListener(new FriendClickListener(demand.getDemandeur().getId()));
                    TextView username = (TextView) custom.findViewById(R.id.pseudo);

                    Picasso.with(act)
                            .load("http://149.202.51.217/Server/web/app_dev.php/getProfilPicture/" + demand.getDemandeur().getId() + "/")
                            .resize(40, 40)
                            .transform(new CircleTransform())
                            .into((ImageView) custom.findViewById(R.id.picture));

                    username.setText(demand.getDemandeur().getPseudo());

                    parent.addView(custom);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
