package in.apps.maitreya.samaritansmumbai.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.adapters.NotificationsAdapter;
import in.apps.maitreya.samaritansmumbai.classes.Functions;
import in.apps.maitreya.samaritansmumbai.classes.NotificationMessage;

public class MainActivity extends AppCompatActivity {
    //
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<NotificationMessage> notificationMessages;
    //
    private TextView mTextMessage;
    private LinearLayout dash_LL, notif_LL;
    LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mlocation;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    dash_LL.setVisibility(View.VISIBLE);
                    notif_LL.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    dash_LL.setVisibility(View.GONE);
                    notif_LL.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        //
        dash_LL = findViewById(R.id.main_ll_dash);
        notif_LL = findViewById(R.id.main_ll_notif);
        notif_LL.setVisibility(View.GONE);
        //

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //
        //Location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //
        //Recycler view
        mRecyclerView = findViewById(R.id.recycler_view_notifications);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //
        notificationMessages = new ArrayList<>();
        //
        mAdapter = new NotificationsAdapter(notificationMessages, this);
        mRecyclerView.setAdapter(mAdapter);
        //

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NotificationMessage notificationMessage = dataSnapshot.getValue(NotificationMessage.class);
                notificationMessages.add(notificationMessage);
                mAdapter = new NotificationsAdapter(notificationMessages,MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NotificationMessage notificationMessage = dataSnapshot.getValue(NotificationMessage.class);
                notificationMessages.remove(notificationMessage);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //
        //
        boolean notification_call = getIntent().getBooleanExtra("notif_bool", false);
        //
        if(notification_call){
            Intent intent = new Intent(this, ReceiveNotificationActivity.class);
            intent.putExtra("title", getIntent().getStringExtra("title"));
            intent.putExtra("message", getIntent().getStringExtra("message"));
            startActivity(intent);
        }
        //
        //User Location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mlocation = location;
            }
        });
        //
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2 * 1000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mlocation = location;
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
        //

        Log.d("test_dis", "loc_m " + mlocation);
        //


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addLogEntry(View v) {
        if (Functions.checkPermissions(this, MY_PERMISSIONS_REQUEST_LOCATION, locationManager)) {

            //Samaritans Location
            Location source = new Location("S");
            source.setLatitude(19.015046);
            source.setLongitude(72.842617);
            //
            //source.setLatitude(19.250713);
            //source.setLongitude(72.853800);


            //User Location
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            float distance = 101;
            if (mlocation != null) {
                distance = mlocation.distanceTo(source);
            }
            Toast.makeText(this, "distance "+distance, Toast.LENGTH_SHORT).show();
            if (distance > 200) {
                Intent intent = new Intent(this, LogEntryActivity.class);
                startActivity(intent);
            } else {
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

    public void requestSub(View v){

    }

    public void viewCallerProfiles(View v){

    }
}
