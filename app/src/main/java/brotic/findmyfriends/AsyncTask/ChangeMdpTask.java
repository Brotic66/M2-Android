package brotic.findmyfriends.AsyncTask;

import android.app.ProgressDialog;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

/**
 * @author Brice VICO
 * @date 19/01/2016
 */
public class ChangeMdpTask extends BroticAsyncTask
{
    private ProgressDialog prog;

    @Override
    protected void onPreExecute()
    {
        this.prog = ProgressDialog.show(MyActivity.getAct(), "Modification", "Veuillez patienter", true);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        if (this.prog.isShowing())
            this.prog.dismiss();

        try {
            if (jsonObject.getInt("response") == 1)
                Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.mdpChanged), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MyActivity.getAct().getBaseContext(), MyActivity.getAct().getString(R.string.mdpNotChanged), Toast.LENGTH_SHORT).show();

            MyActivity.getAct().finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
