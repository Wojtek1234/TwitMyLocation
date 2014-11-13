package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.exception.LoginFailException;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class TwitterPanel {
    private Button logginButton,findHashTagButton,tweetLocationButton;
    private TwitterUtils twitterUtils=TwitterUtils.getInstance();
    private TwitterListener twitterListener;
    private LinearLayout view;

    private boolean logged;

    public TwitterPanel(LinearLayout view, SharedPreferences prefs){
        tryToLogin(prefs);
        logginButton=(Button)view.findViewById(R.id.loginTwitButton);
        setLoginButtonText(view);
        logginButton.setOnClickListener(new LoginButOnClick());
        this.view=view;

    }

    private void setLoginButtonText(View view) {
        if(logged) logginButton.setText(view.getResources().getString(R.string.logoutTwitText));
        else logginButton.setText(view.getResources().getString(R.string.loginTwitText));

    }

   public void showPanel(){
       if(this.view.getVisibility()==View.GONE) this.view.setVisibility(View.VISIBLE);
       else this.view.setVisibility(View.GONE);

   }

    private void tryToLogin(SharedPreferences prefs) {
        try {
            twitterUtils.authenticat(prefs);
            logged=true;
        } catch (LoginFailException e) {
            logged=false;
        }
    }

    public void setOnTwittListener(TwitterListener twitterInterface) {
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void login(SharedPreferences prefs) {


    }

    public interface TwitterListener{
        public void onLogingDemand(Intent loggingIntent);
        public void onLogOutDemand();
        public void onFindHashTag(Intent hashTagIntent);
    }
    public boolean isLogged() {
        return logged;
    }

    private Intent createLoginIntent(){
        return new Intent(this.view.getContext(),RequestTokenActivity.class);
    }



    private class LoginButOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(logged){
                twitterListener.onLogOutDemand();
                logged=!logged;
            }else{
                twitterListener.onLogingDemand(createLoginIntent());
                logged=!logged;
            }
            setLoginButtonText(view);
        }
    }
}
