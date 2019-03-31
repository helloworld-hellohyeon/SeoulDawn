package kr.go.seouldawn;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DrugStore extends Fragment {
    TextView textView;
    ArrayList<String> ArrData = new ArrayList<>();
    Button hos;
    Button phar;
    Button hair;
    Button res;

    //병원,약국
    //가게 클릭시 sub4 실행(intent:"guname"-구이름, "category"-카테고리,"count"-선택한가게의 배열 숫자)
    //list_count : 다음버튼을 클릭시 1씩 더해주고 가게 선택시 OnItemClickListener에서 count=position+(list_count*10)로 넘겨준다.
    //intent_count : 받아온 count를 넣어줌


    int i=0,j=0,first,last,list_count=0,intent_count=0;
    String guname="",category,tell,timee,address,check="",check2="",name;
    ImageButton left,right;
    ListView listview;
    String numm;
    DatabaseReference Ddname;
    ListViewAdapter adapter;
    ArrayList<String> LIST = new ArrayList<String>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Gangnam;

    public DrugStore() { }

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

        listview = (ListView)view.findViewById(R.id.view2);
        adapter = new ListViewAdapter() ;

        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listview.setOnItemClickListener(itemHandler);;

        View footer = getLayoutInflater().inflate(R.layout.activity_list_footer, null, false) ;
        right=(ImageButton)footer.findViewById(R.id.btn_right);
        listview.addFooterView(footer);


        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity root = getActivity();
                if(check.equals("no")) {
                    Toast.makeText(root, "가게가 더이상 없습니다.", Toast.LENGTH_LONG).show();
                    check2="no";
                }
                else{
                    if(check2.equals("no")){
                        Toast.makeText(root, "가게가 더이상 없습니다.", Toast.LENGTH_LONG).show();

                    }else {
                        list_count++;
                        intent_count++;
                        first = first + 5;
                        Data(first, first);
                    }
                }

            }
        });
        return view;
    }//onCreate

    AdapterView.OnItemClickListener itemHandler=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            position = position+(list_count*5);
            Intent intent = new Intent(getContext(), Detail.class); //getApplicationContext()
            intent.putExtra("guname",guname);
            intent.putExtra("category",category);
            intent.putExtra("count",position);
            intent.putExtra("intent_count",intent_count);
            startActivity(intent);
        }

    };
    @Override
    public void onStart() {
        super.onStart();
        Data(intent_count*5,intent_count*5);
        this.first=intent_count*5;
    }//onStart


    void Data(int first,int last) {
        this.first=first;
        this.last=last;
        last=last+5;
        check="";
        check2="";
        for (first = first; first<last; first++) {
            DatabaseReference Dname;
            DatabaseReference tel,time,addr;
            String num = String.valueOf(first);
            numm = String.valueOf(first+1);
            Dname = Gangnam.child(num).child("name");
            time = Gangnam.child(num).child("monday");
            addr=Gangnam.child(num).child("address");

            LIST.clear();
            adapter.notifyDataSetChanged();



            time.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timee=dataSnapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });

            addr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    address=dataSnapshot.getValue(String.class);
                    if(address.contains("(")){
                        address = address.substring(0, address.indexOf("(")-1) + "\n" + address.substring(address.indexOf("("));

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });

            Dname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.getValue(String.class);
                    Log.d("GangNamGu", "Value is " + name);
                    if(name != null){
                        LIST.add(name +"\n\n"+address);
                        adapter.addItem(name,address);
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        check="no";
                        Ddname= Gangnam.child(numm).child("name");
                        Ddname.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.getValue(String.class);
                                if(name != null){}
                                else{
                                    check2="no";
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

            Ddname= Gangnam.child(numm).child("name");
            Ddname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    if(name != null){   }
                    else{
                        check2="no";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }//for

    }
}
