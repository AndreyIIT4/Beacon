package andrei.ftf.grsu.by.blescan;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;


public class BLEService extends Service {
    BluetoothAdapter bluetoothAdapter;
    Scanable bleCallback;
    BluetoothLeScanner btScanner;
    private IBinder connect = new BeaconBinder();
    BleDevice bleDevice;
   ArrayList<BleDevice> beacon = new ArrayList();
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
        return connect;
    }

    @SuppressLint("NewApi")
    private ScanCallback leScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (device.getName() != null) {
                int rssi = result.getRssi();
                bleDevice = new BleDevice(device.getName(), rssi);//uuid,
                Log.d("Main", "Beacon Name " + device.getName());
                Log.d("Main", "Beacon RSSI " + rssi);
                beacon.add(bleDevice);
                if (bleCallback != null) {
                   bleCallback.search(bleDevice);

                }
            }
        }
    };

    public void startScanning() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (Build.VERSION.SDK_INT >=21) {
                btScanner = bluetoothAdapter.getBluetoothLeScanner();
                ScanSettings settings = new ScanSettings.Builder()
                        .setReportDelay(0)
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                Log.d("Main", "Start Scan Beacon ");
                btScanner.startScan(null, settings, leScanCallback);
            }
        }
        else{
            startScanning();
        }
    }

    public void stopScanning() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btScanner.stopScan(leScanCallback);
        }
    }

    public class BeaconBinder extends Binder {
        public void setCallback(Scanable callback) {
            bleCallback = callback;
        }
    }
}