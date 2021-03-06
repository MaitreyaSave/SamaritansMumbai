package in.apps.maitreya.samaritansmumbai.classes;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maitreya on 1/19/2018.
 *
 */

public class User {
    private String role, email, username, uid, shift_day,shift_time, created_by, time_created;
    private int countA;
    private long timestamp;

    public User(String username, String email, String role, String shift_day, String shift_time, String created_by){
        this.username = username;
        this.email = email;
        this.role = role;
        this.shift_day=shift_day;
        this.shift_time=shift_time;
        this.created_by=created_by;
        countA=0;
    }

    public User() {
        countA=0;
    }

    public int getCountA() {
        return countA;
    }

    public void setCountA(int countA) {
        this.countA = countA;
    }

    public String getShift_day() {
        return shift_day;
    }

    public void setShift_day(String shift_day) {
        this.shift_day = shift_day;
    }

    public String getShift_time() {
        return shift_time;
    }

    public void setShift_time(String shift_time) {
        this.shift_time = shift_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("email", email);
        result.put("role", role);
        result.put("time_stamp", timestamp);

        return result;
    }
}
