package in.apps.maitreya.samaritansmumbai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    //
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0, MY_PERMISSIONS_REQUEST_NETWORK = 1;
    LocationManager locationManager;
    //
    private FirebaseAuth mAuth;
    private boolean userLoggedIn =false;
    private TextView username, network_location_access;
    private Button loginButton,logoutButton,createUserButton;
    private FloatingActionButton refresh_fab;
    boolean doubleBackToExitPressedOnce = false;
    List<String> ad_users = Arrays.asList("maitreya.save@gmail.com","samaritans.helpline@gmail.com");

    private static final String TAG = StartActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        username = (TextView) findViewById(R.id.user_name_tv);
        network_location_access = (TextView) findViewById(R.id.network_location_access_tv);
        loginButton = (Button) findViewById(R.id.login_button);
        logoutButton = (Button) findViewById(R.id.logout_button);
        createUserButton = (Button) findViewById(R.id.create_user_button);
        refresh_fab = (FloatingActionButton) findViewById(R.id.refresh_home_fab);

        //Shared Preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        //Server TimeStamp
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Long ts = (Long) snapshot.getValue();
                Date date = new Date(ts);
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                String dateText = df2.format(date);
                editor.putString("time_stamp", dateText); // Storing string
                editor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.setValue(ServerValue.TIMESTAMP);
        //

        //Location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
    public void promptUserLogin(View v){

            if(userLoggedIn){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //checkpermissions
        if(Functions.checkPermissions(this,MY_PERMISSIONS_REQUEST_LOCATION,locationManager)) {
            network_location_access.setVisibility(View.GONE);
            refresh_fab.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            if (currentUser != null) {
                if (ad_users.contains(currentUser.getEmail()))
                    createUserButton.setVisibility(View.VISIBLE);
                else
                    createUserButton.setVisibility(View.GONE);
                updateUI(currentUser);
            } else {
                createUserButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
            }
        }
        else{
            loginButton.setVisibility(View.GONE);
            createUserButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
        }
    }
    public void updateUI(FirebaseUser currentUser){
        userLoggedIn =true;
        loginButton.setText(R.string.login_proceed);
        logoutButton.setVisibility(View.VISIBLE);
        String hi_user = "Hi "+currentUser.getDisplayName();;
        username.setText(hi_user);
    }
    public void promptUserLogout(View v){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(getIntent());
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    public void createUser(View v){
        Intent intent = new Intent(this,CreateUserActivity.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull  String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        recreate();
                    } else {
                        Functions.showGPSDisabledAlertToUser(this);
                    }
                } else {
                    Toast.makeText(this, "Sorry! Location Access Permission needed!", Toast.LENGTH_SHORT).show();
                    //nothing can be done
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void clickRefresh(View v){
        recreate();
    }
}
