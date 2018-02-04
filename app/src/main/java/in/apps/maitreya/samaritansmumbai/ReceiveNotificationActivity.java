package in.apps.maitreya.samaritansmumbai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveNotificationActivity extends AppCompatActivity {
    TextView title_tv,message_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //
        toolbar.setNavigationIcon(R.drawable.ic_action_close);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        //
        title_tv = findViewById(R.id.receive_notif_title_val);
        message_tv = findViewById(R.id.receive_notif_body_val);
        //
        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");

        Toast.makeText(this, "bool "+getIntent().getBooleanExtra("notif_bool",false), Toast.LENGTH_SHORT).show();

        title_tv.setText(title);
        message_tv.setText(message);

    }

}
