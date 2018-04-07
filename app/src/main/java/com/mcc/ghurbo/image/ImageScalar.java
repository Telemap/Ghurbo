package com.mcc.ghurbo.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageScalar {

    private Bitmap scaledBitmap;
    public static final int PROFILE_IMAGE_DIMENSION = 200;
    private static final int COMPRESSION_FACTOR = 70;

    public String scaledImagePathFromGalleryCapture(String selectedImagePath) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        // Calculate inSampleSize
        options.inSampleSize = scaleImageSize(options, PROFILE_IMAGE_DIMENSION, PROFILE_IMAGE_DIMENSION);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        String savedImagePath=null;

        scaledBitmap = adjustImageOrientation(selectedImagePath, BitmapFactory.decodeFile(selectedImagePath, options));

        try {
            savedImagePath = createImageFilePath(scaledBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedImagePath;
    }


    public int scaleImageSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     *
     * @param bitmap
     * @return
     * @throws IOException
     */
    public String createImageFilePath(Bitmap bitmap) throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(timeStamp, ".jpg", storageDir);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if(bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_FACTOR, bytes);
        }
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsoluteFile().toString();
    }


    private Bitmap adjustImageOrientation(String photoPath, Bitmap bitmap) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(photoPath);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);

                return bitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Bitmap getScaledBitmap() {
        return scaledBitmap;
    }

    public Bitmap getImageBitmap(String photoPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(photoPath, options);
    }


    public String encodeToBase64(Bitmap image) {
        if (image != null) {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_FACTOR, byteArrayOS);
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        }
        return null;
    }

    public Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
