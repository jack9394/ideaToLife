package me.ideatolife.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


/*
 * Created by jack on 2/24/2016.
 */
public class Utils {

    public static void galleryAddPic(String IMAGE_Path, Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(IMAGE_Path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showLog(String msg) {
        Log.d(AppController.getmInstance().getPackageName(), msg);
    }


    public static int dipToPixels(Context mContext, float value) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mContext.getResources().getDisplayMetrics());
        return (int) pixels;
    }


    public static void saveImage( Context context,Bitmap finalBitmap, String name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + context.getString(R.string.app_name) + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname;
        if (name != null && name.length() > 0) {
            fname = name+".jpg";
        } else {
            fname = "Image-" + n + ".jpg";
        }
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(context,"Picture saved to Galery",Toast.LENGTH_LONG).show();
            galleryAddPic(file.getPath(),context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}