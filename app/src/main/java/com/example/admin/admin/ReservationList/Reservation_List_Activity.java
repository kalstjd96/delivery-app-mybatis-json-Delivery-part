package com.example.admin.admin.ReservationList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.admin.DeliveryList.DeliveryViewAdapter;
import com.example.admin.admin.DeliveryList.Delivery_Detail_Activity;
import com.example.admin.admin.DeliveryList.barcode_Activity;
import com.example.admin.admin.Model.DeliveryVO;
import com.example.admin.admin.Model.DeliveryViewItem;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Reservation_List_Activity extends AppCompatActivity implements View.OnClickListener{

    private Boolean isFabOpen = false;
    private FloatingActionButton fab;

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
        setContentView(R.layout.activity_reservation_list);

        fab= (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);

        String Admin_ID = auto.getString("inputId","아이디오류");
        String Admin_PWD = auto.getString("inputPwd","비밀번호오류");
        String Admin_MNO = auto.getString("inputNO","회원번호오류");
        String Admin_Area = auto.getString("inputArea","지역오류");
        String Admin_Name = auto.getString("inputName","이름오류");

        TextView text_list = (TextView)findViewById(R.id.text_list);
        text_list.setVisibility(View.GONE);

        setTitle(Admin_Area +"의 예약목록");

        ListView listview ;
        DeliveryViewAdapter adapter;

        // Adapter 생성
        adapter = new DeliveryViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);


        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "Reservation_List");
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
        final List<DeliveryVO> deliverylist = gson.fromJson(msg, typeToken.getType());

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


            adapter.addItem(deliveryVO.getWaybill_number(), deliveryVO.getProduct_name(), deliveryVO.getRe_addr1(),deliveryVO.getDelivery_location(),"") ;




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

                Intent intent = new Intent(Reservation_List_Activity.this, Reservation_Locker_Activity.class);
                intent.putExtra("WAYBILL_NUMBER",numStr);
                intent.putExtra("SNO",deliverylist.get(position).getSno());
                startActivity(intent);

            }
        }) ;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                Intent intent = new Intent(Reservation_List_Activity.this, barcode_Activity.class);
                startActivity(intent);
                finish();

                break;
        }
    }
}
