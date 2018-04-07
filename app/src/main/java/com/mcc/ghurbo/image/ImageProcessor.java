package com.mcc.ghurbo.image;


import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ImageProcessor  extends AsyncTask<Void,Void,Void> {

    private ImageProcessListener imageProcessListener = null;
    private ImageScalar imageScalar;

    private String imagePath, processedImagePath, processedImageEncodedString;

    public ImageProcessor(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageProcessListener(ImageProcessListener imageProcessListener) {
        this.imageProcessListener = imageProcessListener;

        imageScalar = new ImageScalar();
    }

    @Override
    protected Void doInBackground(Void... params) {
        processedImagePath = imageScalar.scaledImagePathFromGalleryCapture(imagePath);

        Bitmap processedBitmap = imageScalar.getScaledBitmap();
        processedImageEncodedString = imageScalar.encodeToBase64(processedBitmap);

        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        if (imageProcessListener != null) {
            imageProcessListener.onImageProcessed(processedImagePath, processedImageEncodedString);
        }
    }
}
