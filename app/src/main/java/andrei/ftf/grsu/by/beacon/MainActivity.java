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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import andrei.ftf.grsu.by.blescan.BLEService;
import andrei.ftf.grsu.by.blescan.BleDevice;
import andrei.ftf.grsu.by.blescan.Scanable;

public class MainActivity extends AppCompatActivity implements Scanable{

    List<String> bleDevices = new ArrayList<>();
    ListView listView;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          accessLocationPermission();
          Intent intent=new Intent(this, BLEService.class);
          bindService(intent,connection, Context.BIND_AUTO_CREATE);
          setContentView(R.layout.activity_main);
          listView=(ListView)findViewById (R.id.listView);
          ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,bleDevices);
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
    public void search(BleDevice s) {
        bleDevices.add(s.getNameDevice());
    }
}
