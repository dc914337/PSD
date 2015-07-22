package anon.psd.models.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import anon.psd.models.PassItem;
import anon.psd.storage.FileWorker;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class PrettyPassword
{
    //appearance cfg data
    String picName;
    public ArrayList<Date> UsedDates = new ArrayList<Date>();
    String title;

    //real data
    transient PassItem passItem;
    transient Bitmap pic;

    transient final String picPostfix = ".pic";

    transient public static Bitmap _defaultPic;

    transient private static final int MAX_COMPRESS_QUALITY = 100;
    transient private static File picsDir;


    public PrettyPassword()
    {
        //empty constructor for json
    }

    public void setPicsDir(File value)
    {
        picsDir = value;
    }

    public PrettyPassword(PassItem origPass)
    {
        setPassItem(origPass);

        //set default pic and path
        loadDefaultPic();
    }


    public void setPassItem(PassItem pass)
    {
        passItem = pass;
        title = pass.Title;
    }

    public PassItem getPassItem()
    {
        return passItem;
    }


    public boolean setPic(File newPic)
    {
        if (passItem == null)
            return false;//PasswordItem is null. You can't set pic to non existing password. I want to throw exception like this and not to handle it

        //get pic
        if (!loadPic(newPic)) {
            loadDefaultPic();
            return false;
        }
        //generate
        picName = String.valueOf(System.currentTimeMillis()) + picPostfix; //%timestamp%.pic
        //save to our dir
        return savePic();
    }

    private boolean loadPic(File newPic)
    {
        byte[] picBytes = FileWorker.readFromFile(newPic);
        if (picBytes == null)
            return false;
        //get bitmap
        pic = BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
        return true;
    }


    private void loadDefaultPic()
    {
        pic = _defaultPic;
        picName = null;
    }

    private boolean savePic()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, MAX_COMPRESS_QUALITY, stream);
        byte[] byteArray = stream.toByteArray();
        File picFile = new File(picsDir, picName);
        return FileWorker.writeFile(byteArray, picFile);
    }


    public static void setDefaultPic(Bitmap defaultPic)
    {
        _defaultPic = defaultPic;
    }

    public String getTitle()
    {
        if (passItem != null)
            return passItem.Title;
        else
            return title;
    }
}
