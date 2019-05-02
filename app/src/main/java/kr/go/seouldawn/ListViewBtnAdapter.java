package kr.go.seouldawn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListViewBtnAdapter extends ArrayAdapter {

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;

    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드, 화면에 출력 시 사용
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.activity_list_element*/, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final TextView textview = (TextView) convertView.findViewById(R.id.storename);  //가게 이름
        final TextView textview1 = (TextView) convertView.findViewById(R.id.storeaddress);  //가게 주소

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewBtnItem listViewItem = (ListViewBtnItem) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        textview.setText(listViewItem.getName());
        textview1.setText(listViewItem.getAddress());

        // button1 클릭 시 가게 전화번호 연결
        ImageButton but = (ImageButton)convertView.findViewById(R.id.callphone);
        but.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //각 리스트 listviewItem의 전화번호를 바로 가져와서 넘겨줌
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+listViewItem.getTel()));
                context.startActivity(myIntent);
            }
        });
        but.setFocusable(false);


        return convertView;
    }

}
