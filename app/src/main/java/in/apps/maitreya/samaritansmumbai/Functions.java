package in.apps.maitreya.samaritansmumbai;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by Maitreya on 1/19/2018.
 */

abstract class Functions {
    static void showGPSDisabledAlertToUser(final Activity a) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Open Location Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                a.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    static void showNetworkDisabledAlertToUser(final Activity a){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setMessage("Internet Connection is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Open Network Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                                a.startActivity(intent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    static boolean checkPermissions(Activity a, int permissionsRequest, LocationManager locationManager) {
        boolean result = false;
        if (ActivityCompat.checkSelfPermission(a, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            a.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionsRequest);

        } else {
            if(isNetworkAvailable(a)){
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    result = true;

                } else {
                    showGPSDisabledAlertToUser(a);
                }
            }
            else {
                showNetworkDisabledAlertToUser(a);
            }
        }
        return result;
    }

    private static boolean isNetworkAvailable(Activity a) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
