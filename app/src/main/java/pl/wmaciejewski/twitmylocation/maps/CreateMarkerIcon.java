package pl.wmaciejewski.twitmylocation.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.annotation.Target;
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
public class CreateMarkerIcon  {

public Bitmap createIcon(View view){
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
            return tryGetBitmapByPicassa(view, user);
        } catch (TwitterException e) {
            return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
        }

    }

    private Bitmap tryGetBitmapByPicassa(View view, User user) {
        try {
            return getBitmap(view, user);
        } catch (IOException e) {
            return BitmapFactory.decodeResource(view.getResources(), R.drawable.zabka);
        }
    }

    private Bitmap getBitmap(View view, User user) throws IOException {
        final Bitmap[] bitmap123 = new Bitmap[1];
        com.squareup.picasso.Target target=new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                bitmap123[0] =bitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(view.getContext())
                .load(user.getProfileImageURL())
                .into(target);
        return bitmap123[0];
    }

    private User getUser() throws TwitterException {

        return TwitterUtils.getInstance().getUser() ;
    }


}
