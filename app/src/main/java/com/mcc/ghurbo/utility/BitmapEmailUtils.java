package com.mcc.ghurbo.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.print.PrintHelper;
import android.view.View;

import com.mcc.ghurbo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapEmailUtils extends AsyncTask<Void, Void, String>{

    private WeakReference<View> weakWidget;
    private WeakReference<Context> weakContext;
    private String title;

    public BitmapEmailUtils(View view, Context context, String title) {
        this.weakWidget = new WeakReference<View>(view);
        this.weakContext = new WeakReference<Context>(context);
        this.title = title;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.showToast(weakContext.get(), weakContext.get().getResources().getString(R.string.initiating));
    }

    @Override
    protected String doInBackground(Void... voids) {

        View v = weakWidget.get();
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return saveImage(bitmap);
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        Utils.emailImage(weakContext.get(), "", title, "", str);
    }


    private String saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/.ghurbo");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String fname = title + "_" + timeStamp + ".png";

        File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
