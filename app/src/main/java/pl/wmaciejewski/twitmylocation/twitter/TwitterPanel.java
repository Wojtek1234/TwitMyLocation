package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class TwitterPanel  {
    private Button logginButton,findHashTagButton,tweetLocationButton;
    private TwitterUtils twitterUtils=TwitterUtils.getInstance();
    private TwitterListener twitterListener;
    private LinearLayout view;
    private boolean logged=false;


    public TwitterPanel(LinearLayout view, SharedPreferences prefs){
        login(prefs);
        logginButton=(Button)view.findViewById(R.id.loginTwitButton);
        logginButton.setEnabled(false);
        logginButton.setOnClickListener(new LoginButOnClick());
        //twitterUtils.addObserver(this);
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

    public void setOnTwittListener(TwitterListener twitterInterface) {
        this.twitterListener=twitterInterface;
    }

    public void setLogged(boolean logged) {
        twitterUtils.setLogged(logged);
    }


    public void login(SharedPreferences prefs) {
            twitterUtils.authenticat(prefs);
    }

  /*  @Override
    public void update(Observable observable, Object data) {
        logginButton.setEnabled(true);
        setLoginButtonText(view);
    }*/

    @Subscribe
    public void answerAvailable(MessageLogin event) {
        logginButton.setEnabled(true);
        logged=event.isLogginStatus();
        setLoginButtonText(view);

    }

    public interface TwitterListener{
        public void onLogingDemand(Intent loggingIntent);
        public void onLogOutDemand();
        public void onFindHashTag(Intent hashTagIntent);
    }
    public boolean isLogged() {
        return twitterUtils.isLogged();
    }

    private Intent createLoginIntent(){
        return new Intent(this.view.getContext(),RequestTokenActivity.class);
    }



    private class LoginButOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(twitterUtils.isLogged()){
                twitterListener.onLogOutDemand();

            }else{
                twitterListener.onLogingDemand(createLoginIntent());

            }
            setLoginButtonText(view);
        }
    }
}
