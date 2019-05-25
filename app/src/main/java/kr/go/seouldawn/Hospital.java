package kr.go.seouldawn;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.Subject;


public class Hospital extends Fragment {
    //병원
    //가게 클릭시 sub4 실행(intent:"guname"-구이름, "category"-카테고리,"count"-선택한가게의 배열 숫자)
    //list_count : 다음버튼을 클릭시 1씩 더해주고 가게 선택시 OnItemClickListener에서 count=position+(list_count*10)로 넘겨준다.
    //intent_count : 받아온 count를 넣어줌


    int i = 0, j = 0, first, last, list_count = 0, intent_count = 0;
    String guname = "", category, tell, timee, address, check = "", check2 = "", name;
    ImageButton left, right;
    ListView listview;
    String numm,tel1;
    DatabaseReference Ddname;
    ListViewBtnAdapter adapter;


    ArrayList<ListViewBtnItem> LIST = new ArrayList<ListViewBtnItem>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Gangnam;

    public Hospital() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_hospital, container, false);


        Bundle extra = getArguments();
        guname = extra.getString("guname");
        category = extra.getString("category");
        intent_count = extra.getInt("intent_count");
        list_count = intent_count;
        Gangnam = mDatabase.child(guname).child(category);

        listview = (ListView) view.findViewById(R.id.view2);
        adapter = new ListViewBtnAdapter(getContext(),R.layout.activity_list_element,LIST);

        View footer = getLayoutInflater().inflate(R.layout.activity_list_footer, null, false);
        right = (ImageButton) view.findViewById(R.id.btn_right);
        left = (ImageButton) view.findViewById(R.id.btn_left);
        //listview.addFooterView(footer);

        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listview.setOnItemClickListener(itemHandler);

        //setListViewHeightBasedOnChildren(listview);


        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity root = getActivity();
                if (check.equals("no")) {
                    Toast.makeText(root, "가게가 더이상 없습니다.", Toast.LENGTH_LONG).show();
                    check2 = "no";
                } else {
                    if (check2.equals("no")) {
                        Toast.makeText(root, "2가게가 더이상 없습니다.", Toast.LENGTH_LONG).show();
                        check="yes";
                    } else {
                        list_count++;
                        intent_count++;
                        first = first + 7;
                        Data(first, first);
                    }
                }

            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity root = getActivity();
                if(list_count<=0) {
                    Toast.makeText(root, "가게가 더이상 없습니다.", Toast.LENGTH_LONG).show();
                    check2="yes";
                }
                else{
                    if(check2.equals("no")){
                        Toast.makeText(root, "2가게가 더이상 없습니다.", Toast.LENGTH_LONG).show();
                        check2="yes";
                    }else {
                        list_count--;
                        intent_count--;
                        first = first - 7;
                        Data(first, first);
                    }
                }
            }
        });


        return view;
    }//onCreate

    AdapterView.OnItemClickListener itemHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            position = position + (list_count * 7);
            Intent intent = new Intent(getContext(), Detail.class); //getApplicationContext()
            intent.putExtra("guname", guname);
            intent.putExtra("category", category);
            intent.putExtra("count", position);
            intent.putExtra("intent_count", intent_count);
            startActivity(intent);
        }

    };

    @Override
    public void onStart() {
        super.onStart();
        Data(intent_count * 7, intent_count * 7);
        this.first = intent_count * 7;
    }//onStart


    void Data(int first, int last) {
        this.first = first;
        this.last = last;
        last = last + 7;
        /*
        check = "";
        check2 = "";*/
        for (first = first; first < last; first++) {
            final DatabaseReference Dname;
            DatabaseReference tel, time, addr;
            String num = String.valueOf(first);
            numm = String.valueOf(first + 1);
            Dname = Gangnam.child(num).child("name");
            tel = Gangnam.child(num).child("tel");
            time = Gangnam.child(num).child("monday");
            addr = Gangnam.child(num).child("address");


            LIST.clear();
            adapter.notifyDataSetChanged();

//            Dname.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    storename =dataSnapshot.getValue(String.class);
//                    Log.e("zzzz",storename);
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {              }
//            });
//            // if(tell.) break;

            addr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    address = dataSnapshot.getValue(String.class);
                    if(address != null) {
                        if (address.contains("(")) {
                            address = address.substring(0, address.indexOf("(") - 1) + "\n" + address.substring(address.indexOf("("));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            time.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timee = dataSnapshot.getValue(String.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            tel.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //tel1 : 전화번호 가져온 거 담는 String
                    tel1= dataSnapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {            }
            });


            Dname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.getValue(String.class);
                    ListViewBtnItem item = new ListViewBtnItem();
                    Log.d("GangNamGu", "Value is " + name);
                    if (name != null) {
                        //가게 이름, 주소, 전화번호를 각 리스트의 item에 저장해줌
                        item.setName(name);
                        item.setAddress(address);
                        item.setTel(tel1);
                        LIST.add(item);
                        adapter.notifyDataSetChanged();
                    } else {
                        check = "no";
                        Ddname = Gangnam.child(numm).child("name");
                        Ddname.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.getValue(String.class);
                                if (name != null) {
                                } else {
                                    check2 = "no";
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("GangNamGuGu", "Faild " + databaseError.toException());
                }
            });

            Ddname = Gangnam.child(numm).child("name");
            Ddname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    if (name != null) {
                    } else {
                        check2 = "no";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }//for

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight+(listView.getDividerHeight()*(listAdapter.getCount()-1));
        listView.setLayoutParams(params);

        listView.requestLayout();
    }


}
