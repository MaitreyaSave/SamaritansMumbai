package in.apps.maitreya.samaritansmumbai.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.LogEntry;

public class ViewSingleLogActivity extends AppCompatActivity {
    TextView sams_name,date,sams_pseudo_name,sr_no,time,duration,leader_name,leader_msg,suicide_qn,refer_PU,self_assessment,caller_name,age,gender,tel_door,new_old,language,frequent_caller,caller_support,occupation,problem_nature,risk_level,plan,attempt,gist,feelings_addressed,volunteers_response,call_end, secondary_nature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_log);
        LogEntry logEntry = (LogEntry) getIntent().getSerializableExtra("LogEntry");
        //
        //Initialize text views
        sams_name = findViewById(R.id.single_log_sams_name);
        date = findViewById(R.id.single_log_date);
        sams_pseudo_name = findViewById(R.id.single_log_pseudo_name);
        sr_no = findViewById(R.id.single_log_srno);
        time = findViewById(R.id.single_log_time);
        duration = findViewById(R.id.single_log_duration);
        leader_name = findViewById(R.id.single_log_leader_name);
        leader_msg = findViewById(R.id.single_log_leader_msg);
        suicide_qn = findViewById(R.id.single_log_suicide_qn);
        refer_PU = findViewById(R.id.single_log_PU_referred);
        self_assessment = findViewById(R.id.single_log_self_assessment);
        caller_name = findViewById(R.id.single_log_caller_name);
        age = findViewById(R.id.single_log_age);
        gender = findViewById(R.id.single_log_gender);
        new_old = findViewById(R.id.single_log_old_new);
        frequent_caller = findViewById(R.id.single_log_frequent_caller);
        tel_door = findViewById(R.id.single_log_tel_door);
        language = findViewById(R.id.single_log_language);
        occupation = findViewById(R.id.single_log_occupation);
        caller_support = findViewById(R.id.single_log_support);
        problem_nature = findViewById(R.id.single_log_problem_nature);
        risk_level = findViewById(R.id.single_log_risk_level);
        attempt = findViewById(R.id.single_log_attempt);
        plan = findViewById(R.id.single_log_plan);
        gist = findViewById(R.id.single_log_gist);
        feelings_addressed = findViewById(R.id.single_log_feelings_addressed);
        volunteers_response = findViewById(R.id.single_log_volunteers_response);
        call_end = findViewById(R.id.single_log_call_end);
        secondary_nature = findViewById(R.id.single_log_problem_nature_secondary);

        //
        //set values to text views
        date.setText(logEntry.getDate());
        String temp = "<b>"+logEntry.getSr_no()+"</b>";
        sr_no.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_sam_name) +"</b> "+ logEntry.getSams_name();
        sams_name.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_pseudo_name) +"</b> "+ logEntry.getPseudo_name();
        sams_pseudo_name.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_sam_leader)+ "</b> "+ logEntry.getLeader_name();
        leader_name.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_sam_leader_msg) +"</b> "+ logEntry.getLeader_spl_msg();
        leader_msg.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_time) +"</b> "+ logEntry.getTime();
        time.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_duration) +"</b> "+ logEntry.getDuration();
        duration.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_suicide_qn) +"</b> "+ logEntry.getSuicide_qn();
        suicide_qn.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_refer_PU) +"</b> "+ logEntry.getPU_referred();
        refer_PU.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_self_assessment) +"</b> "+ logEntry.getSelf_assessment();
        self_assessment.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_caller_name) +"</b> "+ logEntry.getName();
        caller_name.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_caller_age) +" "+ logEntry.getAge();
        age.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_male_female) +"</b> "+ logEntry.getGender();
        gender.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_new_old) +"</b> "+ logEntry.getNew_old();
        new_old.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_frequent_caller) +"</b> "+ logEntry.getFrequent_caller();
        frequent_caller.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_tel_door) +"</b> "+ logEntry.getTel_door();
        tel_door.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_language) +"</b> "+ logEntry.getLanguage();
        language.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_caller_occupation) +"</b> "+ logEntry.getOccupation();
        occupation.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_caller_support) +"</b> "+ logEntry.getSupport();
        caller_support.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_nature_problem) +"</b> "+ logEntry.getProblem_nature();
        problem_nature.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_nature_problem_secondary_view_single) +"</b> "+ logEntry.getProblem_nature_secondary();
        secondary_nature.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_risk_level) +"</b> "+ logEntry.getRisk_level();
        risk_level.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_attempt) +"</b> "+ logEntry.getAttempt();
        attempt.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_plan) +"</b> "+ logEntry.getPlan();
        plan.setText(Html.fromHtml(temp));

        temp = "<b>"+getResources().getString(R.string.cd_gist) +"</b> "+ logEntry.getGist();
        gist.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_feelings) +"</b> "+ logEntry.getFeelings_addressed();
        feelings_addressed.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_vol_response) +"</b> "+ logEntry.getVolunteers_response();
        volunteers_response.setText(Html.fromHtml(temp));
        temp = "<b>"+getResources().getString(R.string.cd_call_end) +"</b> "+ logEntry.getCall_end();
        call_end.setText(Html.fromHtml(temp));
        //

    }
}
