package andrei.ftf.grsu.by.blescan;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;


public class BLEService extends Service {
    BluetoothAdapter bluetoothAdapter;
    Scanable bleCallback;
    BluetoothLeScanner btScanner;//=new BluetoothLeScanner(); как создать объект
    private  IBinder connect=new BeaconBinder();
    BleDevice bleDevice=new BleDevice();
    public BLEService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        getApplicationContext().startActivity(enableBtIntent);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btScanner = bluetoothAdapter.getBluetoothLeScanner();
        }
        startScanning();

    }

    @Override
    public IBinder onBind(Intent intent) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return  connect;
    }

    @SuppressLint("NewApi")
    private ScanCallback leScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            //result.getRssi();
            if (device.getName() != null) {
                //String uuid=convertASCIItoString(device.getUuids().toString());
                int rssi=result.getRssi();
                bleDevice=new BleDevice(device.getName(),rssi);//uuid,
                Log.d("Main", "Beacon Name " + device.getName());
                Log.d("Main", "Beacon RSSI " + rssi);

            if (bleCallback != null) {
                bleCallback.search(device.getName());

            }
            }
        }
        };

    public void startScanning() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btScanner.startScan(leScanCallback);
        }
    }

    public void stopScanning() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btScanner.stopScan(leScanCallback);
            }
    }
    public class BeaconBinder extends Binder{
        public  void setCallback(Scanable callback){
            bleCallback=callback;
        }
    }
    private String convertASCIItoString(String  UUID){
        UUID = UUID.substring(1,UUID.length()-1);
        UUID = UUID.replaceAll("-","");
        UUID = UUID.replaceAll("00","");
        byte [] txtInByte = new byte [UUID.length() / 2];
        int j = 0;
        for (int i = 0; i < UUID.length(); i ++) txtInByte[j++] = Byte.parseByte(UUID.substring(i, i + 2), 16);
        return String.valueOf(new StringBuffer(new String(txtInByte)).reverse());
    }
}
