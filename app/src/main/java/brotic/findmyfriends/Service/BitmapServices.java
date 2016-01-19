/**
 * @author brice
 * @version 1.0.0
 * @file
 * @date 14/06/15.
 */

package brotic.findmyfriends.Service;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import brotic.selfidefi_v1.Security.MyActivity;

public class BitmapServices
{
    private float scale;

    public BitmapServices()
    {
        this.scale = MyActivity.getAct().getResources().getDisplayMetrics().density;
    }

    public Bitmap rotateBitmap(Bitmap source, Matrix mat)
    {
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), mat, true);
    }

    public Bitmap portraitImg(Uri uri)
    {
        ExifInterface exif = null;
        MyActivity.getAct().getContentResolver().notifyChange(uri, null);
        ContentResolver cr = MyActivity.getAct().getContentResolver();
        Bitmap bitmap = null;
        Matrix matrix = new Matrix();

        try
        {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, uri);
            Toast.makeText(MyActivity.getAct().getBaseContext(), "test", Toast.LENGTH_LONG).show();
            exif = new ExifInterface(uri.getPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (exif != null)
        {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation)
            {
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    return this.rotateBitmap(bitmap, matrix);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    return this.rotateBitmap(bitmap, matrix);
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    return this.rotateBitmap(bitmap, matrix);
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    return this.rotateBitmap(bitmap, matrix);
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    return this.rotateBitmap(bitmap, matrix);
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    return this.rotateBitmap(bitmap, matrix);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    return this.rotateBitmap(bitmap, matrix);
                default:
                    return bitmap;
            }
        }
        return bitmap;
    }
   /* public void decodeBitmapFromStream(String key, InputStream stream, int reqWidthDp, int reqHeightDp)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final float scale = MyActivity.getAct().getResources().getDisplayMetrics().density;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Display display = MyActivity.getAct().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        BitmapFactory.decodeStream(stream, null, options);

        if (reqHeightDp == 0)
            reqHeightDp = (int)(outMetrics.heightPixels / scale);
        if (reqWidthDp == 0)
            reqHeightDp = (int)(outMetrics.widthPixels / scale);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, (int)(reqWidthDp * scale), (int)(reqHeightDp * scale));

        try {
            stream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap tmp = BitmapFactory.decodeStream(stream, null, options);
        MyActivity.getAct().cacheBit.put(key, tmp);
    }*/

    public File getFileFromBytes(byte[] bytes, String prefix)
    {
        File toRtn = null;
        FileOutputStream stream;

        try
        {
            toRtn = File.createTempFile(prefix, "tmp");
            stream = new FileOutputStream(toRtn);
            stream.write(bytes);
            stream.flush();
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return toRtn;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public float getScale()
    {
        return this.scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public int getMaxWidthInDp()
    {
        Display display = MyActivity.getAct().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        return (int)(outMetrics.widthPixels / this.scale);
    }

    public int getMaxHeightInDp()
    {
        Display display = MyActivity.getAct().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        return (int)(outMetrics.heightPixels / this.scale);
    }
}
