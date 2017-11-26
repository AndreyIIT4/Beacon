package andrei.ftf.grsu.by.blescan;

import android.widget.TextView;

/**
 * Created by Lenovo on 22.11.2017.
 */

public class BeaconHolder {
    private TextView nameBeacon;
    private TextView rssiBeacon;

    public  BeaconHolder(TextView textNameBeacon, TextView textRssiBeacon) {
        this.nameBeacon = textNameBeacon;
        this.rssiBeacon = textRssiBeacon;
    }
}
