package andrei.ftf.grsu.by.beacon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import andrei.ftf.grsu.by.blescan.BLEService;
import andrei.ftf.grsu.by.blescan.Scanable;

public class MainActivity extends AppCompatActivity implements Scanable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(this, BLEService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }
    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BLEService.BeaconBinder binder=(BLEService.BeaconBinder ) iBinder;
            binder.setCallback(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();

    }

    @Override
    public void search(String s) {
        Log.d("Main","QQQQQQ   "+s);
    }
}
