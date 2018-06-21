package rocks.ninjachen.hbridgek.util;

import android.util.Log;

import rocks.ninjachen.hbridgek.BootstrapApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import timber.log.Timber;

/**
 * opera image cache utils
 * image cache use key which based on url
 * Created by ninja on 12/7/16.
 */

public class CachedImageUtils {
    // in byte, 20MB
    final static int cacheMaxSize = 20*1024*1024;

    private static final String JPG_EXTENSION = ".jpg";
    private static final String JPEG_EXTENSION = ".jpeg";
    private static final String PNG_EXTENSION = ".png";
    private static final String DEFAULT_IMAGE_EXTENSION = JPG_EXTENSION;

    public static void removeImageCache(String imageUrl) {
        if(ZeonicUtils.isEmpty(imageUrl)) return;
        File imageFileDir = BootstrapApplication.getInstance().getCacheDir();
        String targetFileName = buildImageFileName(imageUrl);
        File imageFile = new File(imageFileDir, targetFileName);
        if(imageFile.exists()){
            imageFile.delete();
            Timber.d("Deleted image cache: url [%s] at [%s]", imageUrl, imageFile.getAbsolutePath());
        }
    }

        /**
         * down load image, and save to file system.
         * It bounds a file cache(base on filename)
         * 1. check is image exist(cache)
         * 2. download and save to tmp file path(filename generate by timestamp)
         * 3. move from temp file to targetFile
         * @param imageUrl
         * @return imagePath or null
         */
    public static String saveImageFile(String imageUrl) {
        InputStream is = null;
        OutputStream os = null;
        try {
            Timber.d("try to saveImageFile " + imageUrl);
//            File imageFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFileDir = getImageCacheDir();
            Timber.d("cache dir is " + imageFileDir.getAbsolutePath());
            String targetFileName = buildImageFileName(imageUrl);
            String tempFileName = UUID.randomUUID().toString();
            File imageFile = new File(imageFileDir, targetFileName);
            File tempImageFile = new File(imageFileDir, tempFileName);
            // check cache
//            Timber.e("imageFile.length() " + imageFile.length());
//            Timber.e("Math.abs(new Date().getTimeInMinute() - imageFile.lastModified())  " + Math.abs(new Date().getTime() - imageFile.lastModified()));
            // 1. check is image exist(cache)
            if(imageFile.exists() && imageFile.length() > 0
                    && Math.abs(new Date().getTime() - imageFile.lastModified()) <= 10*24*60*60*1000) {
                Timber.d(imageUrl + " hit the cache at " + imageFile.getAbsolutePath() );
                return imageFile.getAbsolutePath();
            }
            // todo enhance to not valid https validation
            // 2. download and save to tmp file path(filename generate by timestamp)
            is = new java.net.URL(imageUrl).openStream();
            os = new FileOutputStream(tempImageFile);
            byte[] data = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(data, 0, data.length)) >= 0) {
                os.write(data, 0, bytesRead);
            }
            Timber.d(imageUrl + " save to  temp cache at " + tempImageFile );
            // 3. move from temp file to targetFile
            if(imageFile.exists()){
                imageFile.delete();
            }
            if(tempImageFile.renameTo(imageFile)){
                Timber.d(imageUrl + " move temp file  to  cache: "+ tempImageFile.getAbsolutePath() + " => " + imageFile.getAbsolutePath() );
                tempImageFile.delete();
                Timber.d("delete temp file " + tempImageFile.getAbsolutePath());
                return imageFile.getAbsolutePath();
            }else {
                return tempImageFile.getAbsolutePath();
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static File getImageCacheDir() {
        return BootstrapApplication.getInstance().getCacheDir();
    }

    /**
     * default is jpg image
     * http://wx.qlogo.cn/mmopen/k5r4WsJ2Vf6OkjVicQGGY6XU3lHIp2Qmp3dDYbkECPjeNLrsZ3KnHmZv1WgL9z9h4TwfD44uvr3cmcTnhFHb4kvxV8BRzhfj4/0
     * ->
     * abc.jpg
     * @param imageUrl http://www.wakfo.com/icity/event/bell/bell_mankong.png
     * @return ssss123123.png
     */
    protected static String buildImageFileName(String imageUrl) {
//        if (!ValidationUtils.isUrl(imageUrl)) return null;
        int i = imageUrl.lastIndexOf(".");
        if (i > 0) {
            // extension is start with "."
            String extension = imageUrl.substring(i);
            if(!JPG_EXTENSION.equals(extension)
                    && !JPEG_EXTENSION.equals(extension)
                    && !PNG_EXTENSION.equals(extension)){
                Timber.w("image extension is not valid %s, use default extension %s. url is %s", extension, DEFAULT_IMAGE_EXTENSION, imageUrl);
                extension = DEFAULT_IMAGE_EXTENSION;
            }
//            Timber.e("extension " + extension);
            String fileName = String.valueOf(Math.abs(imageUrl.hashCode())) + extension;
//            Timber.e("fileName " + fileName);
            return fileName;
        }
        return null;
    }

    /**
     * Check image cache, clear if cache dir is over 20MB
     */
    public static void checkImageCache() {
        Timber.e("checkImageCache");
        SafeAsyncTask task = new SafeAsyncTask() {
            @Override
            public Object call() throws Exception {
                File cacheDir = getImageCacheDir();
                if(cacheDir.exists()){
                    // Clear image url cache
                    File[] imageFiles = cacheDir.listFiles();
                    if(imageFiles != null){
                        long size = 0;
                        for(File f : imageFiles){
                            if(f.isFile())
                                size = size+f.length();
                        }
                        Timber.e("Image cache is " + size/1024 + " kb");
//                    if(size > cacheMaxSize){
                        Timber.e("Image cache reach the max size, do clear ");
                        for(File f : imageFiles){
                            if(f.isFile()){
                                Timber.e("delete image cache " + f.getAbsolutePath());
                                f.delete();
                            }

                        }
//                    }
                    }

                    // Clear image bitmap cache
//                    ImageUtils.clearCache();
                }
                return null;
            }
        };
        task.execute();
    }
}
