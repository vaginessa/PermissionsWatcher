package io.github.nfdz.permissionswatcher.common.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ApplicationInfo extends RealmObject {

    @PrimaryKey
    public String packageName;
    public final static String PACKAGE_NAME_FIELD = "packageName";

    public String label;
    public final static String LABEL_FIELD = "label";

    public Integer versionCode;
    public final static String VERSION_CODE_FIELD = "versionCode";

    public String versionName;
    public final static String VERSION_NAME_FIELD = "versionName";

    public boolean isSystemApplication;
    public final static String IS_SYSTEM_APP_FLAG_FIELD = "isSystemApplication";

    public RealmList<PermissionState> permissions;
    public final static String PERMISSIONS_FIELD = "permissions";

    public boolean notifyPermissions;
    public final static String NOTIFY_FLAG_FIELD = "notifyPermissions";

    public boolean hasChanges;
    public final static String HAS_CHANGES_FLAG_FIELD = "hasChanges";

    public ApplicationInfo() {
        this.packageName = null;
        this.label = null;
        this.versionCode = null;
        this.versionName = null;
        this.isSystemApplication = false;
        this.permissions = null;
        this.notifyPermissions = false;
        this.hasChanges = false;
    }

    public ApplicationInfo(@NonNull String packageName,
                           @Nullable String label,
                           @Nullable Integer versionCode,
                           @Nullable String versionName,
                           boolean isSystemApplication,
                           @NonNull RealmList<PermissionState> permissions,
                           boolean notifyPermissions,
                           boolean hasChanges) {
        this.packageName = packageName;
        this.label = label == null ? packageName : label;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.isSystemApplication = isSystemApplication;
        this.permissions = permissions;
        this.notifyPermissions = notifyPermissions;
        this.hasChanges = hasChanges;
    }

    public static ApplicationInfo copyToRealm(Realm realm, ApplicationInfo app, boolean newIsChange) {
        ApplicationInfo managed = realm.createObject(ApplicationInfo.class, app.packageName);
        managed.label = app.label;
        managed.versionCode = app.versionCode;
        managed.versionName = app.versionName;
        managed.isSystemApplication = app.isSystemApplication;
        managed.notifyPermissions = app.notifyPermissions;
        managed.hasChanges = newIsChange && app.hasChanges;
        for (PermissionState permission : app.permissions) {
            PermissionState managedPermission = realm.copyToRealm(permission);
            managedPermission.hasChanged = newIsChange && permission.hasChanged;
            managed.permissions.add(managedPermission);
        }
        return managed;
    }

}
