package brotic.findmyfriends.AsyncTask;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import brotic.selfidefi_v1.COM.SDCommunication;
import brotic.selfidefi_v1.Presenter.ActualitePresenter;
import brotic.selfidefi_v1.R;
import brotic.selfidefi_v1.Security.MyActivity;

/**
 * @file ChangePictureTask.java
 * @author Brice VICO
 * @date 23/06/15
 */
public class ChangePictureTask extends AsyncTask<SDCommunication, Integer, Element>
{
    private String type;
    private ProgressDialog prog;

    public ChangePictureTask(String type)
    {
        super();
        this.type = type;
    }

    @Override
    protected void onPreExecute()
    {
        this.prog = ProgressDialog.show(MyActivity.getAct(), "Modification", "Veuillez patienter", true);
    }

    @Override
    protected Element doInBackground(SDCommunication... coms)
    {
        Element toReturn = null;

        for (SDCommunication com : coms)
        {
            try
            {
                toReturn = com.run();
            }
            catch (IOException | ParserConfigurationException | SAXException e)
            {
                if (e instanceof SocketTimeoutException || e instanceof ConnectException)
                    Toast.makeText(MyActivity.getAct().getBaseContext(), "Erreur : Problème de connexion", Toast.LENGTH_LONG).show();
                else
                    e.printStackTrace();
                break;
            }

            if (this.isCancelled())
                break;
        }

        return toReturn;
    }

    @Override
    protected void onPostExecute(Element element)
    {
        if (this.prog.isShowing())
                this.prog.dismiss();

        if (!element.getAttribute("type").equals("erreur"))
        {
            Toast.makeText(MyActivity.getAct().getBaseContext(), "Changement effectué", Toast.LENGTH_LONG).show();
            MyActivity.getAct().onBackPressed();
        }
        else
        {
            if (this.type.equals("mdp"))
            {
                MyActivity.getAct().findViewById(R.id.ancien_mdp).setBackgroundColor(Color.parseColor("#ff0000"));
                MyActivity.getAct().findViewById(R.id.nouveau_mdp).setBackgroundColor(Color.parseColor("#ff0000"));
            }

            Toast.makeText(MyActivity.getAct().getBaseContext(), "Erreur durant le changement", Toast.LENGTH_LONG).show();
        }
    }
}
