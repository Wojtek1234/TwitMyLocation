package pl.wmaciejewski.twitmylocation.sendtwitpackage;

import java.io.File;

/**
 * Created by w.maciejewski on 2014-11-03.
 */
public class CreatePhotoFile {

    private final String text;
    private final File catcheDir ;
    public CreatePhotoFile(String text, File catcheDir) {
        this.text = text;
        this.catcheDir = catcheDir;
    }

    public File getFile() throws CouldntCreatePhoto {
        return createPhotoFile();
    }

    private File createPhotoFile() throws CouldntCreatePhoto {
        File photo;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            photo = new File(android.os.Environment
                    .getExternalStorageDirectory(), text);
        } else {
            photo = new File(this.catcheDir, text);
        }
        if(photo==null){
            throw new CouldntCreatePhoto("");
        }
        return photo;
    }


}
