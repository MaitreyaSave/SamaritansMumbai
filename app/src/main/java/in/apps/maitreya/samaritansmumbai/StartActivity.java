package in.apps.maitreya.samaritansmumbai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean userLoggedIn =false;
    private TextView username;
    private Button loginButton,logoutButton,createUserButton;
    boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = StartActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        username = (TextView) findViewById(R.id.user_name_tv);
        loginButton = (Button) findViewById(R.id.login_button);
        logoutButton = (Button) findViewById(R.id.logout_button);
        createUserButton = (Button) findViewById(R.id.create_user_button);
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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            if(currentUser.getEmail().equals("maitreya.save@gmail.com"))
                createUserButton.setVisibility(View.VISIBLE);
            else
                createUserButton.setVisibility(View.GONE);
            updateUI(currentUser);
        }
        else {
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
}