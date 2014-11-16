package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.TwitterUtils;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Created by Wojtek on 2014-11-16.
 */
public class CreateMarkerIcon  implements Observer{
    private View view;
public CreateMarkerIcon(View view){
    this.view=view;
    TwitterUtils.getInstance().addObserver(this);
}
public Bitmap createIcon(){
    if(TwitterUtils.getInstance().isLogged()){
        return doOnLoggedInstance(view);

    }else{
        return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
    }
}

    private Bitmap doOnLoggedInstance(View view) {
        User user = null;
        try {
            user = getUser();
        } catch (TwitterException e) {
            return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
        }
        return tryGetBitmapByPicassa(view, user);
    }

    private Bitmap tryGetBitmapByPicassa(View view, User user) {
        try {
            return getBitmap(view, user);
        } catch (IOException e) {
            return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
        }
    }

    private Bitmap getBitmap(View view, User user) throws IOException {
        Bitmap bitmap= Picasso.with(view.getContext())
                .load(user.getProfileImageURL())
                .get();
        return bitmap;
    }

    private User getUser() throws TwitterException {
        Twitter twitter= TwitterUtils.getInstance().getTwitter();
        return twitter.showUser(twitter.getScreenName());
    }

    @Override
    public void update(Observable observable, Object data) {
        createIcon();
    }
}
