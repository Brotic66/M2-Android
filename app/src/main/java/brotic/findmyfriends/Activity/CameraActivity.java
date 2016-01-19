package brotic.findmyfriends.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import brotic.findmyfriends.CameraPreview;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

/**
 *
 * On utilisera normalement camera2 pour les android API 21+ mais ici, on utilise la 15.
 * Du coup le warning deprecated à viré !
 */
public class CameraActivity extends Activity
{
    private static final String TAG = "testounou";
    private static int cameraId;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Uri mImage;
    private int result;
    private int cameraType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent it = getIntent();

        mImage = Uri.parse(it.getStringExtra("uri"));
        result = 1;

        if (checkCameraHardware(this.getBaseContext()))
            mCamera = getCameraInstance();
        else {
            this.finish();
            Log.d(TAG, "Aucune caméra détectée");
        }

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Camera.Parameters p = mCamera.getParameters();
        p.set("jpeg-quality", 50);
        p.set("orientation", "portrait");
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
            p.set("rotation", 90);
        else
            p.set("rotation", 270);
        mCamera.setParameters(p);

        ImageView captureButton = (ImageView) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 // get an image from the camera

                                                 Camera.PictureCallback mPicture = new Camera.PictureCallback() {

                                                     @Override
                                                     public void onPictureTaken(byte[] data, Camera camera) {

                                                         File pictureFile = new File(mImage.getPath());

                                                         try {
                                                             FileOutputStream fos = new FileOutputStream(pictureFile);
                                                             fos.write(data);
                                                             fos.close();

                                                             Intent returnIntent = new Intent();
                                                             returnIntent.putExtra("result", result);
                                                             returnIntent.putExtra("cameraType", cameraType);
                                                             setResult(RESULT_OK, returnIntent);
                                                             finish();
                                                         } catch (FileNotFoundException e) {
                                                             Intent returnIntent = new Intent();
                                                             returnIntent.putExtra("result", result);
                                                             setResult(RESULT_CANCELED, returnIntent);
                                                             finish();
                                                             Log.d(TAG, "File not found: " + e.getMessage());
                                                         } catch (IOException e) {
                                                             Intent returnIntent = new Intent();
                                                             returnIntent.putExtra("result", result);
                                                             setResult(RESULT_CANCELED, returnIntent);
                                                             finish();
                                                             Log.d(TAG, "Error accessing file: " + e.getMessage());
                                                         }
                                                     }
                                                 };
                                                 mCamera.takePicture(null, null, mPicture);
                                             }
                                         }
        );

        ImageView changeCam = (ImageView) this.findViewById(R.id.button_changeCamera);
        changeCam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCamera.stopPreview();
                mCamera.release();

                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                {
                    cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    cameraType = Camera.CameraInfo.CAMERA_FACING_FRONT;
                }
                else
                {
                    cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    cameraType = Camera.CameraInfo.CAMERA_FACING_BACK;

                }

                mCamera = Camera.open(cameraId);
                Camera.Parameters p = mCamera.getParameters();
                p.set("jpeg-quality", 50);
                p.set("orientation", "portrait");
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                    p.set("rotation", 90);
                else
                    p.set("rotation", 270);
                mCamera.setParameters(p);

                mPreview = new CameraPreview(MyActivity.getAct(), mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.removeAllViews();
                preview.addView(mPreview);
            }
        });

        if (Camera.getNumberOfCameras() == 1)
        {
            changeCam.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Vérifie si le téléphone dispose d'une camera
     *
     * @param context
     * @return boolean
     */
    private boolean checkCameraHardware(Context context)
    {
        if (Camera.getNumberOfCameras() == 0)
            return false;
        else
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Camera getCameraInstance()
    {
        Camera c = null;
        try
        {
            c = Camera.open(0); // attempt to get a Camera instance
            cameraId = 0;
        }
        catch (Exception e)
        {
            Log.d(TAG, "Aucune camera détectée");
        }
        return c; // returns null if camera is unavailable
    }
}
