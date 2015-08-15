package anon.psd.utils;

/**
 * Created by Dmitry on 11.08.2015.
 */
public class ArraysUtils
{
    public static byte[] concatArrays(byte[]... arrays)
    {
        int resLength = countArraysLength(arrays);

        byte[] result = new byte[resLength];

        int position = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, position, arr.length);
            position += arr.length;
        }

        return result;
    }

    private static int countArraysLength(byte[]... arrays)
    {
        int resLength = 0;
        for (byte[] arr : arrays) {
            resLength += arr.length;
        }
        return resLength;
    }

    //compares all bytes in arr to prevent timing attack(i don't know if it is possible to do)
    //actually, i didn't find arrays.equals
    public static boolean safeCompare(byte[] arr1, byte[] arr2)
    {
        if (arr1 == null || arr2 == null)
            return false;
        if (arr1.length <= 0)
            return false;
        if (arr1.length != arr2.length)
            return false;

        boolean success = true;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                success = false;
        }
        return success;
    }
}