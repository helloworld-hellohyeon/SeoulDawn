package kr.go.seouldawn;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SearchBus extends AppCompatActivity {

    EditText edit;
    Button search_bus;
    ProgressDialog pb;
    String key = "w1LpendNML9NSRvEVJVRbZTbwm0ZK8bwkZiIoXsOwU0QZzhoQZmSDrRgr%2FEeqvNRWV%2F4NGpifpwT8LM1Hvu0dg%3D%3D";
    HashMap<String, String> result = new HashMap<>();
    ListView listView;
    ListviewBusAdapter adapter;
    ArrayList<ListViewBus> data = new ArrayList<>();
    ArrayList<String> dataForBus = new ArrayList<>();
    String buses[] = {"N13", "N15", "N16", "N26", "N30", "N37", "N61", "N62", "N65", "N6001", "N6002"};
    String name = null, id = null, arsID = null;
    InputMethodManager imm;
    int check=0; //서울을 입력했는지 확인해주는 변수
    int check1=0;  //검색결과가 있는지 확인하는 변수

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(SearchBus.this);

        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("로딩중입니다..");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0;i < 2 ;i++) {
                    Thread.sleep(1000);
                }
                getXmlDataSearch();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            adapter.notifyDataSetChanged();
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
            super.onPostExecute(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus);

        for (int i = 0; i < buses.length; i++) {
            dataForBus.add(buses[i]);

        }

        listView = findViewById(R.id.stationList);
        edit = findViewById(R.id.search_key);
        edit.setInputType (InputType. TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        search_bus = findViewById(R.id.search_station);
        search_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=0; check1=0;
                adapter.clear();
                adapter.notifyDataSetChanged();

                if(edit.getText().toString().equals("서울")){
                    check=1;
                }else {
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                }

                if(check == 1){
                    Toast.makeText(getApplicationContext(), "상세하게 적어주세요!!", Toast.LENGTH_LONG).show();
                    edit.setText("");
                    check=0;
                }
                if(check1 == 0)Toast.makeText(getApplicationContext(), "검색결과가 없습니다.", Toast.LENGTH_LONG).show();
                Log.e("없는 검색어",""+check1);
                adapter.notifyDataSetChanged();
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
            }
        });
        listView.setAdapter(null);
        adapter = new ListviewBusAdapter();

        listView.setAdapter(adapter);
    }


    void getXmlDataSearch() {

        StringBuffer buffer = new StringBuffer();
        data = new ArrayList<>();


        String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
        //Log.e("받아옴", str);
        String busStation = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding     //지역 검색 위한 변수

        String queryUrl = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?"//요청 URL
                + "serviceKey=" + key
                + "&stSrch=" + busStation;


        try {
            //String name = null, id = null;
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            xpp.next();

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("item")) ;// 첫번째 검색결과

                        else if (tag.equals("stId")) { // 정류소 아이디
                            xpp.next();
                            id = xpp.getText();
                        } else if (tag.equals("arsId")) { //  정류소 고유 번호
                            xpp.next();
                            arsID = xpp.getText();
                        } else if (tag.equals("stNm")) {
                            xpp.next();
                            name = xpp.getText();
                            getBusName(arsID); // 버스 번호 얻어오는 함수로 연결됨
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:

                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    } // getXMLDataSearch()


    //boolean getBusName(String arsId) {
    // 버스 번호 얻어오는 함수
    void getBusName(String arsId) {
        //Log.e("버스함수", arsId);
        String queryUrl = "http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation?"//요청 URL
                + "serviceKey=" + key
                + "&arsId=" + arsId; // 정류소 고유 번호로 검색

        try {

            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            //Log.e("eventType", eventType+" ");

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("busRouteNm")){
                            //Log.e("함수", "elseif");
                            xpp.next();
                            if(dataForBus.contains(xpp.getText())){
                                //Log.e("버스함수If", "들어옴");
                                result.put("busNum", xpp.getText()); // 버스 번호
                                //Log.e("버스번호", xpp.getText());
                                result.put("station", name);
                                result.put("id", arsId);
                                Log.e("결과", result+"");
                                adapter.addItem(result); // 정류소이름 = name, 정류소아이디 = id
                                check1=1;
                                //adapter.notifyDataSetChanged();
                            }else{
                                if(check1==1){}
                                else{check1=0;}
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:

                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } // getBusName
}
