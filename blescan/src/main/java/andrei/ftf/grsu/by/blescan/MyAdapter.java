package andrei.ftf.grsu.by.blescan;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Lenovo on 22.11.2017.
 */

public class MyAdapter {// extends BaseAdapter extends ArrayAdapter<BleDevice>
     private List<BleDevice> deviceList;
        public MyAdapter(List<BleDevice> beacon){
            deviceList=beacon;
    }

}
