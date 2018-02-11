package in.apps.maitreya.samaritansmumbai.classes;

/**
 * Created by Maitreya on 2/11/2018.
 *
 */

public class AttendaceLog {
    private String user_name,date,shift,time_in,time_out;

    public AttendaceLog(){

    }

    public AttendaceLog(String user_name,String date,String shift,String time_in,String time_out){
        this.user_name=user_name;
        this.date=date;
        this.shift=shift;
        this.time_in=time_in;
        this.time_out=time_out;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }
}
