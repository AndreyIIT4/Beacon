package andrei.ftf.grsu.by.beacon;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrei.ftf.grsu.by.blescan.BleService;
import andrei.ftf.grsu.by.blescan.BleDevice;
import andrei.ftf.grsu.by.blescan.Scanable;

public class MainActivity extends AppCompatActivity implements Scanable{
    ArrayList<BleDevice> bleDevices = new ArrayList<BleDevice>();
    ListView listView;
    BeaconAdapter adapter;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          accessLocationPermission();
          Intent intent=new Intent(this, BleService.class);
          bindService(intent,connection, Context.BIND_AUTO_CREATE);
          setContentView(R.layout.main);
          listView=(ListView) findViewById (R.id.lvMain);
          bleDevices.add(new BleDevice("ID3",-32));
          adapter=new BeaconAdapter(this,bleDevices);
          adapter.notifyDataSetChanged();
          listView.setAdapter(adapter);
    }

    @TargetApi(23)
    private void accessLocationPermission() {
        int accessCoarseLocation = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation   = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listRequestPermission = new ArrayList<>();
        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listRequestPermission.isEmpty()) {
            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
            requestPermissions(strRequestPermission, 1);
        }
    }

   private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BleService.BeaconBinder binder=(BleService.BeaconBinder ) iBinder;
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
    public void search(BleDevice s) {
        bleDevices.add(s);
    }

}
