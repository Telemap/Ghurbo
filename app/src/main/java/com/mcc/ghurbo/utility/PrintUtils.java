package com.mcc.ghurbo.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v4.print.PrintHelper;
import android.view.View;

import com.mcc.ghurbo.R;

import java.lang.ref.WeakReference;

public class PrintUtils extends AsyncTask<Void, Void, Void>{

    private WeakReference<View> weakWidget;
    private WeakReference<Context> weakContext;
    private String title;

    public PrintUtils(View view, Context context, String title) {
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
    protected Void doInBackground(Void... voids) {

        View v = weakWidget.get();
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        PrintHelper photoPrinter = new PrintHelper(weakContext.get());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap(title, bitmap);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
