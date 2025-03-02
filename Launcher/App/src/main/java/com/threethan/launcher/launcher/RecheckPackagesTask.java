package com.threethan.launcher.launcher;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.threethan.launcher.helper.Platform;

import java.lang.ref.WeakReference;
import java.util.List;

/** @noinspection deprecation */
class RecheckPackagesTask extends AsyncTask<Object, Void, Object> {
    List<ApplicationInfo> foundApps;
    WeakReference<LauncherActivity> ownerRef;
    boolean changeFound;

    @Override
    protected Object doInBackground(Object[] objects) {
        LauncherActivity owner = (LauncherActivity) objects[0];

        PackageManager packageManager = owner.getPackageManager();
        foundApps = packageManager.getInstalledApplications(0);

        changeFound = Platform.installedApps == null ||
                Platform.installedApps.size() != foundApps.size();

        ownerRef = new WeakReference<>(owner);
        return null;

    }
    @Override
    protected void onPostExecute(Object _n) {
        if (changeFound && ownerRef.get() != null) {
            ownerRef.get().reloadPackages();
            ownerRef.get().refreshInterface();
        }
    }
}