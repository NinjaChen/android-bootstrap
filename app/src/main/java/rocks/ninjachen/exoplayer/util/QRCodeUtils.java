package rocks.ninjachen.exoplayer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * QRCodeUtils
 * Created by IrrElephant on 17/5/31.
 */

public class QRCodeUtils {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    // logo缩放尺度
    private static final int LOGO_SCALE = 6;


//    /**
//     * 生成用户的二维码
//     *
//     * @param context 上下文
//     * @param content 二维码内容
//     * @return 生成的二维码图片, or null when meet exception
//     */
//    public static Bitmap createCode(Context context, String content) {
//
////        MultiFormatWriter writer = new MultiFormatWriter();
//        Hashtable<EncodeHintType, Object> hintsTable = new Hashtable();
//
//        // 设置字符编码
//        hintsTable.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//
//        // 设置二维码容错率 L 7%,M 15%,Q 25%,H 30%
//        hintsTable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//
//        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//        BitMatrix matrix = null;
//        try {
//            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,
//                    DensityUtils.dp2px(context, 240),
//                    DensityUtils.dp2px(context, 240),
//                    hintsTable);
//        } catch (WriterException e) {
//            e.printStackTrace();
//            return null;
//        }
//        int width = matrix.getWidth();
//        int height = matrix.getHeight();
//        //二维矩阵转为一维像素数组,也就是一直横着排了
//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                if (matrix.get(x, y)) {
//                    pixels[y * width + x] = BLACK;
//                } else {
//                    pixels[y * width + x] = WHITE;
//                }
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        //通过像素数组生成bitmap
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }
//
//    /**
//     * 生成中间带图片的二维码
//     *
//     * @param context 上下文
//     * @param content 二维码内容
//     * @param logo    二维码中间的图片
//     * @return 生成的二维码图片
//     * @throws WriterException 生成二维码异常
//     */
//    public static Bitmap createCode(Context context, String content, Bitmap logo) {
//
//        Matrix m = new Matrix();
//        float sx = (float) 2 * IMAGE_HALFWIDTH / logo.getWidth();
//        float sy = (float) 2 * IMAGE_HALFWIDTH / logo.getHeight();
//        // 设置缩放信息
//        m.setScale(sx, sy);
//        // 将logo图片按martix设置的信息缩放
//        logo = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),
//                logo.getHeight(), m, false);
//
//        MultiFormatWriter writer = new MultiFormatWriter();
//        Hashtable<EncodeHintType, Object> hst = new Hashtable();
//
//        // 设置字符编码
//        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//
//        // 设置二维码容错率 L 7%,M 15%,Q 25%,H 30%
//        hst.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//
//        // 生成二维码矩阵信息
//        BitMatrix matrix = null;
//        try {
//            matrix = writer.encode(content, BarcodeFormat.QR_CODE,
//                    DensityUtils.dp2px(context, 300),
//                    DensityUtils.dp2px(context, 300), hst);
//        } catch (WriterException e) {
//            e.printStackTrace();
//            // write fail
//            return null;
//        }
//        // 矩阵高度
//        int width = matrix.getWidth();
//        // 矩阵宽度
//        int height = matrix.getHeight();
//        int halfW = width / 2;
//        int halfH = height / 2;
//        // 定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
//        int[] pixels = new int[width * height];
//        // 从行开始迭代矩阵
//        for (int y = 0; y < height; y++) {
//            // 迭代列
//            for (int x = 0; x < width; x++) {
//                // 该位置用于存放图片信息
//                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//                        && y > halfH - IMAGE_HALFWIDTH
//                        && y < halfH + IMAGE_HALFWIDTH) {
//                    // 记录图片每个像素信息
//                    pixels[y * width + x] = logo.getPixel(x - halfW
//                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
//                } else {
//                    // 如果有黑块点，记录信息
//                    if (matrix.get(x, y)) {
//                        // 记录黑块信息
//                        pixels[y * width + x] = BLACK;
//                    } else {
//                        pixels[y * width + x] = WHITE;
//                    }
//                }
//
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        // 通过像素数组生成bitmap
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }

    /**
     * @param context 上下文
     * @param content 二维码内容
     * @param logo    二维码中间的logo,可为null
     * @param size    二维码的尺寸 pixels
     * @return 返回带logo的二维码
     */
    public static Bitmap createCode(Context context, String content, @Nullable Bitmap logo, int size) {
        Hashtable<EncodeHintType, Object> hintsTable = new Hashtable();

        // 设置字符编码
        hintsTable.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 设置二维码容错率 L 7%,M 15%,Q 25%,H 30%
        hintsTable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE,
                    DensityUtils.dp2px(context, size),
                    DensityUtils.dp2px(context, size), hintsTable);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = BLACK;
                    } else {
                        pixels[y * width + x] = WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return addLogoToQRCode(bitmap, logo);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加logo到二维码图片上
     *
     * @param qrcode 原始二维码bitmap
     * @param logo   所需要添加的logo
     * @return
     */
    private static Bitmap addLogoToQRCode(Bitmap qrcode, Bitmap logo) {
        if (qrcode == null || logo == null) {

            return qrcode;
        }

        int srcWidth = qrcode.getWidth();
        int srcHeight = qrcode.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        float scaleFactor = srcWidth * 1.0f / LOGO_SCALE / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(qrcode, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            Paint p = new Paint();
            p.setAntiAlias(true);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, p);
            canvas.save(Canvas.ALL_SAVE_FLAG);

            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
        }
        return bitmap;
    }
}


