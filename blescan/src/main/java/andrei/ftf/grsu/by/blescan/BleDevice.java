package andrei.ftf.grsu.by.blescan;

/**
 * Created by Lenovo on 15.11.2017.
 */

public class BleDevice {
    private String nameDevice;
    private String uuidDevice;
    private int rssiDevice;

    public BleDevice (String nameDevice,int rssiDevice)//String uuidDevice,
    {
//реализовать конструктор
        this.nameDevice=nameDevice;
        //this.uuidDevice=uuidDevice;
        this.rssiDevice=rssiDevice;
    }
    public BleDevice (){

    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public void setUuidDevice(String uuidDevice) {
        this.uuidDevice = uuidDevice;
    }

    public void setRssiDevice(int rssiDevice) {
        this.rssiDevice = rssiDevice;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public String getUuidDevice() {
        return uuidDevice;
    }

    public int getRssiDevice() {
        return rssiDevice;
    }
}
