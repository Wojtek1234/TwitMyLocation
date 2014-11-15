package pl.wmaciejewski.twitmylocation.twitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pl.wmaciejewski.twitmylocation.R;

/**
 * Created by w.maciejewski on 2014-11-14.
 */

public class WebViewActivity extends Activity {

    private WebView webView;

    public static String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_view_activity);

        setTitle("Login");

        final String url = this.getIntent().getStringExtra(EXTRA_URL);
        if (null == url) {
            Log.e("Twitter", "URL cannot be null");
            finish();
        }


        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(false);


        webView = (WebView) findViewById(R.id.webView);
        WebSettings ws = webView.getSettings();
        ws.setSaveFormData(false);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains(Constants.OAUTH_CALLBACK_URL)) {
                Uri uri = Uri.parse(url);

                    String verifier = uri.getQueryParameter(Constants.VERIFIER);
                    if(verifier!=null) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Constants.VERIFIER, verifier);
                        setResult(RequestTokenActivity.WEBVIEW_REQUEST_LOGGED, resultIntent);

                        finish();
                        return true;
                    }else{
                        Intent resultIntent = new Intent();
                        setResult(RequestTokenActivity.WEBVIEW_REQUEST_NOTLOGGED, resultIntent);
                        finish();
                        return true;
                    }

            }
            return false;
        }
    }
}