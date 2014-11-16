package pl.wmaciejewski.twitmylocation.twitter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.net.URL;

import twitter4j.User;

/**
 * Created by Wojtek on 2014-11-16.
 */
public class TwitterUser {
    private Long id;
    private String photo;
    private String name;

    public TwitterUser(User user){

        photo= user.getProfileImageURL();
        id=user.getId();
        name=user.getName();

    }

    public TwitterUser() {
        photo=null;
        id=(long)0;
        name="annonymus";
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
