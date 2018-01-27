package in.apps.maitreya.samaritansmumbai;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button addLogButton,viewLogsButton;
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
    public void addLogEntry(View v){
        if(Functions.checkPermissions(this,MY_PERMISSIONS_REQUEST_LOCATION,locationManager)) {
            Intent intent = new Intent(this, LogEntryActivity.class);
            startActivity(intent);
        }
    }
    public void viewLogs(View v){
        Intent intent = new Intent(this,ViewLogsRecyclerActivity.class);
        startActivity(intent);
    }
}
