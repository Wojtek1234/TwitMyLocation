package pl.wmaciejewski.twitmylocation.twitter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import pl.wmaciejewski.twitmylocation.R;

/**
 * Created by w.maciejewski on 2014-11-20.
 */
public class FindHashTagDialog extends DialogFragment {
    private OnSearchHashTagListener listener;
    private EditText editText;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_hashtag, null);
        builder.setView(view);
        builder.setTitle(getResources().getString(R.string.find));
        editText=(EditText)view.findViewById(R.id.hashTagString);
        view.findViewById(R.id.startToSeatch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onSearchHashTag(editText.getText().toString());
                    dismiss();
                }
            }
        });
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

    public interface OnSearchHashTagListener{
        public void onSearchHashTag(String string);

    }

    public void attacheListener(OnSearchHashTagListener listener){
        this.listener=listener;
    }


}
