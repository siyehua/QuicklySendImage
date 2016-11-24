package com.siyehua.listenerfile;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by siyehua on 2016/11/23.
 */
public class QuicklySendImage {
    /**
     * quick send image method.
     *
     * @param context      context
     * @param intervalTime System.currentTimeMillis() and last image interval time
     * @return image path or null form the interval time
     */
    public static String getLastImage(Context context, long intervalTime) {
        String path = null;
        Cursor imgCursor = context.getContentResolver().query(MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DATE_TAKEN + " " +
                "desc limit 1");
        if (imgCursor != null && imgCursor.getCount() > 0) {
            imgCursor.moveToFirst();
            long lastFileTime = Long.parseLong(imgCursor.getString(imgCursor.getColumnIndex
                    (MediaStore.Images.Media.DATE_TAKEN)));
            if (System.currentTimeMillis() - lastFileTime < intervalTime) {
                path = imgCursor.getString(imgCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            imgCursor.close();
        }
        return path;
    }
}
