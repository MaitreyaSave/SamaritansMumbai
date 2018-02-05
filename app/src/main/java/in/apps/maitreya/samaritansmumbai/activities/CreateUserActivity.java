package in.apps.maitreya.samaritansmumbai.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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

    private FirebaseAuth mAuth;
    private static final String TAG = CreateUserActivity.class.getName();
    private EditText userEmail, userName, userPassword;
    private String role;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        userEmail = findViewById(R.id.create_user_email);
        userName = findViewById(R.id.create_user_name);
        userPassword = findViewById(R.id.create_user_password);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Default value of radio button
        role = "user";

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
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
        String name =userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        createAccount(email,password,name,role);
    }
    public  void createAccount(String displayemail, String password, String displayName, String displayrole){
        final String name = displayName, email = displayemail, role = displayrole;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if(firebaseUser!=null) {
                                //Shared prefs
                                SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
                                String og_email = pref.getString("user_email", "-1");
                                String og_pwd = pref.getString("user_pwd", "-1");
                                long ts = Long.parseLong(pref.getString("time_stamp","-1"));
                                //
                                //
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();
                                firebaseUser.updateProfile(profileUpdates);
                                Toast.makeText(CreateUserActivity.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();


                                // Database entry
                                User user = new User(name,email,role);
                                String userId = FirebaseAuth.getInstance().getUid();
                                user.setUid(userId);
                                user.setTimestamp(ts);
                                //

                                // Write a message to the database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users");

                                if (userId != null) {
                                    myRef.child(userId).setValue(user);
                                }

                                // Read from the database
                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        Toast.makeText(CreateUserActivity.this, "Database Updated!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });
                                //

                                Functions.signOut(CreateUserActivity.this);
                                Functions.signIn(og_email,og_pwd,CreateUserActivity.this,false);
                                //
                            }
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });




    }
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
