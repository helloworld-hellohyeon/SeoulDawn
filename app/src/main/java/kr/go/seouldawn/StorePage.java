package kr.go.seouldawn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class StorePage extends AppCompatActivity{
    WebView mWebView;
    String guname;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setBackgroundColor(0);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);

        mWebView.setWebViewClient(new WebViewClient());
  //      mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);

        mWebView.loadUrl("file:///android_asset/www/index.html");
        mWebView.addJavascriptInterface(new WebAppInterface(this), "connect");

    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void setGu(String getName) {
            guname = getName;
            Intent intent = new Intent(getApplicationContext(),StorePageList.class); //getApplicationContext()
            intent.putExtra("guname",guname);
            Toast.makeText(mContext, guname, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}