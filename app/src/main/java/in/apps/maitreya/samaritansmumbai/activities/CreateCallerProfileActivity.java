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
    private EditText callerProfileName, callerProfileAge, callerProfileCommonIdentifiers;
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
                //
                DatabaseReference myRef = database.getReference("caller_profiles");
                //
                CallerProfile callerProfile = new CallerProfile(name,age,commonIdentifiers,"N/A","N/A","N/A","N/A","N/A","N/A");
                myRef.child(name).setValue(callerProfile);
                //
                Toast.makeText(CreateCallerProfileActivity.this, "done", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        callerProfileName = findViewById(R.id.create_caller_profile_name);
        callerProfileAge = findViewById(R.id.create_caller_profile_age);
        callerProfileCommonIdentifiers = findViewById(R.id.create_caller_profile_common_identifiers);

        //
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //
    }

}
