package anon.psd.crypto.protocol;

import java.io.IOException;
import java.security.InvalidKeyException;

import anon.psd.crypto.KeyGenerator;
import anon.psd.global.Constants;
import anon.psd.models.Password;
import anon.psd.utils.ArrayUtils;
import anon.psd.utils.ShortUtils;

import static anon.psd.crypto.HashProvider.sha256Bytes;
import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdProtocolV1
{
    public byte[] btKey;
    public byte[] hBtKey;

    private byte[] nextBtKey;
    private byte[] nextHBtKey;


    public PsdProtocolV1(byte[] currBtKey, byte[] currHBtKey)
    {
        btKey = currBtKey;
        hBtKey = currHBtKey;
    }


    public byte[] generateSendPass(short index, byte[] passPart1Bytes)
    {
        //generate new keys
        nextBtKey = KeyGenerator.generateRandomBtKey();
        nextHBtKey = KeyGenerator.generateRandomHBtKey();

        Log(this, "[ PROTOCOL ] Generated message with keys");

        //do crypto
        byte[] tempMessagePayload = generateTempMessagePayload(index, passPart1Bytes, nextBtKey, nextHBtKey);
        byte[] message = new byte[0];
        try {
            message = new ProtocolCrypto(btKey, hBtKey).generateSignedEncryptedMessage(tempMessagePayload);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;//return null and don't switch keys
        } catch (IOException e) {
            e.printStackTrace();
            return null;//return null and don't switch keys
        }
        return message;
    }


    private byte[] generateTempMessagePayload(short index, byte[] passPart1Bytes, byte[] nextBtKey, byte[] nextHBtKey)
    {
        byte[] indexBytes = ShortUtils.getShortBytes(index);
        return ArrayUtils.concatArrays(indexBytes, passPart1Bytes, nextBtKey, nextHBtKey);
    }


    public boolean checkResponse(byte[] message)
    {
        byte[] expectedMessage = sha256Bytes(hBtKey);
        boolean responseCorrect = ArrayUtils.safeCompare(expectedMessage, message);

        if (responseCorrect)
            rollKeys();
        return responseCorrect;
    }


    public void rollKeys()
    {
        btKey = nextBtKey;
        hBtKey = nextHBtKey;
        Log(this, "[ PROTOCOL ] \t\t\t\t\t\t\t[ Keys roll ]");
    }
}
