package andrei.ftf.grsu.by.beacon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import andrei.ftf.grsu.by.blescan.BleDevice;

/**
 * Created by Lenovo on 26.11.2017.
 */

public class BeaconAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<BleDevice> beaconList;

    public  BeaconAdapter(Context context, ArrayList<BleDevice> products) {
        ctx = context;
        beaconList = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int position) {
        return beaconList.get(position);
    }

    @Override
    public int getCount() {
        return beaconList.size();
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }
        BleDevice bleDevice = getBleDevice(position);
        ((TextView) view.findViewById(R.id.tvDescr)).setText(bleDevice.getNameDevice());
        String rssi=String.valueOf(bleDevice.getRssiDevice());
        ((TextView) view.findViewById(R.id.tvPrice)).setText(rssi);
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
        return view;
    }

    // товар по позиции
    BleDevice getBleDevice(int position) {////
        return ((BleDevice) getItem(position));
    }
}
