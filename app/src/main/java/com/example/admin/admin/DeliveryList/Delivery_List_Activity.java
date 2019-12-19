package com.example.admin.admin.DeliveryList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.admin.LockerList.ListViewAdapter;
import com.example.admin.admin.LockerList.Locker_DetailList_Activity;
import com.example.admin.admin.LockerList.Locker_List_Activity;
import com.example.admin.admin.MainActivity;
import com.example.admin.admin.Menu_Activity;
import com.example.admin.admin.Model.DeliveryVO;
import com.example.admin.admin.Model.DeliveryViewItem;
import com.example.admin.admin.Model.ListViewItem;
import com.example.admin.admin.Model.LockerVO;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Delivery_List_Activity extends AppCompatActivity implements View.OnClickListener {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab;

    TextView text_list;


    SwipeRefreshLayout swipeRefreshLayout; //3

    String Admin_ID ="";
    String Admin_PWD ="";
    String Admin_MNO ="";
    String Admin_Area="";
    String Admin_Name ="";

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Toast.makeText(this,"홈 버튼 터치",Toast.LENGTH_SHORT).show();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);
        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);

        fab= (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);



         Admin_ID = auto.getString("inputId","아이디오류");
         Admin_PWD = auto.getString("inputPwd","비밀번호오류");
         Admin_MNO = auto.getString("inputNO","회원번호오류");
         Admin_Area = auto.getString("inputArea","지역오류");
         Admin_Name = auto.getString("inputName","이름오류");

        text_list = (TextView)findViewById(R.id.text_list);
        text_list.setVisibility(View.GONE);

        setTitle(Admin_Area +"의 목록");

        ListView listview ;
        DeliveryViewAdapter adapter;

        // Adapter 생성
        adapter = new DeliveryViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);


        //////////////////


        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "DeliveryList");
        params.put("AREA", Admin_Area);


        String msg = null;
        try {
            msg = networkTask.execute(params).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("흠...", msg);

        //모델에 데이터 담기
        Gson gson = new Gson();

        ///////
        TypeToken<List<DeliveryVO>> typeToken = new TypeToken<List<DeliveryVO>>() {};
        List<DeliveryVO> deliverylist = gson.fromJson(msg, typeToken.getType());

        if (deliverylist.isEmpty()){
            text_list.setVisibility(View.VISIBLE);
        }


        for (DeliveryVO deliveryVO : deliverylist) {
            //list2의 값을 TestVO에 넣는다

/*
            Log.i("테스트주소1...", TestVO.getId());
            Log.i("테스트주소2...", TestVO.getName());
            Log.i("테스트주소3...", TestVO.getNumbers());
            Log.i("테스트주소4...", TestVO.getPhone());
            Log.i("테스트주소5...", TestVO.getAddress());*/


            adapter.addItem(deliveryVO.getWaybill_number(), deliveryVO.getProduct_name(), deliveryVO.getRe_addr1(),deliveryVO.getRe_addr2(),deliveryVO.getRe_addr3()) ;




        }



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                DeliveryViewItem item = (DeliveryViewItem) parent.getItemAtPosition(position) ;


                String numStr = item.getNumStr();
                String nameStr = item.getNameStr();
                String addr1Str = item.getAddr1Str();
                String addr2Str = item.getAddr2Str();
                String addr3Str = item.getAddr3Str();

                Intent intent = new Intent(Delivery_List_Activity.this, Delivery_Detail_Activity.class);
                intent.putExtra("WAYBILL_NUMBER",numStr);
                startActivity(intent);
                finish();

            }
        }) ;

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                Intent intent = new Intent(Delivery_List_Activity.this, barcode_Activity.class);
                startActivity(intent);
                finish();

                break;
        }


    }
}
