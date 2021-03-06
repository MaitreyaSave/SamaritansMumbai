package in.apps.maitreya.samaritansmumbai.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.Functions;
import in.apps.maitreya.samaritansmumbai.classes.LogEntry;

public class LogEntryActivity extends AppCompatActivity {

    // Declare variables.
    private static SharedPreferences pref;
    private static String timestamp,sams_name;
    private static String gist;
    private String gender="Male";
    private String tel_door="Tel";
    private String new_old="Old";
    private String suicide_qn="Yes";
    private String risk_level="0";
    private static String problem_nature;
    private String caller_support ="F";
    private String occupation="E";
    private String self_assessment="Stood By";
    private String PU_referred="Yes";
    private static String leader_name;
    private static String pseudo_name;
    private static String leader_spl_msg;
    private static String name;
    private static String age;
    private static String feelings_addressed;
    private static String volunteers_response;
    private static String call_end;
    private static String hours;
    private static String mins;
    private static String ampm;
    private static String duration_hours;
    private static String duration_mins;
    private String language = "E";
    private String frequent_caller ="Yes";
    private static String plan;
    private static String attempt;
    private static String secondary_nature ="N/A";
    public static  int cnt;

    //@SuppressLint("StaticFieldLeak")
    //private static View rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_entry);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the View Pager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Attach tab-layout to View Pager and add "On Tab Selected Listener".
        final TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //


        //Re-initialize static edit texts.
        leader_name="N/A";
        leader_spl_msg="N/A";
        pseudo_name="N/A";
        name="N/A";
        age="N/A";
        hours="00";
        mins="00";
        duration_hours="0";
        duration_mins="00";
        plan="N/A";
        attempt="N/A";
        gist="N/A";
        feelings_addressed="N/A";
        call_end="N/A";
        volunteers_response="N/A";

