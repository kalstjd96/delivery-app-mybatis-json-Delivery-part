package com.example.admin.admin.LockerList;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.admin.Model.ListViewItem;
import com.example.admin.admin.Model.LockerVO;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Locker_DetailList_Activity extends AppCompatActivity {

    String Product;
    TextView txt_test3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_detail_list);


        Intent intent = getIntent();
        String LNO = intent.getExtras().getString("LNO");
        final String WAYBILL_NUMBER = intent.getExtras().getString("WAYBILL_NUMBER");



        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);


        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "LockerDetailList");
        params.put("LNO", LNO);


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

        TypeToken<List<LockerVO>> typeToken = new TypeToken<List<LockerVO>>() {};
        List<LockerVO> lockerlist = gson.fromJson(msg, typeToken.getType());
        setTitle(lockerlist.get(0).getL_name());

        for (LockerVO lockerVO : lockerlist) {
            //list2의 값을 TestVO에 넣는다

/*
            Log.i("테스트주소1...", TestVO.getId());
            Log.i("테스트주소2...", TestVO.getName());
            Log.i("테스트주소3...", TestVO.getNumbers());
            Log.i("테스트주소4...", TestVO.getPhone());
            Log.i("테스트주소5...", TestVO.getAddress());*/

            //물건체크
            switch(lockerVO.getProduct_chk()){
                case "I" : // 물건들어있으시
                    Product = "보관함에 물건이 들어있습니다.";

                    adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_lockerin),
                            lockerVO.getSno(), lockerVO.getL_name(),lockerVO.getDetail_no()+"번 사물함",Product,"I") ;
                    break;
                case "O" : //없을시
                    Product = "보관함에 물건이 들어있지않습니다.";
                    adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_locker),
                            lockerVO.getSno(), lockerVO.getL_name(),lockerVO.getDetail_no()+"번 사물함",Product,"O") ;
                    break;
                default:
                    Product = "오류!!";
            }







        }


//        // 첫 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icon1),
//                "1", "XX아파트 101동 사물함","12345","경기도 화성시 XX동 XX아파트","101동") ;
//        // 두 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icon1),
//                "2", "XX아파트 102동 사물함","12345","경기도 화성시 XX동 XX아파트","102동") ;
//        // 세 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icon2),
//                "3", "XX아파트 103동 사물함","12345","경기도 화성시 XX동 XX아파트","103동") ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;


                String snoStr = item.getLnoStr();
                String nameStr = item.getNameStr();
                String addr1Str = item.getAddr1Str();
                String addr2Str = item.getAddr2Str();
                String addr3Str = item.getAddr3Str();



                Intent intent = new Intent(Locker_DetailList_Activity.this, Locker_Activity.class);
                intent.putExtra("SNO",snoStr);
                intent.putExtra("WAYBILL_NUMBER",WAYBILL_NUMBER);
                startActivity(intent);

            }
        }) ;
    }
}
