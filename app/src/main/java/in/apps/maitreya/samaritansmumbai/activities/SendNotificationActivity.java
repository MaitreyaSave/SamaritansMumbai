package in.apps.maitreya.samaritansmumbai.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.messaging.FirebaseMessaging;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.NotificationMessage;

public class SendNotificationActivity extends AppCompatActivity {
    private EditText title, message;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private String dataTitle, dataMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        // Handle possible data accompanying notification message.
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("title")) {
                    dataTitle=(String)getIntent().getExtras().get(key);
                }
                if (key.equals("message")) {
                    dataMessage = (String)getIntent().getExtras().get(key);
                }
            }
            showAlertDialog();
        }
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");
        //
        title = findViewById(R.id.send_notif_title_et);
        message = findViewById(R.id.send_notif_body_et);

    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("title: " + dataTitle + "\n" + "message: " + dataMessage);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public void subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("notifications");
        //Toast.makeText(this, "Subscribed to Topic: Notifications", Toast.LENGTH_SHORT).show();
    }
    public void sendMessage() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        myRef.push().setValue(new NotificationMessage(title.getText().toString(), message.getText().toString(),pref.getString("time_stamp","-1")));
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }

    //
    public void sumbitNotif(View v){
        dataTitle = title.getText().toString();
        dataMessage = message.getText().toString();
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Notification");
        alertDialog.setMessage("Are you sure you want to send this Notification?");
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                subscribeToTopic();
                sendMessage();
                finish();
            }
        });
        alertDialog.show();
    }
}
