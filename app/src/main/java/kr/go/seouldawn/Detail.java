package kr.go.seouldawn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Detail extends FragmentActivity implements OnMapReadyCallback {

    ArrayList<String> ReceiveArr;
    ArrayList<String> ArrData = new ArrayList<>();
    String guname,category,name,tell="",timee,address,vacation,num;
    String smon,stues,swen,sthur,sfri,ssatur,ssun,sother;
    int count,intent_count=0;
    TextView iname, itel, iaddress, itime,ivacation;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference data;

    private GoogleMap mMap;
    public Geocoder geocoder;

    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        iname=findViewById(R.id.iname);
        itel=findViewById(R.id.itel);
        iaddress=findViewById(R.id.iaddress);
        itime=findViewById(R.id.itime);
        ivacation=findViewById(R.id.ivacation);
        button=findViewById(R.id.button);

        Intent intent = getIntent();
        //ReceiveArr = intent.getStringArrayListExtra("ArrList");
        guname = intent.getExtras().getString("guname");
        category=intent.getExtras().getString("category");
        count=intent.getExtras().getInt("count");
        intent_count=intent.getExtras().getInt("intent_count");
        num = String.valueOf(count);
        data=mDatabase.child(guname).child(category).child(num);

        ArrData.add(guname);


        //현수코드
        //button=(Button)findViewById(R.id.button);
        //  editText = findViewById(R.id.editText);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference dname, tel,addr;
        dname=data.child("name");

        if(category.equals("미용실")||category.equals("식당")) {
            DatabaseReference time,vaca;
            time=data.child("time");
            vaca=data.child("vacation");
            time.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timee=dataSnapshot.getValue(String.class);
                    itime.append(timee);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });

            vaca.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    vacation=dataSnapshot.getValue(String.class);
                    ivacation.append("휴무일 : "+vacation);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
        }
        else{
            DatabaseReference mon,tues,wen,thur,fri,satur,sun,other;
            mon=data.child("monday");
            tues=data.child("tuesday");
            wen=data.child("wednesday");
            thur=data.child("thursday");
            fri=data.child("friday");
            satur=data.child("saturday");
            sun=data.child("sunday");
            other=data.child("otherday");

            mon.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    smon=dataSnapshot.getValue(String.class);
                    itime.append("\n"+"  월요일("+smon+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            tues.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    stues=dataSnapshot.getValue(String.class);
                    itime.append("  화요일("+stues+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            wen.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    swen=dataSnapshot.getValue(String.class);
                    itime.append("  수요일("+swen+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }

            });
            thur.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sthur=dataSnapshot.getValue(String.class);
                    itime.append("  목요일("+sthur+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            fri.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sfri=dataSnapshot.getValue(String.class);
                    itime.append("  금요일("+sfri+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            satur.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ssatur=dataSnapshot.getValue(String.class);
                    itime.append("  토요일("+ssatur+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            sun.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ssun=dataSnapshot.getValue(String.class);
                    itime.append("  일요일("+ssun+")"+"\n");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });
            other.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sother=dataSnapshot.getValue(String.class);
                    itime.append("  기타("+sother+")");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {              }
            });


        }//else
        tel=data.child("tel");
        addr=data.child("address");

        dname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.getValue(String.class);
                iname.append(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {              }
        });

        tel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tell=dataSnapshot.getValue(String.class);
                itel.append(tell);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {              }
        });

        addr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                address=dataSnapshot.getValue(String.class);
                iaddress.append(address);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {              }
        });

    }//onStart

    //현수코드
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        ////////////////////

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = address; //mConditionRef;//"신도림";//editText.getText().toString();
                //Log.e("next", address);
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "실패");
                }
                Log.e("next", addressList.get(0).toString());
                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String[] splitStr = addressList.get(0).toString().split(",");
                String saddress = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                //Toast.makeText(getApplicationContext(), saddress, Toast.LENGTH_LONG).show();

                HashMap<String, Double> map = new HashMap<>();

                for (Address addre : addressList) {
                    map.put("latitude", addre.getLatitude());
                    map.put("longtitude", addre.getLongitude());
                }

//                Map get_ll = new HashMap();
//
//                for(int i = 0; i < splitStr.length; i++){
//                    get_pre = splitStr[i].trim().split("=");
//                    get_ll.put(get_pre[0], get_pre[1]);
//                }
//
//                Log.e("next", get_pre[0].toString() + " " + get_pre[1].toString());
                //Log.e("next", (String)map.get("latitude") + " " + map.get("longitude"));
                String latitude = map.get("latitude").toString(); //splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = map.get("longtitude").toString(); //splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                //System.out.println(latitude);
                //System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(saddress);
                mOptions2.position(point);


                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap ownMarker = Bitmap.createScaledBitmap(b, 59, 88, false);

                mOptions2.icon(BitmapDescriptorFactory.fromBitmap(ownMarker));
                // 마커 추가
                mMap.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 18));
            }
        });

        LatLng seoul = new LatLng(37.6, 127.0);
        MarkerOptions mOptions3 = new MarkerOptions();
        mOptions3.title("search result");
        mOptions3.position(seoul);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 10));
    }



    @Override
    public void onBackPressed() {
        if(category.equals("미용실")){
            BeautySalon fg = new BeautySalon();
            Bundle bundle = new Bundle(3);
            bundle.putString("guname",guname);
            bundle.putString("category","식당");
            bundle.putInt("intent_count",intent_count);
            fg.setArguments(bundle);
        }
        else if(category.equals("식당")){
            Restaurant fg = new Restaurant();
            Bundle bundle = new Bundle(3);
            bundle.putString("guname",guname);
            bundle.putString("category","식당");
            bundle.putInt("intent_count",intent_count);
            fg.setArguments(bundle);

        }else if(category.equals("병원")){
            Restaurant fg = new Restaurant();
            Bundle bundle = new Bundle(3);
            bundle.putString("guname",guname);
            bundle.putString("category","식당");
            bundle.putInt("intent_count",intent_count);
            fg.setArguments(bundle);
        }
        else{
            DrugStore fg = new DrugStore();
            Bundle bundle = new Bundle(3);
            bundle.putString("guname",guname);
            bundle.putString("category","식당");
            bundle.putInt("intent_count",intent_count);
            fg.setArguments(bundle);
        }
        super.onBackPressed();
        finish();

    }



}