        // Get current firebase user (user who has logged in).
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            sams_name = firebaseUser.getDisplayName();
        }
        //
        // Fetch Shared Preferences.
        pref = getSharedPreferences("MyPref", 0); // 0 - for private mode

        // Floating Action Button to submit caller (log) details.
        FloatingActionButton fab = findViewById(R.id.submit_caller_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Alert Dialog to prompt user before submitting.
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogEntryActivity.this);
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
                        // Lose focus of current edit text so that the data in that edit text can be fetched.
                        View v =getCurrentFocus();
                        assert v != null;
                        v.clearFocus();

                        // Get count of logs from Shared Preferences and increment it by one.
                        cnt = pref.getInt("count", 0);
                        cnt++;
                        //
                        // Get a reference to Firebase Database.
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("log");
                        // Convert time and duration into hours and minutes.
                        String time = hours + ":" + mins + " " + ampm;
                        String duration = duration_hours + " hours " + duration_mins + " mins";

                        // Create and attach an object of "Log Entry" to the direbase database with "count" (of logs) as node.
                        LogEntry logEntry = new LogEntry(cnt, timestamp, gist, sams_name, gender, tel_door, new_old, suicide_qn, risk_level, problem_nature, leader_name, pseudo_name, leader_spl_msg, name, age, caller_support, occupation, self_assessment, PU_referred, feelings_addressed, volunteers_response, call_end, time, duration, language, frequent_caller, plan, attempt, secondary_nature);
                        final String child_sr_no = String.valueOf(cnt);
                        ref.child(child_sr_no).setValue(logEntry);

                        // Add a value event listener to store the incremented count on Firebase Database.
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                DatabaseReference ref_count = FirebaseDatabase.getInstance().getReference("count");
                                ref_count.setValue(child_sr_no);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //
                        // Create a toast to notify the user that Caller Details were submitted successfully.
                        Toast.makeText(LogEntryActivity.this, "Caller Details submitted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alertDialog.show();
            }
        });

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



    // A placeholder fragment containing a simple view.
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
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_caller_details, container, false);
            //rView =rootView;
            // TextViews and Values.
            TextView tv_cd_date_val, tv_cd_sams_name_val;

            //Layouts
            LinearLayout tab1,tab2,tab3;
            //
            //Initialize Values.
            tv_cd_date_val = rootView.findViewById(R.id.cd_date_val);
            tv_cd_sams_name_val = rootView.findViewById(R.id.sams_name_tv_val);
            //
            //Initializes Layouts.
            tab1 = rootView.findViewById(R.id.tab_linear_layout_child1);
            tab2 = rootView.findViewById(R.id.tab_linear_layout_child2);
            tab3 = rootView.findViewById(R.id.tab_linear_layout_child3);

            // Setup UI to make soft-input keyboard disapper
            Functions.setupUI(rootView.findViewById(R.id.tab_linear_layout_child1),getActivity());
            Functions.setupUI(rootView.findViewById(R.id.tab_linear_layout_child2),getActivity());
            Functions.setupUI(rootView.findViewById(R.id.tab_linear_layout_child3),getActivity());

            // Retrieve Shared Preferences time stamp.
            String timestamp_long=pref.getString("time_stamp", "-1");
            long ts = Long.parseLong(timestamp_long);
            Date date = new Date(ts);
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            timestamp = df2.format(date);

            //
            tv_cd_date_val.setText(timestamp);
            tv_cd_sams_name_val.setText(sams_name);

            //
            // Spinner for Nature of Problem.
            Spinner spinner = rootView.findViewById(R.id.spinner_cd_problem_nature_val);
            // Create an ArrayAdapter using the string array and a default spinner layout.
            Context context=getContext();
            assert context != null;
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.nature_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner.
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    problem_nature = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            //
            // Spinner for Time AM/PM.

            Spinner spinner_ampm = rootView.findViewById(R.id.spinner_cd_ampm);
            ArrayAdapter<CharSequence> adapter_ampm = ArrayAdapter.createFromResource(getContext(),
                    R.array.am_pm_array, android.R.layout.simple_spinner_item);
            adapter_ampm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_ampm.setAdapter(adapter_ampm);

            spinner_ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ampm = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            //
            //Workaround to solve edit text refresh bug for tab1 and tab3
            //for tab1

            final EditText hours_et = rootView.findViewById(R.id.cd_time_hours);
            hours_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    hours = hours_et.getText().toString();
                }
            });
            final EditText mins_et = rootView.findViewById(R.id.cd_time_mins);
            mins_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    mins = mins_et.getText().toString();
                }
            });
            final EditText hours_duration_et = rootView.findViewById(R.id.cd_duration_hours_val);
            hours_duration_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    duration_hours= hours_duration_et.getText().toString();
                }
            });
            final EditText mins_duration_et = rootView.findViewById(R.id.cd_duration_mins_val);
            mins_duration_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    duration_mins= mins_duration_et.getText().toString();
                }
            });
            final EditText name_et = rootView.findViewById(R.id.cd_name_val);
            name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(name_et.getText())){
                    name="N/A";
                }else{
                    name = name_et.getText().toString();
                }
                }
            });
            final EditText age_et = rootView.findViewById(R.id.cd_age_val);
            age_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(age_et.getText())){
                    age="N/A";
                }else{
                    age = age_et.getText().toString();
                }
                }
            });
            final EditText plan_et = rootView.findViewById(R.id.cd_plan_val);
            plan_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(plan_et.getText())){
                    plan="N/A";
                }else{
                    plan = plan_et.getText().toString();
                }
                }
            });
            final EditText attempt_et = rootView.findViewById(R.id.cd_attempt_val);
            attempt_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(attempt_et.getText())){
                    attempt="N/A";
                }else{
                    attempt = attempt_et.getText().toString();
                }
                }
            });
            //
            //for tab3
            final EditText pseudo_name_et = rootView.findViewById(R.id.sams_pseudo_name_val);
            pseudo_name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(pseudo_name_et.getText())){
                    pseudo_name="N/A";
                }else{
                    pseudo_name = pseudo_name_et.getText().toString();
                }

                }
            });
            final EditText leader_name_et = rootView.findViewById(R.id.sams_leader_name_val);
            leader_name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(leader_name_et.getText())){
                    leader_name="N/A";
                }else{
                    leader_name = leader_name_et.getText().toString();
                }
                }
            });
            final EditText leader_msg_et = rootView.findViewById(R.id.sams_leader_msg_val);
            leader_msg_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(leader_msg_et.getText())){
                    leader_spl_msg="N/A";
                }else{
                    leader_spl_msg = leader_msg_et.getText().toString();
                }
                }
            });
            final EditText secondary_nature_et = rootView.findViewById(R.id.cd_problem_nature_secondary_val);
            secondary_nature_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(secondary_nature_et.getText())){
                    secondary_nature="N/A";
                }else{
                    secondary_nature = secondary_nature_et.getText().toString();
                }
                }
            });
            //for tab2
            final EditText gist_et = rootView.findViewById(R.id.cd_gist_et);
            gist_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(gist_et.getText())){
                    gist="N/A";
                }else{
                    gist = gist_et.getText().toString();
                }
                }
            });
            final EditText feelings_addressed_et = rootView.findViewById(R.id.cd_feelings_addressed_et);
            feelings_addressed_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(feelings_addressed_et.getText())){
                    feelings_addressed="N/A";
                }else{
                    feelings_addressed = feelings_addressed_et.getText().toString();
                }
                }
            });
            final EditText volunteers_response_et = rootView.findViewById(R.id.cd_volunteers_response_et);
            volunteers_response_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(volunteers_response_et.getText())){
                    volunteers_response="N/A";
                }else{
                    volunteers_response = volunteers_response_et.getText().toString();
                }
                }
            });
            final EditText call_end_et = rootView.findViewById(R.id.cd_call_end_et);
            call_end_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if( TextUtils.isEmpty(call_end_et.getText())){
                    call_end="N/A";
                }else{
                    call_end = call_end_et.getText().toString();
                }
                }
            });
            //

            //Use separate logic for each tab
            assert getArguments() != null;
            int tabNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (tabNumber){
                case 1:
                    tab2.setVisibility(View.GONE);
                    tab3.setVisibility(View.GONE);
                    break;
                case 2:
                    tab1.setVisibility(View.GONE);
                    tab3.setVisibility(View.GONE);
                    break;
                case 3:
                    tab1.setVisibility(View.GONE);
                    tab2.setVisibility(View.GONE);
                    break;
                default:
            }

            return rootView;
        }
    }

    //A FragmentPagerAdapter that returns a fragment corresponding to one of the sections/tabs/pages.
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
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
    public void onRadioButtonClickedCD(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cd_radio_male:
                if (checked)
                    gender = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_female:
                if (checked)
                    gender = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_door:
                if (checked)
                    tel_door = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_tel:
                if (checked)
                    tel_door = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_suicide_Y:
                if (checked)
                    suicide_qn = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_suicide_N:
                if (checked)
                    suicide_qn = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_new:
                if (checked)
                    new_old = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_old:
                if (checked)
                    // In case the caller is and old caller, prompt user to update caller profile. (If profile exists for this caller)
                    new_old = ((RadioButton) view).getText().toString();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogEntryActivity.this);
                    alertDialog.setTitle("Old Caller");
                    alertDialog.setMessage("Is this caller present in Caller Profile? If yes, please update Caller Profile details");
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    // Start activity to update Caller Profile.
                    alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(LogEntryActivity.this,ViewCallerProfilesRecyclerActivity.class);
                        SharedPreferences.Editor editor =pref.edit();
                        editor.putString("updated_by",sams_name);
                        editor.putBoolean("update_CP",true);
                        editor.apply();
                        startActivity(intent);
                        }
                    });
                    alertDialog.show();
                break;
            case R.id.cd_radio_riskNA:
                if (checked)
                    risk_level = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_risk0:
                if (checked)
                    risk_level = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_risk1:
                if (checked)
                    risk_level = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_risk2:
                if (checked)
                    risk_level = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_risk3:
                if (checked)
                    risk_level = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_support_A:
                if (checked)
                    caller_support = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_support_F:
                if (checked)
                    caller_support = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_support_H:
                if (checked)
                    caller_support = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_support_PG:
                if (checked)
                    caller_support = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_occupation_B:
                if (checked)
                    occupation = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_occupation_E:
                if (checked)
                    occupation = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_occupation_S:
                if (checked)
                    occupation = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_occupation_UE:
                if (checked)
                    occupation = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_occupation_W:
                if (checked)
                    occupation = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_stood_by:
                if (checked)
                    self_assessment = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_lead:
                if (checked)
                    self_assessment = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_pushed:
                if (checked)
                    self_assessment = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_PU_Y:
                if (checked)
                    PU_referred = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_PU_N:
                if (checked)
                    PU_referred = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_E:
                if (checked)
                    language = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_G:
                if (checked)
                    language = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_H:
                if (checked)
                    language = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_radio_M:
                if (checked)
                    language = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_frequent_caller_Y:
                if (checked)
                    frequent_caller = ((RadioButton) view).getText().toString();
                break;
            case R.id.cd_frequent_caller_N:
                if (checked)
                    frequent_caller = ((RadioButton) view).getText().toString();
                break;
        }
    }
}
