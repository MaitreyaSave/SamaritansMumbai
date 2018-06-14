package in.apps.maitreya.samaritansmumbai.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.apps.maitreya.samaritansmumbai.classes.Functions;
import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.User;


public class CreateUserActivity extends AppCompatActivity {
    // Declare all variables.
    private FirebaseAuth mAuth;
    private static final String TAG = CreateUserActivity.class.getName();
    private EditText userEmail, userName, userPassword;
    private String role,shift_day,shift_time;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Attach toolbar to view.
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch references to Edit Texts.
        userEmail = findViewById(R.id.create_user_email);
        userName = findViewById(R.id.create_user_name);
        userPassword = findViewById(R.id.create_user_password);

        // Get Firebase instance.
        mAuth = FirebaseAuth.getInstance();

        //Default value of radio button.
        role = "user";

        //
        // Get reference to Spinner for Shift Day.
        Spinner spinner = findViewById(R.id.spinner_cu_shift_day_val);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.shift_day_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shift_day = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner for Shift Time
        Spinner spinner1 = findViewById(R.id.spinner_cu_shift_time_val);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.shift_time_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shift_time = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked.
        switch(view.getId()) {
            case R.id.radio_user:
                if (checked)
                    role = ((RadioButton) view).getText().toString();
                    break;
            case R.id.radio_admin:
                if (checked)
                    role = ((RadioButton) view).getText().toString();
                    break;
        }
    }
    public void submitUser(View v){
        // Retrieve text values from Edit Texts.
        String name =userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        // If user name is empty, throw error. Name is required because it is the node field in firebase database.
        if( TextUtils.isEmpty(userName.getText())){
            userName.setError( "Name is required!" );

        }
        else {
            // Length check for name to avoid garbage values.
            if (name.length() > 50) {
                userName.setError("Name is too long! Maximum 50 characters allowed!");
            } else {
                // Check for email id. This field is required for authentication through firebase.
                if( TextUtils.isEmpty(userEmail.getText())){
                    userEmail.setError( "Email is required!" );
                }
                else {
                    // Check for password. This field is required for authentication through firebase.
                    if( TextUtils.isEmpty(userPassword.getText())){
                        userPassword.setError( "Password is required!" );

                    } else {
                        // Length check for password.
                        if (password.length() < 5) {
                            userPassword.setError("Password is too short! Minimum 5 characters required!");
                        } else {
                            createAccount(email, password, name, role);
                        }
                    }
                }
            }
        }
    }
    // Custom Function to create and authenticate account on firebase.
    public  void createAccount(String displayemail, String password, String displayName, String displayrole){
        // Declare variables as final for access to inner class.
        final String name = displayName, email = displayemail, role = displayrole;
        // Firebase function for creating and authenticating user.
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information.
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if(firebaseUser!=null) {
                        // Retrieve values from Shared Preferences.
                        SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
                        String og_email = pref.getString("user_email", "-1");
                        String og_pwd = pref.getString("user_pwd", "-1");
                        long ts = Long.parseLong(pref.getString("time_stamp","-1"));
                        //
                        // Add user name to firebase.
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();
                        firebaseUser.updateProfile(profileUpdates);
                        Toast.makeText(CreateUserActivity.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();

                        // Create user object and attach it to Firebase Database.
                        User user = new User(name,email,role,shift_day,shift_time);
                        user.setTimestamp(ts);
                        //
                        // Write a user (user name as node) to the Firebase Database.
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users");
                        String uname = user.getUsername();
                        myRef.child(uname).setValue(user);

                        // Read from the database
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Create a toast to display that the database has been updated with the new user.
                                Toast.makeText(CreateUserActivity.this, "Database Updated!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Nothing to do here.
                                //Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });
                        //
                        // Sign-out the newly created user and sign-in the current user (who created the new user)
                        Functions.signOut(CreateUserActivity.this);
                        Functions.signIn(og_email,og_pwd,CreateUserActivity.this,false);
                        //
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CreateUserActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Function to exit activity on double back-press
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to return", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
