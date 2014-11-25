package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.twitter.SendTwit;
import twitter4j.TwitterException;

public class SendTwitActivity extends FragmentActivity implements ImagePickerDialog.ImagePickerListener {
    private static final int SELECT_PHOTO_GALLERY = 100;
    private static final int SELECT_PHOTO_CAPTURE = 101;
    public static final String BITMAP_PROPOERTY="bitmap";
    public static final String NAME_PROPOERTY="name";
    public static final String LATITUDE_PROPERTY="latitude";
    public static final String LONGITUDE_PROPERTY="longitude";


    private LatLng latLng;
    private String user_name;
    private Bitmap bitmap;

    @InjectView(R.id.sendTwitButton)ImageButton sendTwitButton;
    @InjectView(R.id.cancelSendingTwit)ImageButton cancelTwitingButton;
    @InjectView(R.id.pickPhotoToTwit)ImageButton pickPhoto;
    @InjectView(R.id.sendTwitText)EditText sendTwitEdit;
    @InjectView(R.id.sendTwitProfileIcon)ImageView imageView;
    private Uri selectedImageUri;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_twit);
        ButterKnife.inject(this);

        sendTwitEdit.addTextChangedListener(new TextChangeListener(sendTwitEdit));
        sendTwitEdit.setText(getResources().getString(R.string.programHashTag));
        if(savedInstanceState!=null)setUpFromBundle( savedInstanceState);
        else  setUpFromBundle(getIntent().getExtras());
        imageView.setImageBitmap(bitmap);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState=SetUpBundle.setBundle(latLng,bitmap,user_name);
        super.onSaveInstanceState(outState);
    }

    private void setUpFromBundle(Bundle bundle) {
        byte[] byteArray =bundle.getByteArray(BITMAP_PROPOERTY);
        bitmap= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        user_name=bundle.getString(NAME_PROPOERTY);
        latLng=new LatLng(bundle.getDouble(LATITUDE_PROPERTY),bundle.getDouble(LONGITUDE_PROPERTY));
    }


    @OnClick(R.id.sendTwitButton)
    public void doOnSend(){
        SendTwit sendTwit=new SendTwit();
        if(file!=null){
            try {
                sendTwit.sendTwit(sendTwitEdit.getText().toString(),file,latLng);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }else{
            try {
                sendTwit.sendTwit(sendTwitEdit.getText().toString(),latLng);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    @OnClick(R.id.pickPhotoToTwit)
    public void getImage() {
        ImagePickerDialog imagePickerDialog = new ImagePickerDialog();
        imagePickerDialog.setmListener(this);
        imagePickerDialog.show(getSupportFragmentManager(), getResources().getString(R.string.pick_image_source));
    }

    @OnClick(R.id.cancelSendingTwit)
    public void cancel(){
        finish();
    }


    @Override
    public void onGalleryButtonClick(DialogFragment dialog) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO_GALLERY);
    }

    @Override
    public void onCameraButtonClick(DialogFragment dialog) {
        startCamera();
    }

    public void startCamera() {
        File photo;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            CreatePhotoFile createPhotoFile=new CreatePhotoFile(user_name,getCacheDir());
            photo = createPhotoFile.getFile();
            startCatchCamerIntent(photo, intent);
        } catch (CouldntCreatePhoto couldntCreatePhoto) {
            createDialogNoPhoto(couldntCreatePhoto.getMessage());
            return;
        }
    }
    private void startCatchCamerIntent(File photo, Intent intent) {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        selectedImageUri = Uri.fromFile(photo);
        startActivityForResult(intent, SELECT_PHOTO_CAPTURE);
    }

    private AlertDialog.Builder createDialogNoPhoto(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setTitle(getResources().getString(R.string.no_photo_warn));
        alertDialogBuilder.setMessage(message);
        return alertDialogBuilder;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO_GALLERY:
                if (resultCode == RESULT_OK) {
                    file=new File(getRealPathFromUri(imageReturnedIntent.getData()));
                }
                break;
            case SELECT_PHOTO_CAPTURE:
                if (resultCode == RESULT_OK) {
                    doOnUriRecived(selectedImageUri);
                }
                break;

        }
    }

    private void doOnUriRecived(Uri selectedImage) {
        file = new File(selectedImage.getPath());
    }

    private  String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
