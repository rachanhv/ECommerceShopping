package vinformax.vinmart.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by subbian.goutham on 12/11/2015.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    private BitmapCompleteListener bitmapCompleteListener;

    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    public LruBitmapCache(Context ctx) {
        this(getCacheSize(ctx));
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return super.sizeOf(key, value);
    }

    @Override
    public Bitmap getBitmap(String s) {
        Bitmap bitmap=get(s);
        if(bitmap!=null)
        {
            if (bitmapCompleteListener != null) {
                bitmapCompleteListener.onComplete(bitmap,s);
            }
        }
        return get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        put(s, bitmap);

    }

    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

    public void setBitmapCompleteListener(BitmapCompleteListener bitmapCompleteListener) {
        this.bitmapCompleteListener = bitmapCompleteListener;
    }



    public interface BitmapCompleteListener {
        void onComplete(Bitmap bitmap, String key);
    }
}
