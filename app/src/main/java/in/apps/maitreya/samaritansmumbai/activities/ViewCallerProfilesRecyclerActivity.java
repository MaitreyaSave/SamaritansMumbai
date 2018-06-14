package in.apps.maitreya.samaritansmumbai.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.adapters.CallerProfilesAdapter;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;

public class ViewCallerProfilesRecyclerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<CallerProfile> callerProfiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_caller_profile);
        setSupportActionBar(toolbar);

        //Recycler view
        mRecyclerView = findViewById(R.id.recycler_view_caller_profiles);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //
        callerProfiles = new ArrayList<>();
        //
        //boolean update = getIntent().getBooleanExtra("update_CP",false);
        mAdapter = new CallerProfilesAdapter(callerProfiles, this);

        mRecyclerView.setAdapter(mAdapter);
        //
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("caller_profiles/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                CallerProfile callerProfile = dataSnapshot.getValue(CallerProfile.class);
                callerProfiles.add(callerProfile);
                mAdapter = new CallerProfilesAdapter(callerProfiles, ViewCallerProfilesRecyclerActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                CallerProfile callerProfile = dataSnapshot.getValue(CallerProfile.class);
                callerProfiles.remove(callerProfile);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
