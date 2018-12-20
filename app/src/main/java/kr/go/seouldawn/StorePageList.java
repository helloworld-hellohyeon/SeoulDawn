package kr.go.seouldawn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;


public class StorePageList extends AppCompatActivity implements View.OnClickListener{
    ImageButton hospital, drugstore, beautysalon,restaurant;

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private final int FRAGMENT4 = 4;

    String guname="";
    ArrayList<String> ArrData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page_list);


        hospital = findViewById(R.id.hospital);
        drugstore = findViewById(R.id.drugstore);
        beautysalon = findViewById(R.id.beautysalon);
        restaurant = findViewById(R.id.restaurant);

        hospital.setOnClickListener(this);
        drugstore.setOnClickListener(this);
        beautysalon.setOnClickListener(this);
        restaurant.setOnClickListener(this);

        callFragment(FRAGMENT1);

        Intent intent= getIntent();
        guname = intent.getExtras().getString("guname");
    }

    @Override
    public void onClick(View v) {
        hospital.setImageResource(R.drawable.hospital_off);
                 hospital.setBackgroundResource(R.drawable.storepageicon_off);
                 drugstore.setImageResource(R.drawable.drugstore_off);
                 drugstore.setBackgroundResource(R.drawable.storepageicon_off);
                 beautysalon.setImageResource(R.drawable.beautysalon_off);
                 beautysalon.setBackgroundResource(R.drawable.storepageicon_off);
                 restaurant.setImageResource(R.drawable.restaurant_off);
                 restaurant.setBackgroundResource(R.drawable.storepageicon_off);

                switch (v.getId()) {
                        case R.id.hospital:
                            hospital.setImageResource(R.drawable.hospital_on);
                            hospital.setBackgroundResource(R.drawable.storepageicon_on);
                            callFragment(FRAGMENT1);
                              break;
                        case R.id.drugstore:
                             drugstore.setImageResource(R.drawable.drugstore_on);
                             drugstore.setBackgroundResource(R.drawable.storepageicon_on);
                            callFragment(FRAGMENT2);
                            break;
                        case R.id.beautysalon:
                            beautysalon.setImageResource(R.drawable.beautysalon_on);
                            beautysalon.setBackgroundResource(R.drawable.storepageicon_on);
                            callFragment(FRAGMENT3);
                            break;
                        case R.id.restaurant:
                            restaurant.setImageResource(R.drawable.restaurant_on);
                            restaurant.setBackgroundResource(R.drawable.storepageicon_on);
                            callFragment(FRAGMENT4);
                            break;
                    }//switch
    }

    private void callFragment(int fragmentNo) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNo){
            case 1:
                Hospital fragment1 = new Hospital();
                Bundle bundle1 = new Bundle(2);
                bundle1.putString("guname", guname);
                bundle1.putString("category","병원");
                fragment1.setArguments(bundle1);
                transaction.replace(R.id.fragment_container, fragment1).commit();
                break;
            case 2:
                DrugStore fragment2 = new DrugStore();
                Bundle bundle2 = new Bundle(2);
                bundle2.putString("guname", guname);
                bundle2.putString("category","약국");
                fragment2.setArguments(bundle2);
                transaction.replace(R.id.fragment_container, fragment2).commit();
                break;
            case 3:
                BeautySalon fragment3 = new BeautySalon();
                Bundle bundle3 = new Bundle(2);
                bundle3.putString("guname", guname);
                bundle3.putString("category","미용실");
                fragment3.setArguments(bundle3);
                transaction.replace(R.id.fragment_container, fragment3).commit();
                break;
            case 4:
                Restaurant fragment4 = new Restaurant();
                Bundle bundle4 = new Bundle(2);
                bundle4.putString("guname", guname);
                bundle4.putString("category","식당");
                fragment4.setArguments(bundle4);
                transaction.replace(R.id.fragment_container, fragment4).commit();
                break;


    }


}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

