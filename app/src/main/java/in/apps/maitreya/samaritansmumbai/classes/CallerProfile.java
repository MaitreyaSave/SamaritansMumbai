package in.apps.maitreya.samaritansmumbai.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Maitreya on 2/18/2018.
 *
 */

public class CallerProfile implements Serializable{
    private String name,age,common_identifiers,support_system,occupation,health_issues,call_frequency,suicide_attempts,call_gist;
    private List<Call_Log> call_logs;

    public CallerProfile(){
        name="N/A";
    }

    public CallerProfile(String name, String age, String common_identifiers, String support_system, String occupation, String health_issues, String call_frequency, String suicide_attempts, String call_gist) {
        this.name = name;
        this.age = age;
        this.common_identifiers = common_identifiers;
        this.support_system = support_system;
        this.occupation = occupation;
        this.health_issues = health_issues;
        this.call_frequency = call_frequency;
        this.suicide_attempts = suicide_attempts;
        this.call_gist = call_gist;
    }

    public void setCall_logs(List<Call_Log> call_logs) {
        this.call_logs = call_logs;
    }

    public List<Call_Log> getCall_logs() {
        return call_logs;
    }

    public void addCallLog(Call_Log call_log){
        call_logs.add(call_log);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCommon_identifiers() {
        return common_identifiers;
    }

    public void setCommon_identifiers(String common_identifiers) {
        this.common_identifiers = common_identifiers;
    }

    public String getSupport_system() {
        return support_system;
    }

    public void setSupport_system(String support_system) {
        this.support_system = support_system;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHealth_issues() {
        return health_issues;
    }

    public void setHealth_issues(String health_issues) {
        this.health_issues = health_issues;
    }

    public String getCall_frequency() {
        return call_frequency;
    }

    public void setCall_frequency(String call_frequency) {
        this.call_frequency = call_frequency;
    }

    public String getSuicide_attempts() {
        return suicide_attempts;
    }

    public void setSuicide_attempts(String suicide_attempts) {
        this.suicide_attempts = suicide_attempts;
    }

    public String getCall_gist() {
        return call_gist;
    }

    public void setCall_gist(String call_gist) {
        this.call_gist = call_gist;
    }
}
