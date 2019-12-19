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

public class Locker_List_Activity extends AppCompatActivity {

    TextView txt_test1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_list);


        Intent intent = getIntent(); /*데이터 수신*/

        final String WAYBILL_NUMBER = intent.getExtras().getString("WAYBILL_NUMBER"); /*String형*/



        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);


        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "LockerList");
        params.put("WAYBILL_NUMBER", WAYBILL_NUMBER);


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
        setTitle(lockerlist.get(0).getL_addr2());

        for (LockerVO lockerVO : lockerlist) {
            //list2의 값을 TestVO에 넣는다

/*
            Log.i("테스트주소1...", TestVO.getId());
            Log.i("테스트주소2...", TestVO.getName());
            Log.i("테스트주소3...", TestVO.getNumbers());
            Log.i("테스트주소4...", TestVO.getPhone());
            Log.i("테스트주소5...", TestVO.getAddress());*/


            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_apt),
                    lockerVO.getLno(), lockerVO.getL_name(),"우편번호: "+lockerVO.getL_addr1(),lockerVO.getL_addr2(),lockerVO.getL_addr3()) ;


        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;


                String lnoStr = item.getLnoStr();
                String nameStr = item.getNameStr();
                String addr1Str = item.getAddr1Str();
                String addr2Str = item.getAddr2Str();
                String addr3Str = item.getAddr3Str();



                Intent intent = new Intent(Locker_List_Activity.this, Locker_DetailList_Activity.class);
                intent.putExtra("LNO",lnoStr);
                intent.putExtra("WAYBILL_NUMBER",WAYBILL_NUMBER);
                startActivity(intent);

            }
        }) ;

    }
}
