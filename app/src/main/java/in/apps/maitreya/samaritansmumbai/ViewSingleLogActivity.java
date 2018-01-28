package in.apps.maitreya.samaritansmumbai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ViewSingleLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_log);
        LogEntry logEntry = (LogEntry) getIntent().getSerializableExtra("LogEntry");
        Toast.makeText(this, "name "+logEntry.getPseudo_name(), Toast.LENGTH_SHORT).show();

    }
}
