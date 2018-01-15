package in.apps.maitreya.samaritansmumbai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = CreateUserActivity.class.getName();
    private EditText userEmail, userName, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        userEmail = (EditText) findViewById(R.id.create_user_email);
        userName = (EditText) findViewById(R.id.create_user_name);
        userPassword = (EditText) findViewById(R.id.create_user_password);
        //Firebase
        mAuth = FirebaseAuth.getInstance();

    }
    public void submitUser(View v){
        String name =userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        createAccount(email,password,name);

    }
    public  void createAccount(String email,String password, String displayName){
        final String name = displayName;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null) {
                                //
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();
                                user.updateProfile(profileUpdates);
                                Toast.makeText(CreateUserActivity.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                startActivity(getIntent());
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

}
