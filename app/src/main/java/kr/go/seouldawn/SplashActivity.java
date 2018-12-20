package kr.go.seouldawn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


public class SplashActivity extends AppCompatActivity {
    ImageView SeoulDawnTitle;
    ConnectivityManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.BLACK);
        }

        SeoulDawnTitle = findViewById(R.id.seouldawntitle);
        Animation Aplha = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashalpha);
        if (!isConnected()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
            dialog.setMessage("네트워크 연결을 확인해주세요");
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //ActivityCompat.finishAffinity(getParent());
                    finish();
                }
            }).show();
            // Toast.makeText(this, "네트워크가 연결되지 않았습니다.", Toast.LENGTH_SHORT).show();
        } else {
            SeoulDawnTitle.startAnimation(Aplha);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }

    }
    private boolean isConnected () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetWork != null && activeNetWork.isConnectedOrConnecting();

        return isConnected;
    }

    private int getNetworkType () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();

        return activeNetWork.getType();
    }
}



