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


public class BLEService extends Service {

    BluetoothLeScanner btScanner;
    BluetoothAdapter bluetoothAdapter;
    Scanable bleCallback;


    private  IBinder connect=new BeaconBinder();
    public BLEService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        startScanning();
        return  connect;
    }

    @SuppressLint("NewApi")
    private ScanCallback leScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
         BluetoothDevice device= result.getDevice();
            if (bleCallback!=null){
                bleCallback.search(device.getName());
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

}
