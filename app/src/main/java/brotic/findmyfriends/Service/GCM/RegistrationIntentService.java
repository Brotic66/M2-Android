package brotic.findmyfriends.Service.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.findmyfriends.AsyncTask.SendTokenGCMTask;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 17/01/2016
 */
public class RegistrationIntentService extends IntentService {

    public RegistrationIntentService() {
        super("IntentService de registration");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.d("==== ALERT ====", "Voici le token : " + token);

            BroticCommunication com = new BroticCommunication("gcmToken");
            com.addParamGet("id", String.valueOf(MyActivity.getSecurity().getUtilisateur().getId()));
            com.addParamGet("token", MyActivity.getSecurity().getSid());
            com.addParamGet("tokenGCM", token);

            com.run();

        } catch (IOException | SecurityContextException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
