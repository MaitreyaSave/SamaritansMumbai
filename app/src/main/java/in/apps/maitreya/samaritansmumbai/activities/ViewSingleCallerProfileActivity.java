package in.apps.maitreya.samaritansmumbai.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;
import in.apps.maitreya.samaritansmumbai.classes.LogEntry;

public class ViewSingleCallerProfileActivity extends AppCompatActivity {
    TextView cp_name,cp_age,cp_occupation,cp_common_identifiers,cp_health_issues,cp_frequency,cp_suicide_attempts,cp_gist,cp_support_system;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_caller_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_caller_profile_single);
        setSupportActionBar(toolbar);
        CallerProfile callerProfile = (CallerProfile) getIntent().getSerializableExtra("CallerProfile");

        //TextViews
        cp_name = findViewById(R.id.single_cp_name);
        cp_age = findViewById(R.id.single_cp_age);
        cp_occupation = findViewById(R.id.single_cp_occupation);
        cp_common_identifiers = findViewById(R.id.single_cp_common_identifiers);
        cp_health_issues = findViewById(R.id.single_cp_heealth_issues);
        cp_frequency = findViewById(R.id.single_cp_frequency);
        cp_suicide_attempts = findViewById(R.id.single_cp_suicide_attempts);
        cp_gist = findViewById(R.id.single_cp_gist);
        cp_support_system = findViewById(R.id.single_cp_support_system);

        //
        String temp = getResources().getString(R.string.caller_profile_name)+": "+callerProfile.getName();
        cp_name.setText(temp);
        temp = getResources().getString(R.string.caller_profile_age)+": "+callerProfile.getAge();
        cp_age.setText(temp);
        temp = getResources().getString(R.string.caller_profile_common_identifiers)+": "+callerProfile.getCommon_identifiers();
        cp_common_identifiers.setText(temp);
        temp = getResources().getString(R.string.caller_profile_health_issues)+": "+callerProfile.getHealth_issues();
        cp_health_issues.setText(temp);
        temp = getResources().getString(R.string.caller_profile_occupation)+": "+callerProfile.getOccupation();
        cp_occupation.setText(temp);
        temp = getResources().getString(R.string.caller_profile_frequency)+": "+callerProfile.getCall_frequency();
        cp_frequency.setText(temp);
        temp = getResources().getString(R.string.caller_profile_suicide_attempts)+": "+callerProfile.getSuicide_attempts();
        cp_suicide_attempts.setText(temp);
        temp = getResources().getString(R.string.caller_profile_gist)+": "+callerProfile.getCall_gist();
        cp_gist.setText(temp);
        temp = getResources().getString(R.string.caller_profile_support_system)+": "+callerProfile.getSupport_system();
        cp_support_system.setText(temp);
    }

}
