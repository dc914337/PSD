package anon.psd.crypto;

import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class HashProvider
{
    public static String base64Sha256String(String input)
    {
        return Base64.encodeToString(sha256Bytes(input.getBytes()), Base64.DEFAULT);
    }


    public static byte[] sha256Bytes(byte[] input)
    {
        MessageDigest mda = null;
        try {
            mda = MessageDigest.getInstance("SHA-256", "BC");
        } catch (Exception e) {
            Log.wtf(Constants.LTAG, e);
            e.printStackTrace();
            return null;
        }
        return mda.digest(input);
    }
}