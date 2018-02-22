package in.apps.maitreya.samaritansmumbai.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;

public class CreateCallerProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText callerProfileName, callerProfileAge, callerProfileCommonIdentifiers, callerProfileSupportSystem, callerProfileOccupation, callerProfileHealthIssues, callerProfileFrequency, callerProfileSuicideAttempts, callerProfileGist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_caller_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_create_caller_profile);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.submit_caller_profile_fab);
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
                CallerProfile callerProfile = new CallerProfile(name,age,commonIdentifiers,supportSystem,occupation,healthIssues,frequency,suicideAttempts,gist);
                myRef.child(name).setValue(callerProfile);
                //
                Toast.makeText(CreateCallerProfileActivity.this, "done", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        callerProfileName = findViewById(R.id.create_caller_profile_name);
        callerProfileAge = findViewById(R.id.create_caller_profile_age);
        callerProfileCommonIdentifiers = findViewById(R.id.create_caller_profile_common_identifiers);
        callerProfileOccupation =findViewById(R.id.create_caller_profile_occupation);
        callerProfileHealthIssues = findViewById(R.id.create_caller_profile_health_issues);
        callerProfileSupportSystem = findViewById(R.id.create_caller_profile_support_systems);
        callerProfileFrequency = findViewById(R.id.create_caller_profile_frequency);
        callerProfileSuicideAttempts = findViewById(R.id.create_caller_profile_suicide_attempts);
        callerProfileGist = findViewById(R.id.create_caller_profile_gist);

        //
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //
    }

}
