package in.apps.maitreya.samaritansmumbai.classes;

import android.annotation.SuppressLint;
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
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import in.apps.maitreya.samaritansmumbai.activities.CreateUserActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by Maitreya on 1/19/2018.
 *
 */

public abstract class Functions {
    public static boolean showGPSDisabledAlertToUser(final Activity a) {
        final boolean[] result = {false};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle("Enable GPS?");
        alertDialogBuilder.setMessage("GPS is disabled in your device. Please turn GPS on in order to create a new log entry.")
                .setCancelable(false)
                .setPositiveButton("Open Location Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                result[0] =true;
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
        return result[0];
    }
    private static boolean showNetworkDisabledAlertToUser(final Activity a){
        final boolean[] result = {false};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle("Enable Internet?");
        alertDialogBuilder.setMessage("Internet Connection is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Open Network Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                result[0] =true;
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
        return result[0];
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkPermissions(Activity a, int permissionsRequest, LocationManager locationManager) {
        boolean result = false;
        if (ActivityCompat.checkSelfPermission(a, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            a.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionsRequest);

        } else {
            if(isNetworkAvailable(a)){
                result = (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||showGPSDisabledAlertToUser(a));
            }
            else {
                result = showNetworkDisabledAlertToUser(a);
            }
        }
        return result;
    }

    public static boolean isNetworkAvailable(Activity a) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void signIn(String email, String password, final Activity activity, final boolean create_user){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(activity.getApplicationContext(), "Authentication successful.",Toast.LENGTH_SHORT).show();
                            if(create_user){
                                Intent intent = new Intent(activity.getApplicationContext(),CreateUserActivity.class);
                                activity.startActivity(intent);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public static void signOut(Activity activity){
        FirebaseAuth.getInstance().signOut();
        activity.finish();
        activity.startActivity(activity.getIntent());
    }
    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager!=null&&activity.getCurrentFocus()!=null)
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void setupUI(View view, Activity act) {
        final Activity activity=act;
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView,act);
            }
        }
    }
}
