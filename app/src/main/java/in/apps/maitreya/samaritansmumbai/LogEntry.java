package in.apps.maitreya.samaritansmumbai;

/**
 * Created by Maitreya on 1/21/2018.
 */

public class LogEntry {
    String date,gist,sams_name;
    private int sr_no;

    LogEntry(){
        sr_no = -1;
    }

    LogEntry(int sr_no,String date,String gist,String sams_name){
        this.sr_no=sr_no;
        this.date=date;
        this.sams_name=sams_name;
        this.gist=gist;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGist() {
        return gist;
    }

    public void setGist(String gist) {
        this.gist = gist;
    }

    public String getSams_name() {
        return sams_name;
    }

    public void setSams_name(String sams_name) {
        this.sams_name = sams_name;
    }

    public int getSr_no() {
        return sr_no;
    }

    public void setSr_no(int sr_no) {
        this.sr_no = sr_no;
    }
}
