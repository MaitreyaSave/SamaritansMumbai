package in.apps.maitreya.samaritansmumbai;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class LogEntryActivity extends AppCompatActivity {

    //Shared preferences
    private static SharedPreferences pref;
    private static final String TAG = LogEntryActivity.class.getName();
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
    public static  int cnt;

    @SuppressLint("StaticFieldLeak")
    private static View rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_entry);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.caller_support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.caller_support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //
        //Re-initialize static edit texts
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

        //
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            sams_name = firebaseUser.getDisplayName();
        }
        //
        //Shared preferences
        pref = getSharedPreferences("MyPref", 0); // 0 - for private mode


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.submit_caller_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        //

                        //Get values
                        //
                        EditText name_et = (EditText) rView.findViewById(R.id.cd_name_val);
                        EditText age_et = (EditText) rView.findViewById(R.id.cd_age_val);
                        EditText time_hours_et =(EditText) rView.findViewById(R.id.cd_time_hours);
                        EditText time_mins_et = (EditText) rView.findViewById(R.id.cd_time_mins);
                        EditText duration_hours_et =(EditText) rView.findViewById(R.id.cd_duration_hours_val);
                        EditText duration_mins_et = (EditText) rView.findViewById(R.id.cd_duration_mins_val);
                        EditText plan_et = (EditText) rView.findViewById(R.id.cd_plan_val);
                        EditText attempt_et = (EditText) rView.findViewById(R.id.cd_attempt_val);
                        //
                        EditText gist_et = (EditText) findViewById(R.id.cd_gist_et);
                        EditText feelings_et = (EditText) findViewById(R.id.cd_feelings_addressed_et);
                        EditText volunteers_response_et = (EditText) findViewById(R.id.cd_volunteers_response_et);
                        EditText call_end_et = (EditText) findViewById(R.id.cd_call_end_et);
                        //
                        EditText sams_leader_name_et = (EditText) rView.findViewById(R.id.sams_leader_name_val);
                        EditText leader_msg_et = (EditText) rView.findViewById(R.id.sams_leader_msg_val);
                        EditText pseudo_name_et = (EditText) rView.findViewById(R.id.sams_pseudo_name_val);
                        //

                        //
                        //1st tab
                        if(time_hours_et.hasFocus())
                            time_hours_et.clearFocus();
                        if(time_mins_et.hasFocus())
                            time_mins_et.clearFocus();
                        if(duration_hours_et.hasFocus())
                            duration_hours_et.clearFocus();
                        if(duration_mins_et.hasFocus())
                            duration_mins_et.clearFocus();
                        if(plan_et.hasFocus())
                            plan_et.clearFocus();
                        if(attempt_et.hasFocus())
                            attempt_et.clearFocus();
                        if(age_et.hasFocus())
                            age_et.clearFocus();
                        if(name_et.hasFocus())
                            name_et.clearFocus();
                        //
                        //2nd tab
                        if(gist_et.hasFocus())
                            gist_et.clearFocus();
                        if(call_end_et.hasFocus())
                            call_end_et.clearFocus();
                        if(volunteers_response_et.hasFocus())
                            volunteers_response_et.clearFocus();
                        if(feelings_et.hasFocus())
                            feelings_et.clearFocus();
                        //
                        //3rd tab
                        if(pseudo_name_et.hasFocus())
                            pseudo_name_et.clearFocus();
                        if(sams_leader_name_et.hasFocus())
                            sams_leader_name_et.clearFocus();
                        if(leader_msg_et.hasFocus())
                            leader_msg_et.clearFocus();
                        //
                        //Log.d(TAG,"temp_et_in");

                        //
                        cnt = pref.getInt("count",0);
                        cnt++;
                        //
                        //Firebase Database
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("log");
                        //LogEntry logEntry = new LogEntry(cnt,timestamp,gist,sams_name,);
                        String time = hours+":"+mins+" "+ampm;
                        String duration = duration_hours+" hours "+duration_mins+" mins";
                        LogEntry logEntry = new  LogEntry(cnt,timestamp,gist,sams_name,gender,tel_door,new_old,suicide_qn,risk_level,problem_nature,leader_name,pseudo_name,leader_spl_msg,name,age, caller_support,occupation,self_assessment,PU_referred,feelings_addressed,volunteers_response,call_end,time,duration,language,frequent_caller,plan,attempt);
                        final String child_sr_no = String.valueOf(cnt);
                        ref.child(child_sr_no).setValue(logEntry);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DatabaseReference ref_count = FirebaseDatabase.getInstance().getReference("count");
                                ref_count.setValue(child_sr_no);
                                ref_count.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

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
            rView =rootView;
            // TextViews and Values
            TextView tv_cd_date_val, tv_cd_sams_name_val;

            //Layouts
            LinearLayout tab1,tab2,tab3;

            //

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

            //
            //Spinner for Nature of Problem
            Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_cd_problem_nature_val);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.nature_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
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
            //Spinner for Time
            //
            //AMPM
            Spinner spinner_ampm = (Spinner) rootView.findViewById(R.id.spinner_cd_ampm);
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

            final EditText hours_et = (EditText) rootView.findViewById(R.id.cd_time_hours);
            hours_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    hours = hours_et.getText().toString();
                }
            });
            final EditText mins_et = (EditText) rootView.findViewById(R.id.cd_time_mins);
            mins_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    mins = mins_et.getText().toString();
                }
            });
            final EditText hours_duration_et = (EditText) rootView.findViewById(R.id.cd_duration_hours_val);
            hours_duration_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    duration_hours= hours_duration_et.getText().toString();
                }
            });
            final EditText mins_duration_et = (EditText) rootView.findViewById(R.id.cd_duration_mins_val);
            mins_duration_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    duration_mins= mins_duration_et.getText().toString();
                }
            });
            final EditText name_et = (EditText) rootView.findViewById(R.id.cd_name_val);
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
            final EditText age_et = (EditText) rootView.findViewById(R.id.cd_age_val);
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
            final EditText plan_et = (EditText) rootView.findViewById(R.id.cd_plan_val);
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
            final EditText attempt_et = (EditText) rootView.findViewById(R.id.cd_attempt_val);
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
            final EditText pseudo_name_et = (EditText) rootView.findViewById(R.id.sams_pseudo_name_val);
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
            final EditText leader_name_et = (EditText) rootView.findViewById(R.id.sams_leader_name_val);
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
            final EditText leader_msg_et = (EditText) rootView.findViewById(R.id.sams_leader_msg_val);
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
            //for tab2
            final EditText gist_et = (EditText) rootView.findViewById(R.id.cd_gist_et);
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
            final EditText feelings_addressed_et = (EditText) rootView.findViewById(R.id.cd_feelings_addressed_et);
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
            final EditText volunteers_response_et = (EditText) rootView.findViewById(R.id.cd_volunteers_response_et);
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
            final EditText call_end_et = (EditText) rootView.findViewById(R.id.cd_call_end_et);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
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
                    new_old = ((RadioButton) view).getText().toString();
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
