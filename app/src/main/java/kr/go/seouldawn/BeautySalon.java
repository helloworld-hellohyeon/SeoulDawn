package kr.go.seouldawn;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class BeautySalon extends Fragment {
    FirebaseDatabase database;
    int first,last,list_count=0,intent_count=0;
    int con,ex;
    String numm;
    DatabaseReference Ddname;
    String guname,category,tell="",timee,address,check="",check2="";
    ImageButton left,right;

    ArrayAdapter<String> adapter;
    ArrayList<String> LIST_Hair = new ArrayList<String>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Gangnam;


    public BeautySalon(){ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_hospital, container, false);
        database = FirebaseDatabase.getInstance();
        left=(ImageButton)view.findViewById(R.id.btn_left);
        right=(ImageButton)view.findViewById(R.id.btn_right);

        Bundle extra = getArguments();
        guname = extra.getString("guname");
        category = extra.getString("category");
        intent_count = extra.getInt("intent_count");
        list_count = intent_count;
        Gangnam = mDatabase.child(guname).child(category);


        ListView listview = (ListView)view.findViewById(R.id.view2);
        adapter = new ArrayAdapter<String>(getContext() ,android.R.layout.simple_list_item_1,LIST_Hair);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(itemHandler);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity root = getActivity();
                first=first-10;
                if(first<0) {
                    Toast.makeText(root, "이전으로 돌아갈 수 없습니다.", Toast.LENGTH_LONG).show();
                    first=first+10;
                }else{
                    list_count--;
                    intent_count--;
                    Data(first, first);
                }
            }
        });
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
                        first = first + 10;
                        Data(first, first);
                    }
                }
            }
        });
        return view;
    }
    AdapterView.OnItemClickListener itemHandler=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            position = position+(list_count*200);
            Intent intent = new Intent(getContext(), Detail.class);
            intent.putExtra("guname",guname);
            intent.putExtra("category",category);
            intent.putExtra("count",position);
            intent.putExtra("intent_count",intent_count);
            startActivity(intent);
        }

    };


    @Override
    public void onStart(){
        super.onStart();
        Data(intent_count*200,intent_count*200);
        this.first=intent_count*200;
    }//onStart


    void Data(int first,int last) {
        this.first=first;
        this.last=last;
        last=last+200;
        check="";
        check2="";
        for (first = first; first<last; first++) {
            ex=first;
            DatabaseReference Dname;
            DatabaseReference tel,time,addr;
            String num = String.valueOf(first);
            numm = String.valueOf(first+1);
            Dname = Gangnam.child(num).child("name");
            tel = Gangnam.child(num).child("tel");
            time = Gangnam.child(num).child("time");
            addr=Gangnam.child(num).child("address");

            LIST_Hair.clear();
            adapter.notifyDataSetChanged();

            tel.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tell=dataSnapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            // if(tell.) break;

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
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });

            Dname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    Log.d("GangNamGu", "Value is " + name);
                    if(name != null){
                        LIST_Hair.add(name +"\n\n"+timee+"\n"+tell+"\n"+address);
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
                    }//else

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
                    if(name != null){  }
                    else{
                        check2="no";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }//for

    }//Data

}//class

