package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

/**
 * Created by w.maciejewski on 2014-11-24.
 */
public class TextChangeListener implements TextWatcher{

    private final EditText editText;

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
        String text=editable.toString();

        int first=text.indexOf("#");
        int last=text.indexOf(" ",first);
        if(first!=-1) editable.setSpan(new ForegroundColorSpan(Color.argb(0,0,0,180)),first,last,0);

        first=text.indexOf("@");
        last=text.indexOf(" ",first);
        if(first!=-1) editable.setSpan(new ForegroundColorSpan(Color.argb(0,0,0,180)),first,last,0);

    }
}
