package br.com.danyswork.trakttv.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.danyswork.trakttv.R;


public class ImageLoader {

    private static final int STUB_ID = R.mipmap.boldee_icons;

    private Context mContext;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private ExecutorService executorService;

    public ImageLoader(Context context) {
        executorService = Executors.newFixedThreadPool(5);
        mContext = context;
    }

    public void displayImage(String imageId, ImageView imageView) {
        imageViews.put(imageView, imageId);
        Bitmap bitmap = Utils.getResId(mContext, imageId);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            queuePhoto(imageId, imageView);
            imageView.setImageResource(STUB_ID);
        }
    }

    private void queuePhoto(String flagName, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(flagName, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String fileName) {
        try {
            String filename = fileName.concat(".png");
            String src = Constants.IMAGE_LOADER_URL + filename;
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            File destFile = new File(mContext.getFilesDir(), filename);
            OutputStream os = new FileOutputStream(destFile);
            Utils.CopyStream(input, os);
            return Utils.getResId(mContext, fileName);
        } catch (IOException e) {
            return null;
        }
    }


    //Task for the queue
    private class PhotoToLoad {
        public String flagName;
        public ImageView imageView;

        public PhotoToLoad(String f, ImageView i) {
            flagName = f;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            BitmapDisplay bd = new BitmapDisplay(getBitmap(photoToLoad.flagName), photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        return tag != null && tag.equals(photoToLoad.flagName);
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplay implements Runnable {

        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplay(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (!imageViewReused(photoToLoad)) {
                return;
            }

            if (bitmap != null) {
                photoToLoad.imageView.setImageBitmap(bitmap);
            } else {
                photoToLoad.imageView.setImageResource(STUB_ID);
            }
        }
    }
}
