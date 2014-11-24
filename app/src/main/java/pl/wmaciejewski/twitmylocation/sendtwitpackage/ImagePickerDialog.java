package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;

import pl.wmaciejewski.twitmylocation.R;


/**
 * Created by Wojtek on 2014-10-31.
 */
public class ImagePickerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.pick_image_source)
                .setPositiveButton(R.string.from_gallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onGalleryButtonClick(ImagePickerDialog.this);
                    }
                })
                .setNegativeButton(R.string.from_camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onCameraButtonClick(ImagePickerDialog.this);
                    }
                }
                );

        final AlertDialog dialog=builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // if you do the following it will be left aligned, doesn't look correct
                // button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);

                Drawable drawable = getActivity().getResources().getDrawable(R.drawable.ic_action_gallery);

                // set the bounds to place the drawable a bit right
                drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() ), drawable.getIntrinsicHeight());
                button.setCompoundDrawables(drawable, null, null, null);

                Button button2 = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                // if you do the following it will be left aligned, doesn't look correct
                // button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);

                Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.ic_action_camera);

                // set the bounds to place the drawable a bit right
                drawable2.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() ), drawable.getIntrinsicHeight());
                button2.setCompoundDrawables(drawable, null, null, null);
            }
        });
        return dialog;
    }

    public interface ImagePickerListener {
        public void onGalleryButtonClick(DialogFragment dialog);
        public void onCameraButtonClick(DialogFragment dialog);
    }


    ImagePickerListener mListener;

    public void setmListener(ImagePickerListener mListener){
        this.mListener=mListener;
    }
}
