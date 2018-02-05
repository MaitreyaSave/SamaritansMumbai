package in.apps.maitreya.samaritansmumbai.classes;

import java.io.Serializable;

/**
 * Created by Maitreya on 2/2/2018.
 *
 */

public class NotificationMessage implements Serializable {
    private String title, message,creation_time;

    public NotificationMessage(){

    }
    public NotificationMessage(String title, String message,String creation_time){
        this.title=title;
        this.message=message;
        this.creation_time=creation_time;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
