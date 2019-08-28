package com.teetaa.phototaker.main.glideutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.teetaa.phototaker.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by wx on 2017/7/22.
 */
public class GlideUtils {

    public static void loadUrlImage(Context context, String url, int errimg, int placeholerimg, ImageView imageView) {
        GlideApp.with(context).asBitmap()
                .load(url).error(errimg)
                .placeholder(placeholerimg)
                .into(imageView);
    }


    public static void loadBitMapImage(Context context, Bitmap bitmap, int errimg, int placeholerimg, ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        GlideApp.with(context).asBitmap()
                .load(bytes).error(errimg)
                .placeholder(placeholerimg)
                .into(imageView);
    }
}
