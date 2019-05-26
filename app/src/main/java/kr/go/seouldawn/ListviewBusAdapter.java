package kr.go.seouldawn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListviewBusAdapter extends BaseAdapter {

    private ArrayList<ListViewBus> data = new ArrayList<>();

    public ListviewBusAdapter(){}
    String firstName = "firstValue";
    String firstId = "firstValue";
    String firstBusNum = "firstValue";

    @Override
    public int getCount() {
        return data.size() ;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        TextView stationName = convertView.findViewById(R.id.stationName);
        TextView stationId = convertView.findViewById(R.id.stationID);
        TextView busNum = convertView.findViewById(R.id.busNum);

        ListViewBus listViewitem = data.get(position);

        stationName.setText(listViewitem.getName());
        stationId.setText(listViewitem.getId());
        busNum.setText(listViewitem.getBus());

        return convertView;
    }
    public void addItem(HashMap getData) { // 정류소 이름 = name, 정류소 아이디 = id, 버스번호 = busNum
        ListViewBus item = new ListViewBus();
        //Log.e("addItem", "ITEMS : " + getData);

        if (getData.get("station") != null && getData.get("id") != null) {
            //if (!getData.get("station").equals(firstName) || !getData.get("id").equals(firstId)) {
            //if(!getData.get("id").equals(firstBusNum)){
            firstBusNum = (String) getData.get("busNum");
            firstName = (String) getData.get("station");
            firstId = (String) getData.get("id");

            // 리스트에 정류소 이름, 정류소 아이디, 버스 번호 담은 리스트 아이템 추가
            item.setStationName((String) getData.get("station"));
            item.setStationId((String) getData.get("id"));
            item.setBusNum((String) getData.get("busNum"));
            data.add(item);
            //}

        }
    }
    public void clear(){
        data.clear();
    }
}
