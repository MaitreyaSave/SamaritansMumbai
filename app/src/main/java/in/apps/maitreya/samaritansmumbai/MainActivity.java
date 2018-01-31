package in.apps.maitreya.samaritansmumbai;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button addLogButton, viewLogsButton;
    LocationManager locationManager;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    addLogButton.setVisibility(View.VISIBLE);
                    viewLogsButton.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    addLogButton.setVisibility(View.GONE);
                    viewLogsButton.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        addLogButton = (Button) findViewById(R.id.addLog);
        viewLogsButton = (Button) findViewById(R.id.viewLogs);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //
        //Location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addLogEntry(View v) {
        if (Functions.checkPermissions(this, MY_PERMISSIONS_REQUEST_LOCATION, locationManager)) {

            //Samaritans Location
            Location source = new Location("S");
            source.setLatitude(19.015046);
            source.setLongitude(72.842617);
            //source.setLatitude(19.250713);
            //source.setLongitude(72.853800);

            //User Location
            Location location = getLastKnownLocation();
            //
            float distance = 0;
            if (location != null) {
                distance = location.distanceTo(source);
            }
            Log.d("test_dis","distance "+distance);
            if(distance<=50) {
                Intent intent = new Intent(this, LogEntryActivity.class);
                startActivity(intent);
            }
            else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Location Error");
                alertDialogBuilder.setMessage("You cannot create a Log Entry outside of Samaritans!")
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                      dialog.cancel();
                                    }
                                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }

        }
    }

    public void viewLogs(View v) {
        Intent intent = new Intent(this, ViewLogsRecyclerActivity.class);
        startActivity(intent);
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = null;
        if (locationManager != null) {
            providers = locationManager.getProviders(true);
        }
        Location bestLocation = null;
        assert providers != null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
