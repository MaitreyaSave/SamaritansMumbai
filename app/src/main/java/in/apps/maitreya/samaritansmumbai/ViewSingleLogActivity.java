package in.apps.maitreya.samaritansmumbai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewSingleLogActivity extends AppCompatActivity {
    TextView sams_name,date,sams_pseudo_name,sr_no,time,duration,leader_name,leader_msg,suicide_qn,refer_PU,self_assessment,caller_name,age,gender,tel_door,new_old,language,frequent_caller,caller_support,occupation,problem_nature,risk_level,plan,attempt,gist,feelings_addressed,volunteers_response,call_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_log);
        LogEntry logEntry = (LogEntry) getIntent().getSerializableExtra("LogEntry");
        //
        //Initialize text views
        sams_name = (TextView) findViewById(R.id.single_log_sams_name);
        date = (TextView) findViewById(R.id.single_log_date);
        sams_pseudo_name = (TextView) findViewById(R.id.single_log_pseudo_name);
        sr_no = (TextView) findViewById(R.id.single_log_srno);
        time = (TextView) findViewById(R.id.single_log_time);
        duration = (TextView) findViewById(R.id.single_log_duration);
        leader_name = (TextView) findViewById(R.id.single_log_leader_name);
        leader_msg = (TextView) findViewById(R.id.single_log_leader_msg);
        suicide_qn = (TextView) findViewById(R.id.single_log_suicide_qn);
        refer_PU = (TextView) findViewById(R.id.single_log_PU_referred);
        self_assessment = (TextView) findViewById(R.id.single_log_self_assessment);
        caller_name = (TextView) findViewById(R.id.single_log_caller_name);
        age = (TextView) findViewById(R.id.single_log_age);
        gender = (TextView) findViewById(R.id.single_log_gender);
        new_old = (TextView) findViewById(R.id.single_log_old_new);
        frequent_caller = (TextView) findViewById(R.id.single_log_frequent_caller);
        tel_door = (TextView) findViewById(R.id.single_log_tel_door);
        language = (TextView) findViewById(R.id.single_log_language);
        occupation = (TextView) findViewById(R.id.single_log_occupation);
        caller_support = (TextView) findViewById(R.id.single_log_support);
        problem_nature = (TextView) findViewById(R.id.single_log_problem_nature);
        risk_level = (TextView) findViewById(R.id.single_log_risk_level);
        attempt = (TextView) findViewById(R.id.single_log_attempt);
        plan = (TextView) findViewById(R.id.single_log_plan);
        gist = (TextView) findViewById(R.id.single_log_gist);
        feelings_addressed = (TextView) findViewById(R.id.single_log_feelings_addressed);
        volunteers_response = (TextView) findViewById(R.id.single_log_volunteers_response);
        call_end = (TextView) findViewById(R.id.single_log_call_end);

        //
        //set values to text views
        date.setText(logEntry.getDate());
        String temp = logEntry.getSr_no()+"";
        sr_no.setText(temp);
        temp = getResources().getString(R.string.cd_sam_name) +" "+ logEntry.getSams_name();
        sams_name.setText(temp);
        temp = getResources().getString(R.string.cd_pseudo_name) +" "+ logEntry.getPseudo_name();
        sams_pseudo_name.setText(temp);
        temp = getResources().getString(R.string.cd_sam_leader)+ " "+ logEntry.getLeader_name();
        leader_name.setText(temp);
        temp = getResources().getString(R.string.cd_sam_leader_msg) +" "+ logEntry.getLeader_spl_msg();
        leader_msg.setText(temp);
        temp = getResources().getString(R.string.cd_time) +" "+ logEntry.getTime();
        time.setText(temp);
        temp = getResources().getString(R.string.cd_duration) +" "+ logEntry.getDuration();
        duration.setText(temp);
        temp = getResources().getString(R.string.cd_suicide_qn) +" "+ logEntry.getSuicide_qn();
        suicide_qn.setText(temp);
        temp = getResources().getString(R.string.cd_refer_PU) +" "+ logEntry.getPU_referred();
        refer_PU.setText(temp);
        temp = getResources().getString(R.string.cd_self_assessment) +" "+ logEntry.getSelf_assessment();
        self_assessment.setText(temp);
        temp = getResources().getString(R.string.cd_caller_name) +" "+ logEntry.getName();
        caller_name.setText(temp);
        temp = getResources().getString(R.string.cd_caller_age) +" "+ logEntry.getAge();
        age.setText(temp);
        temp = getResources().getString(R.string.cd_male_female) +" "+ logEntry.getGender();
        gender.setText(temp);
        temp = getResources().getString(R.string.cd_new_old) +" "+ logEntry.getNew_old();
        new_old.setText(temp);
        temp = getResources().getString(R.string.cd_frequent_caller) +" "+ logEntry.getFrequent_caller();
        frequent_caller.setText(temp);
        temp = getResources().getString(R.string.cd_tel_door) +" "+ logEntry.getTel_door();
        tel_door.setText(temp);
        temp = getResources().getString(R.string.cd_language) +" "+ logEntry.getLanguage();
        language.setText(temp);
        temp = getResources().getString(R.string.cd_caller_occupation) +" "+ logEntry.getOccupation();
        occupation.setText(temp);
        temp = getResources().getString(R.string.cd_caller_support) +" "+ logEntry.getSupport();
        caller_support.setText(temp);
        temp = getResources().getString(R.string.cd_nature_problem) +" "+ logEntry.getProblem_nature();
        problem_nature.setText(temp);
        temp = getResources().getString(R.string.cd_risk_level) +" "+ logEntry.getRisk_level();
        risk_level.setText(temp);
        temp = getResources().getString(R.string.cd_attempt) +" "+ logEntry.getAttempt();
        attempt.setText(temp);
        temp = getResources().getString(R.string.cd_plan) +" "+ logEntry.getPlan();
        plan.setText(temp);

        temp = getResources().getString(R.string.cd_gist) +" "+ logEntry.getGist();
        gist.setText(temp);
        temp = getResources().getString(R.string.cd_feelings) +" "+ logEntry.getFeelings_addressed();
        feelings_addressed.setText(temp);
        temp = getResources().getString(R.string.cd_vol_response) +" "+ logEntry.getVolunteers_response();
        volunteers_response.setText(temp);
        temp = getResources().getString(R.string.cd_call_end) +" "+ logEntry.getCall_end();
        call_end.setText(temp);
        //

    }
}
