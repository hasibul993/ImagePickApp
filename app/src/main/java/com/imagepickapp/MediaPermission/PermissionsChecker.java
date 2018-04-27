package com.imagepickapp.MediaPermission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class PermissionsChecker {

    public static PermissionsChecker permissionsChecker = null;

    private PermissionsChecker() {
    }

    public static PermissionsChecker getInstance() {
        if (permissionsChecker == null)
            permissionsChecker = new PermissionsChecker();

        return permissionsChecker;
    }

    public boolean lacksPermissions(Context context,String... permissions) {
        boolean isLack = false;
        try {
            for (String permission : permissions) {
                if (lacksPermission(context,permission)) {
                    isLack = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isLack;
    }

    private boolean lacksPermission(Context context,String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }

}