package com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.View;

import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;

import java.util.HashMap;


/**
 * this class is credited to Mujtahidah from medium.com
 * and the web address is:
 * https://medium.com/@mujtahidah/get-thumbnail-video-from-url-android-f71533168228
 */

public class BitmapForBackGround {
    Context mContext;

    public BitmapForBackGround (Context context) {
        mContext = context;
    }

    @BindingAdapter("setBackground")
    public static void setBackground (View view, Recipe recipe) {

        int lastVideo = recipe.getSteps().size();
        String videoURL = recipe.getSteps().get(lastVideo - 1).getVideoURL();

        try {
            Bitmap bitmap = getThumbNail(videoURL);
            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                Drawable drawable = new BitmapDrawable(bitmap);
                view.setBackground(drawable);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static Bitmap getThumbNail (String path) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = null;

        try {
            retriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(path, new HashMap<String, String>());
            } else {
                retriever.setDataSource(path);
            }
            bitmap = retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new Throwable("unable to retrieve the thumbnail");
        } finally {
            if (retriever != null)
                retriever.release();
        }
        return bitmap;
    }
}
