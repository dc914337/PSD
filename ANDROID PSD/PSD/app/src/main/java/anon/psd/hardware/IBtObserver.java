package anon.psd.hardware;

import anon.psd.device.ConnectionState;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(byte[] message);
    void onStateChanged(ConnectionState newState);
}