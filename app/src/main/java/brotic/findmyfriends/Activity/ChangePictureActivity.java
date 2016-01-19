package brotic.findmyfriends.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import brotic.findmyfriends.Event.ConfigClickListener;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BitmapServices;

public class ChangePictureActivity extends MyActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RESULT_LOAD_IMAGE = 2;
    public static Uri mImageUri;
    public Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_picture);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        ImageView v = (ImageView) this.findViewById(R.id.suivant_picture);
        v.setOnClickListener(new ConfigClickListener());

        Button capture = (Button) this.findViewById(R.id.boutonCapture);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.getAct(), CameraActivity.class);
                File photo = null;
                try {
                    // place where to store camera taken picture
                    photo = createTemporaryFile("picture", "jpg");
                    photo.delete();
                } catch (Exception e) {
                    Toast.makeText(MyActivity.getAct().getBaseContext(), "Please check SD card! Image shot is impossible!", Toast.LENGTH_SHORT).show();
                }

                mImageUri = Uri.fromFile(photo);
                intent.putExtra("uri", mImageUri.getPath());
                //start camera intent
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        Button gallery = (Button) findViewById(R.id.boutonGallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        BitmapServices bitmapServices = new BitmapServices();

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.Thunbmail);
            //... some code to inflate/create/find appropriate ImageView to place grabbed image
            //this.grabImage(imageView, true);

            Picasso.with(this.getBaseContext())
                    .load(mImageUri)
                    .resize(0, (int) (150 * bitmapServices.getScale()))
                    .into(imageView);

            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA, mImageUri.getPath());

            getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            image = bitmapServices.portraitImg(mImageUri);
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.Thunbmail);
            //... some code to inflate/create/find appropriate ImageView to place grabbed image
            mImageUri = intent.getData();

            imageView.setClickable(false);
            imageView.setOnClickListener(null);

            Picasso.with(this.getBaseContext())
                    .load(mImageUri)
                    .resize(0, (int) (150 * bitmapServices.getScale()))
                    .into(imageView);

            image = bitmapServices.portraitImg(mImageUri);
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public void grabImage(ImageView imageView) {
        BitmapServices bitmapServices = new BitmapServices();
        this.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
            /*this.image = bitmapServices.rotateBitmap(bitmap, -90);
            imageView.setImageBitmap(this.image);*/
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
        }
    }
}
