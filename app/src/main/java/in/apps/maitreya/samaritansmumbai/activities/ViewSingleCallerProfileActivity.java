package in.apps.maitreya.samaritansmumbai.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import java.util.List;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.Call_Log;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;

public class ViewSingleCallerProfileActivity extends AppCompatActivity {
    TextView cp_name,cp_age,cp_occupation,cp_common_identifiers,cp_health_issues,cp_frequency,cp_suicide_attempts,cp_gist,cp_support_system,cp_new_info,cp_new_info_ref,cp_new_info_date,cp_new_info_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_caller_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_caller_profile_single);
        setSupportActionBar(toolbar);
        CallerProfile callerProfile = (CallerProfile) getIntent().getSerializableExtra("CallerProfile");
        //
        // Get new information (call logs) about the caller profile
        List<Call_Log> call_logs = callerProfile.getCall_logs();
        //
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
        cp_new_info = findViewById(R.id.list_new_info);
        cp_new_info_ref = findViewById(R.id.new_info_ref);
        cp_new_info_date = findViewById(R.id.new_info_date);
        cp_new_info_details = findViewById(R.id.new_info_details);
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

        //
        //Display new information (call logs)
        String new_info_heading=getResources().getString(R.string.caller_profile_new_info)+"\n";
        cp_new_info.setText(new_info_heading);
        StringBuilder sb_ref=new StringBuilder("<b>Ref No</b><br/><br/>");
        StringBuilder sb_date=new StringBuilder("<b>Date</b><br/><br/>");
        StringBuilder sb_details=new StringBuilder("<b>Details</b><br/><br/>");
        
        if(call_logs!=null) {
            for (int i = 0; i < call_logs.size(); i++) {
                Call_Log call_log = call_logs.get(i);
                sb_ref.append(call_log.getRef_no()).append("<br/>");
                sb_date.append(call_log.getDate()).append("<br/>");
                sb_details.append(call_log.getNew_info()).append("<br/>");
            }
        }
        String new_info_ref =sb_ref.toString();
        String new_info_date=sb_date.toString();
        String new_info_details=sb_details.toString();
        cp_new_info_ref.setText(Html.fromHtml(new_info_ref));
        cp_new_info_date.setText(Html.fromHtml(new_info_date));
        cp_new_info_details.setText(Html.fromHtml(new_info_details));

        //
    }

}
