package in.apps.maitreya.samaritansmumbai.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;
import in.apps.maitreya.samaritansmumbai.classes.Functions;

public class CreateCallerProfileActivity extends AppCompatActivity {
    // Declare all variables for firebase database and edit texts
    private FirebaseDatabase database;
    private EditText callerProfileName, callerProfileAge, callerProfileCommonIdentifiers, callerProfileSupportSystem, callerProfileOccupation, callerProfileHealthIssues, callerProfileFrequency, callerProfileSuicideAttempts, callerProfileGist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Attach toolbar to the view
        setContentView(R.layout.activity_create_caller_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_create_caller_profile);
        setSupportActionBar(toolbar);

        // Setup UI to make soft-input keyboard disapper
        Functions.setupUI(findViewById(R.id.create_caller_profile_scrollView),this);

        // Floating Action Button for submitting caller profile
        FloatingActionButton fab = findViewById(R.id.submit_caller_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Fetch all values from Edit Texts
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
            // If profile name is empty, throw error. Name is required because it is the node field in firebase.
            if( TextUtils.isEmpty(callerProfileName.getText())){
                callerProfileName.setError( "Name is required!" );

            }
            else{
                // Length check for name to avoid garbage values
                if (name.length()>50){
                    callerProfileName.setError( "Name is too long! Maximum 50 characters allowed!" );
                }
                else{
                    //Get a reference to "caller profiles" node from firebase
                    DatabaseReference myRef = database.getReference("caller_profiles");
                    //
                    //create and attach an object of Caller Profile to firebase.
                    CallerProfile callerProfile = new CallerProfile(name,age,commonIdentifiers,supportSystem,occupation,healthIssues,frequency,suicideAttempts,gist);
                    myRef.child(name).setValue(callerProfile);
                    //
                    Toast.makeText(CreateCallerProfileActivity.this, "Caller Profile Added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            }
        });

        // Fetch references to all Edit Texts
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
        // Getting instance of Firebase
        database = FirebaseDatabase.getInstance();

        //
    }

}
