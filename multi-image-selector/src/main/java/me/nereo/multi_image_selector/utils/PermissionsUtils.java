package me.nereo.multi_image_selector.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by dq on 2016/1/27.
 */
public class PermissionsUtils {

    public static boolean hasPermissions(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String permission, int requestCode) {
        if (!hasPermissions(activity, permission)) {
            //申请权限
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }

    }
}
