package in.apps.maitreya.samaritansmumbai.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.AttendaceLog;
import in.apps.maitreya.samaritansmumbai.classes.Functions;

public class MainActivity extends AppCompatActivity {
    //
    private static boolean isCheckedIn = false;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private static int countOccurrences = 0, requestCodeMain=0;
    private static String subVolunteer="N/A";
    FirebaseUser currentUser;
    SharedPreferences pref;
    AttendaceLog attendaceLog;
    //
    Button checkinButton, checkoutButton;
    int LOCATION_UPDATE_MIN_TIME = 500;
    int LOCATION_UPDATE_MIN_DISTANCE = 0;
    //

    LocationManager locationManager;
    private Location mlocation;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.d("test_dis", "Location is " + location);
                locationManager.removeUpdates(mLocationListener);
            } else {
                Log.d("test_dis", "Location is null");
            }
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
    };
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //


        checkinButton = findViewById(R.id.checkIn);
        checkoutButton = findViewById(R.id.checkOut);
        //
        long lastCheckIn = pref.getLong("last_check_in_timestamp",-1);
        long currentTime = Long.parseLong(pref.getString("time_stamp","-1"));
        countOccurrences = pref.getInt("countCheckin",-1);
        Date date = new Date(lastCheckIn);
        SimpleDateFormat df1 = new SimpleDateFormat("h:mm a", Locale.UK);
        String lastCheckIn_string = df1.format(date);
        date = new Date(currentTime);
        df1 = new SimpleDateFormat("h:mm a", Locale.UK);
        String currentTime_string = df1.format(date);
        Log.d("time_difference","last "+lastCheckIn_string+" current "+currentTime_string+"\ndiff "+(currentTime-lastCheckIn)/60000);
        if(currentTime-lastCheckIn>60*1000){
            countOccurrences=0;
            setCheckInCountSP();
            Log.d("time_difference","true "+(currentTime-lastCheckIn)/1000);
        }
        if(countOccurrences!=1){
            //isCheckedIn=false;
            checkinButton.setEnabled(true);
            checkoutButton.setEnabled(false);
        }
        else{
            isCheckedIn=true;
            checkinButton.setEnabled(false);
            checkoutButton.setEnabled(true);
        }

        //Location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
        mlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
    public void setCheckInCountSP() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("countCheckin",countOccurrences);
        editor.apply();
    }

    public void getLocation() {

        //User Location
        boolean isGPSEnabled = false,isNetworkEnabled = false;
        if (locationManager != null) {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        if (!(isGPSEnabled || isNetworkEnabled))
            Toast.makeText(this, "Please enable GPS to add logs", Toast.LENGTH_SHORT).show();
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                mlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                mlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        //
        mlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.d("test_dis", "loc_m " + mlocation);
        //
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkDistance(){
        boolean checkBool = false;
        if (Functions.checkPermissions(this, MY_PERMISSIONS_REQUEST_LOCATION, locationManager)) {

            //Samaritans Location
            Location source = new Location("S");
            source.setLatitude(19.015046);
            source.setLongitude(72.842617);

            //User Location
            getLocation();

            float distance = 201;
            if (mlocation != null) {
                distance = mlocation.distanceTo(source);
            }
            Log.d("test_dis","d "+distance);
            checkBool = (distance > 200);
        }
        return checkBool;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addLogEntry(View v) {

            if (checkDistance()) {
                if(isCheckedIn) {
                    Intent intent = new Intent(this, LogEntryActivity.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Checkin Error");
                    alertDialogBuilder.setMessage("You cannot create a Log Entry without checking-in!")
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
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Location Error");
                alertDialogBuilder.setMessage("You cannot create a Log Entry outside of Samaritans! In case your GPS is off, please turn it on.")
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

    public void viewLogs(View v) {
        Intent intent = new Intent(this, ViewLogsRecyclerActivity.class);
        startActivity(intent);
    }

    public void requestSub(View v){
        //do nothing
    }

    public void viewCallerProfiles(View v){
        Intent intent = new Intent(this, ViewCallerProfilesRecyclerActivity.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkIn(View v){
        if(countOccurrences==0) {
            if (checkDistance()) {
                if (isCheckedIn) {
                    Toast.makeText(this, "Already checked-in", Toast.LENGTH_SHORT).show();
                    return;
                }
                isCheckedIn = true;
                //
                //Create a prompt for user to input sub
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Subbing Prompt");
                alertDialogBuilder.setMessage("Are you subbing for someone?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), SelectSubRecyclerActivity.class);
                                        startActivityForResult(intent,requestCodeMain);
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                //
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + currentUser.getDisplayName() + "/");
                ref.child("timestamp").setValue(ServerValue.TIMESTAMP);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (countOccurrences == 0&&isCheckedIn) {
                            Long timestamp = (Long) snapshot.child("timestamp").getValue();
                            if (timestamp != null) {
                                Log.d("timestamp_val", "updated main " + timestamp);
                                //time-in
                                Date date = new Date(timestamp);
                                SimpleDateFormat df1 = new SimpleDateFormat("h:mm a", Locale.UK);
                                String time_in_string = df1.format(date);

                                countOccurrences++;

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("check_in", time_in_string); // Storing string
                                editor.putLong("last_check_in_timestamp",timestamp);
                                editor.putInt("countCheckin",countOccurrences);
                                editor.apply();


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //

                checkinButton.setEnabled(false);
                checkoutButton.setEnabled(true);

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Location Error");
                alertDialogBuilder.setMessage("You cannot check-in outside of Samaritans!")
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
        } else{
            Toast.makeText(this, "Cannot check-in more than once a day!", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkOut(View v){
        if(isCheckedIn){
            isCheckedIn = false;
            //
            database = FirebaseDatabase.getInstance();
            //name
            String uname="N/A";
            if (currentUser != null) {
                uname=currentUser.getDisplayName();
            }
            myRef = database.getReference("attendance/"+uname+"/");

            //time-in
            final String time_in_string = pref.getString("check_in","-1");
            //shift
            final String shift= pref.getString("shift","-1");
            //
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/"+uname+"/");
            final DatabaseReference ref_count = FirebaseDatabase.getInstance().getReference("users/"+uname+"/countA/");
            ref.child("timestamp").setValue(ServerValue.TIMESTAMP);
            final String finalUname = uname;
            countOccurrences = pref.getInt("countCheckin",-1);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(countOccurrences==1&&!isCheckedIn) {
                        Long timestamp = (Long) snapshot.child("timestamp").getValue();
                        long countA = (long) snapshot.child("countA").getValue();
                        if (timestamp != null) {
                            Log.d("timestamp_val", "updated main " + timestamp);
                            //time-out
                            Date date = new Date(timestamp);
                            SimpleDateFormat df1 = new SimpleDateFormat("h:mm a", Locale.UK);
                            String time_out_string = df1.format(date);
                            //date
                            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                            String date_string = df2.format(date);

                            attendaceLog = new AttendaceLog(finalUname, date_string, shift, time_in_string, time_out_string);
                            attendaceLog.setSub(subVolunteer);
                            //
                            countA++;
                            String cntString = countA+"";
                            myRef.child(cntString).setValue(attendaceLog);
                            countOccurrences++;

                            setCheckInCountSP();
                            //
                            ref_count.setValue(countA);
                            //
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //
            Toast.makeText(this, "checked-out", Toast.LENGTH_SHORT).show();
            checkinButton.setEnabled(true);
            checkoutButton.setEnabled(false);
            //

        }
        else {
            Toast.makeText(this, "Cannot check-out without checking-in", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == requestCodeMain) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Extract name of volunteer for whom user is substituting
                subVolunteer=data.getStringExtra("subuser");
                Toast.makeText(this, "Checked in for "+subVolunteer, Toast.LENGTH_SHORT).show();
                // Do something with the contact here (bigger example below)
            }
        }
    }
}
