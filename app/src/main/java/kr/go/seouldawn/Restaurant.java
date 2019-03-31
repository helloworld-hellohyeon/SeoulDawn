package kr.go.seouldawn;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Restaurant extends Fragment {
    FirebaseDatabase database;
    int first,last,list_count=0,intent_count=0;
    int con,ex;
    String numm;
    DatabaseReference Ddname;
    String guname,category,tell="",timee,address,check="",check2="",name;
    ImageButton left,right;
    ListViewAdapter adapter;
    ListView listview;
    ArrayList<String> LIST_Hair = new ArrayList<String>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Gangnam;
    public Restaurant() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_hospital, container, false);
        database = FirebaseDatabase.getInstance();

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
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(itemHandler);

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
            position = position+(list_count*10);
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
                        LIST_Hair.add(name +"\n\n"+address);
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

