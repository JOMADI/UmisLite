package com.babcock.umislite;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        return maxMemory / 8;
    }

    LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
