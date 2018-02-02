package in.apps.maitreya.samaritansmumbai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewLogsRecyclerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<LogEntry> logEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs_recycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //

        //
        //Recycler view
        mRecyclerView = findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //
        logEntries = new ArrayList<>();
        //
        mAdapter = new MyAdapter(logEntries, this);
        mRecyclerView.setAdapter(mAdapter);
        //

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("log/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LogEntry entry = dataSnapshot.getValue(LogEntry.class);
                logEntries.add(entry);
                mAdapter = new MyAdapter(logEntries,ViewLogsRecyclerActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                Log.d("ChildAdded","check_test "+logEntries.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LogEntry entry = dataSnapshot.getValue(LogEntry.class);
                logEntries.remove(entry);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
