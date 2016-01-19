package brotic.findmyfriends.Service.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import brotic.findmyfriends.R;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
