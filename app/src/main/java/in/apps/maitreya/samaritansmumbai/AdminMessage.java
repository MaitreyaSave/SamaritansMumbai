package in.apps.maitreya.samaritansmumbai;

/**
 * Created by Maitreya on 2/2/2018.
 *
 */

public class AdminMessage {
    private String title, message;
    AdminMessage(String title,String message){
        this.title=title;
        this.message=message;
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
