package pl.wmaciejewski.twitmylocation.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;

import java.util.List;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.ListOfStatusEvent;
import pl.wmaciejewski.twitmylocation.bus.MessageLogin;
import pl.wmaciejewski.twitmylocation.twitter.dialog.FindHashTagDialog;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 * Created by w.maciejewski on 2014-11-13.
 */
public class TwitterPanel  {
    private Button logginButton,findHashTagButton,twitLocationButton;
    private TwitterUtils twitterUtils=TwitterUtils.getInstance();
    private TwitterListener twitterListener;
    private LinearLayout view;
    private boolean logged=false;
    private FragmentManager fragmentManager;
    private FindHashTagDialog dialogFragment;


    public TwitterPanel(LinearLayout view, SharedPreferences prefs){
        login(prefs);
        logginButton=(Button)view.findViewById(R.id.loginTwitButton);
        logginButton.setEnabled(false);
        logginButton.setOnClickListener(new LoginButOnClick());
        twitLocationButton=(Button)view.findViewById(R.id.twitMyLocation);
        twitLocationButton.setOnClickListener(new TwitMyLocationListener());
        findHashTagButton=(Button)view.findViewById(R.id.findByHash);
        findHashTagButton.setOnClickListener(new FindHasTagListener());
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

    @Subscribe
    public void answerAvailable(MessageLogin event) {
        logginButton.setEnabled(true);
        logged=event.isLogginStatus();
        setLoginButtonText(view);
        twitLocationButton.setEnabled(event.isLogginStatus());
        findHashTagButton.setEnabled(event.isLogginStatus());
    }


    @Subscribe
    public void answerListStatys(ListOfStatusEvent listOfStatusEvent){
        if(twitterListener!=null) twitterListener.onFindHashTag(listOfStatusEvent.getStatusList());
    }


    public void setForDialog(FindHashTagDialog findHashTagDialog, FragmentManager supportFragmentManager) {
        dialogFragment=findHashTagDialog;
        fragmentManager=supportFragmentManager;
        dialogFragment.attacheListener(new DialogListener());
    }

    public interface TwitterListener{
        public void onLogingDemand(Intent loggingIntent);
        public void onLogOutDemand();
        public void onFindHashTag(List<Status> statusList);
        public void onTwitLocation(Twitter twitter);
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
                if(twitterListener!=null)  twitterListener.onLogOutDemand();
            }else{
                if(twitterListener!=null) twitterListener.onLogingDemand(createLoginIntent());
            }
            setLoginButtonText(view);
        }
    }


    private class TwitMyLocationListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(twitterListener!=null) twitterListener.onTwitLocation(twitterUtils.getTwitter());
        }
    }



    private class FindHasTagListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dialogFragment.show(fragmentManager,null);
        }
    }



    private class DialogListener implements FindHashTagDialog.OnSearchHashTagListener{

        @Override
        public void onSearchHashTag(String string) {
            twitterUtils.getListHashTags(string);
        }
    }
}
