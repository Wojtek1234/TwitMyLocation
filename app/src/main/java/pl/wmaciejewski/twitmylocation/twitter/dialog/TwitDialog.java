package pl.wmaciejewski.twitmylocation.twitter.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.senders.SendTwit;
import twitter4j.TwitterException;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public class TwitDialog extends DialogFragment {
    public static final String TWIT_USERNAME="username";
    public static final String TWIT_DATE="date";
    public static final String TWIT_IMAGEURL="image_url";
    public static final String TWIT_TEXT="twit_text";
    public static final String TWIT_ID="status_id";
    public static final String TWIT_IMAGE="image";

    @InjectView(R.id.imageUserdialog)ImageView imageView;
    @InjectView(R.id.userNameTextdialog)TextView  userTextView;
    @InjectView(R.id.dateOfTwitDialog)TextView  dateTextView;
    @InjectView(R.id.twitEditText)TextView twitEditText;
    @InjectView(R.id.retweetButton)ImageButton retweetButton;
    @InjectView(R.id.replayButton)ImageButton replayButton;
    @InjectView(R.id.imageFromTwit)ImageView imageFromVTwit;
    private long status_id;






    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.single_twit_dialog, null);
        ButterKnife.inject(this,view);
        builder.setView(view);
        fillFromBundle(savedInstanceState);

        Drawable d = new ColorDrawable(Color.GRAY);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.getWindow().setContentView(view);

        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);

        return dialog;

    }

    @OnClick(R.id.retweetButton)
    public void clickRetweet(){
        SendTwit sendTwit=new SendTwit();
        try {
            sendTwit.retwit(status_id);
        } catch (TwitterException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Faild to retweet",Toast.LENGTH_SHORT).show();
        }finally {
            dismiss();
        }
    }

    @OnClick(R.id.replayButton)
    public void clickReplay(){


    }

    private void fillFromBundle(Bundle bundle) {
        Picasso.with(getActivity()).load(bundle.getString(TWIT_IMAGEURL)).into(imageView);
        userTextView.setText(bundle.getString(TWIT_USERNAME));
        dateTextView.setText(bundle.getString(TWIT_DATE));
        twitEditText.setText(bundle.getString(TWIT_TEXT));
        if(bundle.getString(TWIT_IMAGE)!=null)   Picasso.with(getActivity()).load(bundle.getString(TWIT_IMAGE)).into(imageFromVTwit);
        status_id=bundle.getLong(TWIT_ID);
    }


}
