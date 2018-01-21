package in.apps.maitreya.samaritansmumbai;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CallerDetailsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    //Shared preferences
    private static SharedPreferences pref;
    private static final String TAG = CallerDetailsActivity.class.getName();
    private static String timestamp,sams_name;
    private String gist;
    public static  int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            sams_name = firebaseUser.getDisplayName();
        }
        //

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.submit_caller_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CallerDetailsActivity.this);
                alertDialog.setTitle("Submit");
                alertDialog.setMessage("Do you wish to submit these Caller Details?");
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                        cnt++;
                        //Get values
                        //
                        EditText gist_et = (EditText) findViewById(R.id.cd_gist_et);
                        gist = gist_et.getText().toString();
                        Log.d(TAG,"mgist "+gist);
                        //
                        //
                        //Firebase Database
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("log");
                        LogEntry logEntry = new LogEntry(cnt,timestamp,gist,sams_name);
                        final String child_sr_no = String.valueOf(cnt);
                        ref.child(child_sr_no).setValue(logEntry);
                        Log.d(TAG,"cnt "+child_sr_no);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DatabaseReference ref_count = FirebaseDatabase.getInstance().getReference("count");
                                ref_count.setValue(child_sr_no);
                                ref_count.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        cnt = Integer.parseInt(dataSnapshot.getValue().toString());
                                        Log.d(TAG,"server cnt "+cnt);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //
                        //
                        Toast.makeText(CallerDetailsActivity.this, "Caller Details submitted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alertDialog.show();
            }
        });
        //Shared preferences
        pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_caller_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_caller_details, container, false);

            // TextViews and Values
            TextView tv_section_description, tv_cd_date_val, tv_cd_sams_name_val;

            //Layouts
            LinearLayout tab1,tab2,tab3;

            //
            //Initializes TextViews
            tv_section_description = (TextView) rootView.findViewById(R.id.section_label);
            //
            //Initialize Values
            tv_cd_date_val = (TextView) rootView.findViewById(R.id.cd_date_val);
            tv_cd_sams_name_val = (TextView) rootView.findViewById(R.id.sams_name_tv_val);

            //
            //Initializes Layouts
            tab1 = (LinearLayout) rootView.findViewById(R.id.tab_linear_layout_child1);
            tab2 = (LinearLayout) rootView.findViewById(R.id.tab_linear_layout_child2);
            tab3 = (LinearLayout) rootView.findViewById(R.id.tab_linear_layout_child3);



            //Shared Preferences time stamp
            String timestamp_long=pref.getString("time_stamp", "-1");
            long ts = Long.parseLong(timestamp_long);
            Date date = new Date(ts);
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            timestamp = df2.format(date);

            //
            tv_cd_date_val.setText(timestamp);
            tv_cd_sams_name_val.setText(sams_name);

            //Use separate logic for each tab
            int tabNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (tabNumber){
                case 1:
                    tv_section_description.setText(getString(R.string.tab1_description));
                    tab2.setVisibility(View.GONE);
                    tab3.setVisibility(View.GONE);
                    break;
                case 2:
                    tv_section_description.setText(getString(R.string.tab2_description));
                    tab1.setVisibility(View.GONE);
                    tab3.setVisibility(View.GONE);
                    break;
                case 3:
                    tv_section_description.setText(getString(R.string.tab3_description));
                    tab1.setVisibility(View.GONE);
                    tab2.setVisibility(View.GONE);
                    break;
                default:
                    tv_section_description.setText("");
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
