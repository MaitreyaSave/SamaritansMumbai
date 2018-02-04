package in.apps.maitreya.samaritansmumbai;

import java.io.Serializable;

/**
 * Created by Maitreya on 1/21/2018.
 *
 */

public class LogEntry implements Serializable{
    private String date,gist,sams_name,gender,tel_door,new_old,suicide_qn,risk_level,problem_nature,leader_name,pseudo_name,leader_spl_msg,name,age,support,occupation,self_assessment,PU_referred,feelings_addressed,volunteers_response,call_end,time,duration,language,frequent_caller,plan,attempt;
    private int sr_no;

    LogEntry(){
        sr_no = -1;
    }

    LogEntry(int sr_no,String date,String gist,String sams_name, String gender,String tel_door,String new_old,String suicide_qn,String risk_level,String problem_nature, String leader_name, String pseudo_name, String leader_spl_msg, String name, String age, String support, String occupation, String self_assessment,String PU_referred, String feelings_addressed, String volunteers_response, String call_end, String time, String duration, String language, String frequent_caller, String plan, String attempt){
        this.sr_no=sr_no;
        this.date=date;
        this.sams_name=sams_name;
        this.gist=gist;
        this.gender=gender;
        this.tel_door=tel_door;
        this.new_old=new_old;
        this.suicide_qn=suicide_qn;
        this.risk_level=risk_level;
        this.problem_nature=problem_nature;
        this.leader_name=leader_name;
        this.pseudo_name=pseudo_name;
        this.leader_spl_msg=leader_spl_msg;
        this.name=name;
        this.age=age;
        this.support=support;
        this.occupation=occupation;
        this.self_assessment=self_assessment;
        this.PU_referred=PU_referred;
        this.feelings_addressed=feelings_addressed;
        this.volunteers_response=volunteers_response;
        this.call_end=call_end;
        this.time=time;
        this.duration=duration;
        this.language=language;
        this.frequent_caller=frequent_caller;
        this.plan=plan;
        this.attempt=attempt;
    }
    String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String getGist() {
        return gist;
    }

    public void setGist(String gist) {
        this.gist = gist;
    }

    String getSams_name() {
        return sams_name;
    }

    public void setSams_name(String sams_name) {
        this.sams_name = sams_name;
    }

    int getSr_no() {
        return sr_no;
    }

    public void setSr_no(int sr_no) {
        this.sr_no = sr_no;
    }

    String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    String getTel_door() {
        return tel_door;
    }

    public void setTel_door(String tel_door) {
        this.tel_door = tel_door;
    }

    String getNew_old() {
        return new_old;
    }

    public void setNew_old(String new_old) {
        this.new_old = new_old;
    }

    String getSuicide_qn() {
        return suicide_qn;
    }

    public void setSuicide_qn(String suicide_qn) {
        this.suicide_qn = suicide_qn;
    }

    String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    String getProblem_nature() {
        return problem_nature;
    }

    public void setProblem_nature(String problem_nature) {
        this.problem_nature = problem_nature;
    }

    String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    String getPseudo_name() {
        return pseudo_name;
    }

    public void setPseudo_name(String pseudo_name) {
        this.pseudo_name = pseudo_name;
    }

    String getLeader_spl_msg() {
        return leader_spl_msg;
    }

    public void setLeader_spl_msg(String leader_spl_msg) {
        this.leader_spl_msg = leader_spl_msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    String getSelf_assessment() {
        return self_assessment;
    }

    public void setSelf_assessment(String self_assessment) {
        this.self_assessment = self_assessment;
    }

    String getPU_referred() {
        return PU_referred;
    }

    public void setPU_referred(String PU_referred) {
        this.PU_referred = PU_referred;
    }

    String getFeelings_addressed() {
        return feelings_addressed;
    }

    public void setFeelings_addressed(String feelings_addressed) {
        this.feelings_addressed = feelings_addressed;
    }

    String getVolunteers_response() {
        return volunteers_response;
    }

    public void setVolunteers_response(String volunteers_response) {
        this.volunteers_response = volunteers_response;
    }

    String getCall_end() {
        return call_end;
    }

    public void setCall_end(String call_end) {
        this.call_end = call_end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String getFrequent_caller() {
        return frequent_caller;
    }

    public void setFrequent_caller(String frequent_caller) {
        this.frequent_caller = frequent_caller;
    }

    String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }
}
