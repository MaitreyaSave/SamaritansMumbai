package in.apps.maitreya.samaritansmumbai.classes;

import java.io.Serializable;

/**
 * Created by Maitreya on 2/18/2018.
 *
 */

public class Call_Log implements Serializable{
    private String date,new_info,ref_no,updates;

    public Call_Log(){
        ref_no="-1";
    }
    public  Call_Log(String date,String new_info,String ref_no, String updates){
        this.date=date;
        this.new_info=new_info;
        this.ref_no=ref_no;
        this.updates=updates;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNew_info() {
        return new_info;
    }

    public void setNew_info(String new_info) {
        this.new_info = new_info;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }
}
