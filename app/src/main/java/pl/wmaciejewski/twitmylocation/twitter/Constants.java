package pl.wmaciejewski.twitmylocation.twitter;

/**
 * Created by w.maciejewski on 2014-11-12.
 */
public class Constants {
    public static String CONSUMER_KEY = "My_Key";
    public static String CONSUMER_SECRET = "My_Key";

    public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
    public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";

    public static final String	OAUTH_CALLBACK_SCHEME	= "wojtekApp";
    public static final String	OAUTH_CALLBACK_HOST		= "callback";
    public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;


}
