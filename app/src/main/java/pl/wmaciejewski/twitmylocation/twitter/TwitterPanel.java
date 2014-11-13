package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class TwitterPanel {
    private Button logginButton,findHashTagButton,tweetLocationButton;

    public boolean isLogged() {
        return logged;
    }

    private boolean logged;

    public TwitterPanel(View view, SharedPreferences prefs){

    }

    public void setOnTwittListener(TwitterListener twitterInterface) {
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public interface TwitterListener{
        public void onLogingDemand(Intent loggingIntent);
        public void onLogOutDemand();
        public void onFindHashTag(Intent hashTagIntent);
    }
}
