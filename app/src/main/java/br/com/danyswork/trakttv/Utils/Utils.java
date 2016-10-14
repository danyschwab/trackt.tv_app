package br.com.danyswork.trakttv.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {

    @IdRes
    private static int getResourceId(Context context, String resName, String defType, String defPackage) {
        return context.getResources().getIdentifier(resName, defType, defPackage);
    }

    public static Bitmap getResId(Context context, String flagName) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), Utils.getResourceId(context, flagName, "drawable", context.getPackageName()));

        if (bitmap != null) {
            return bitmap;
        }

        String filename = flagName.concat(".png");
        // I identify images by hashcode. Not a perfect solution, good for the
        // demo.
        File f = new File(context.getFilesDir(), filename);

        // from SD cache
        return decodeFile(f);
    }

    // decodes image and scales it to reduce memory consumption
    private static Bitmap decodeFile(File f) {
        Bitmap bitmap;
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            bitmap = null;
        }
        return bitmap;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}
