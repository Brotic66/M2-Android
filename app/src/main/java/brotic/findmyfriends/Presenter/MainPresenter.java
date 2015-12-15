/**
 * @author brice VICO
 * @version 1.0.0
 * @date 04/11/2015
 */

package brotic.findmyfriends.Presenter;

import android.os.CountDownTimer;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Security.SecurityContext;
import brotic.findmyfriends.Service.ActivityLauncher;


public class MainPresenter {

    public static void launcher() {
        String FILENAME = "utilisateur";

        //MyActivity.getAct().deleteFile(FILENAME);

        try {
            FileInputStream file = MyActivity.getAct().getBaseContext().openFileInput(FILENAME);
            ObjectInputStream is = new ObjectInputStream(file);
            SecurityContext s = (SecurityContext) is.readObject();
            file.close();

            SecurityContext.setInstance(s);

            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // ne rien faire.
                }

                @Override
                public void onFinish() {
                    MyActivity.getAct().finish();
                    ActivityLauncher.create("MainLogin", true, null);
                }
            }.start();
        } catch (IOException e) {
            Toast.makeText(MyActivity.getAct().getBaseContext(), "Fichier inexistant", Toast.LENGTH_SHORT).show();

            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    MyActivity.getAct().finish();
                    ActivityLauncher.create("MainActivity", true, null);
                }
            }.start();
        } catch (ClassNotFoundException e) {
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    MyActivity.getAct().finish();
                    ActivityLauncher.create("MainActivity", true, null);
                }
            }.start();
        }




    }


}
