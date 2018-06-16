package in.apps.maitreya.samaritansmumbai.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.Call_Log;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;

public class UpdateCallerProfile extends AppCompatActivity {
    private FirebaseDatabase database;
    private CallerProfile callerProfile;
    private StringBuilder updates;
    private EditText callerProfileName, callerProfileAge, callerProfileCommonIdentifiers, callerProfileSupportSystem, callerProfileOccupation, callerProfileHealthIssues, callerProfileFrequency, callerProfileSuicideAttempts, callerProfileGist, callerProfileNewInfo;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_caller_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        updates = new StringBuilder("");
        //
        pref = getSharedPreferences("MyPref", 0); // 0 - for private mode

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = callerProfileName.getText().toString();
                String age = callerProfileAge.getText().toString();
                String commonIdentifiers = callerProfileCommonIdentifiers.getText().toString();
                String supportSystem = callerProfileSupportSystem.getText().toString();
                String occupation = callerProfileOccupation.getText().toString();
                String healthIssues = callerProfileHealthIssues.getText().toString();
                String frequency = callerProfileFrequency.getText().toString();
                String suicideAttempts = callerProfileSuicideAttempts.getText().toString();
                String gist = callerProfileGist.getText().toString();
                //
                DatabaseReference myRef = database.getReference("caller_profiles");
                //
                CallerProfile callerProfile1 = (CallerProfile) getIntent().getSerializableExtra("CallerProfile");
                if(!name.equals(callerProfile.getName())) {
                    updates.append("Name from \"").append(callerProfile.getName()).append("\" to \"").append(name).append("\"\n");
                    callerProfile1.setName(name);
                }
                if(!age.equals(callerProfile.getAge())){
                    updates.append("Age from \"").append(callerProfile.getAge()).append("\" to \"").append(age).append("\"\n");
                    callerProfile1.setAge(age);
                }
                if(!commonIdentifiers.equals(callerProfile.getCommon_identifiers())){
                    updates.append("Common Identifiers from \"").append(callerProfile.getCommon_identifiers()).append("\" to \"").append(commonIdentifiers).append("\"\n");
                    callerProfile1.setCommon_identifiers(commonIdentifiers);
                }
                if(!suicideAttempts.equals(callerProfile.getSuicide_attempts())){
                    updates.append("Suicide Attempts from \"").append(callerProfile.getSuicide_attempts()).append("\" to \"").append(suicideAttempts).append("\"\n");
                    callerProfile1.setSuicide_attempts(suicideAttempts);
                }
                if(!supportSystem.equals(callerProfile.getSupport_system())){
                    updates.append("Support System from \"").append(callerProfile.getSupport_system()).append("\" to \"").append(supportSystem).append("\"\n");
                    callerProfile1.setSupport_system(supportSystem);
                }
                if(!occupation.equals(callerProfile.getOccupation())){
                    updates.append("Occupation from \"").append(callerProfile.getOccupation()).append("\" to \"").append(occupation).append("\"\n");
                    callerProfile1.setOccupation(occupation);
                }
                if(!healthIssues.equals(callerProfile.getHealth_issues())){
                    updates.append("Health Issues from \"").append(callerProfile.getHealth_issues()).append("\" to \"").append(healthIssues).append("\"\n");
                    callerProfile1.setHealth_issues(healthIssues);
                }
                if(!frequency.equals(callerProfile.getCall_frequency())){
                    updates.append("Call Frequency from \"").append(callerProfile.getCall_frequency()).append("\" to \"").append(frequency).append("\"\n");
                    callerProfile1.setCall_frequency(frequency);
                }
                if(!gist.equals(callerProfile.getCall_gist())){
                    updates.append("Gist from \"").append(callerProfile.getCall_gist()).append("\" to \"").append(gist).append("\"\n");
                    callerProfile1.setCall_gist(gist);
                }

                //
                List<Call_Log> call_logs = callerProfile1.getCall_logs();
                if(call_logs==null) {
                    call_logs = new ArrayList<>();
                }
                int cnt = pref.getInt("count",0);
                cnt++;
                String timestamp_long=pref.getString("time_stamp", "-1");
                String updated_by = pref.getString("updated_by","-1");
                //
                updates.append(" - ").append(updated_by);
                //
                long ts = Long.parseLong(timestamp_long);
                Date date = new Date(ts);
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                String timestamp = df2.format(date);
                String new_info = callerProfileNewInfo.getText().toString();
                Call_Log callLog = new Call_Log(timestamp,new_info,""+cnt,updates.toString());
                call_logs.add(callLog);

                callerProfile1.setCall_logs(call_logs);
                //
                myRef.child(name).setValue(callerProfile1);
                //
                Toast.makeText(UpdateCallerProfile.this, "done", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //
        callerProfile = (CallerProfile) getIntent().getSerializableExtra("CallerProfile");
        //
        callerProfileName = findViewById(R.id.update_caller_profile_name);
        callerProfileAge = findViewById(R.id.update_caller_profile_age);
        callerProfileCommonIdentifiers = findViewById(R.id.update_caller_profile_common_identifiers);
        callerProfileOccupation =findViewById(R.id.update_caller_profile_occupation);
        callerProfileHealthIssues = findViewById(R.id.update_caller_profile_health_issues);
        callerProfileSupportSystem = findViewById(R.id.update_caller_profile_support_systems);
        callerProfileFrequency = findViewById(R.id.update_caller_profile_frequency);
        callerProfileSuicideAttempts = findViewById(R.id.update_caller_profile_suicide_attempts);
        callerProfileGist = findViewById(R.id.update_caller_profile_gist);
        callerProfileNewInfo = findViewById(R.id.update_caller_profile_new_info);
        //
        //
        callerProfileName.setText(callerProfile.getName());
        callerProfileAge.setText(callerProfile.getAge());
        callerProfileCommonIdentifiers.setText(callerProfile.getCommon_identifiers());
        callerProfileOccupation.setText(callerProfile.getOccupation());
        callerProfileFrequency.setText(callerProfile.getCall_frequency());
        callerProfileHealthIssues.setText(callerProfile.getHealth_issues());
        callerProfileSupportSystem.setText(callerProfile.getSupport_system());
        callerProfileSuicideAttempts.setText(callerProfile.getSuicide_attempts());
        callerProfileGist.setText(callerProfile.getCall_gist());
        //
        //Firebase
        database = FirebaseDatabase.getInstance();
        //
    }

}
