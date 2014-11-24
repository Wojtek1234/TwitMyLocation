package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

/**
 * Created by w.maciejewski on 2014-11-24.
 */
public class TextChangeListener implements TextWatcher{

    private final EditText editText;
    private boolean dodo=true;
    public  TextChangeListener(EditText editText){
        this.editText = editText;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(dodo) {

            String text = editable.toString();
            int first = text.indexOf("#");
            int last = text.indexOf(" ", first);
            Spannable wordtoSpan = new SpannableString(text);
            if(last==-1)last=first;
            if (first != -1)
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.argb(255, 1, 1, 180)), first, last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            first = text.indexOf("@");
            last = text.indexOf(" ", first);
            if(last==-1)last=first;
            if (first != -1)
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.argb(255, 0, 0, 180)), first, last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            dodo=false;
            this.editText.setText(wordtoSpan);


        }else{
             this.editText.setSelection(editText.getText().length());
            dodo=true;
        }


    }
}
