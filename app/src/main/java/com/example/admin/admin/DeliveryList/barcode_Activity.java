package com.example.admin.admin.DeliveryList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.admin.LockerList.Locker_Activity;
import com.example.admin.admin.LockerList.Locker_List_Activity;
import com.example.admin.admin.Menu_Activity;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class barcode_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        IntentIntegrator intentIntegrator = new IntentIntegrator(barcode_Activity.this);
        intentIntegrator.setBeepEnabled(true);//바코드 인식시 소리
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                //result.getContents() <<바코드값을 웹서버로 보내서 사용자확인후
                //있으면 강제로 사물함선택, 없으면 사물함선택화면

                Map<String, String> params = new HashMap<String, String>();
                params.put("Mapping", "Barcode");
                params.put("WAYBILL_NUMBER", result.getContents());

                NetworkTask networkTask = new NetworkTask();
                String msg = null;

                try {
                    msg = networkTask.execute(params).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("aaa", msg);

                if(msg.equals("fail")){
                    Toast.makeText(this, "존재 하지않는 운송장번호입니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(barcode_Activity.this, Menu_Activity.class);
                    startActivity(intent);
                    finish();
                } else if (msg.equals("overlep")){
                    Toast.makeText(this, "이미 배송완료된 운송장번호입니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(barcode_Activity.this, Menu_Activity.class);
                    startActivity(intent);
                    finish();
                } else if (msg.equals("none")){
                    //회원이 사물함이 없을시 넘겨줘야돼는값
                    Intent intent = new Intent(barcode_Activity.this, Locker_List_Activity.class);
                    intent.putExtra("WAYBILL_NUMBER",result.getContents());
                    startActivity(intent);
                    finish();

                    Log.i("bbb",msg);
                } else {
                    //회원이 사물함을 가지고있으면 사물함번호 넘겨줘야됌
                    Intent intent = new Intent(barcode_Activity.this, Locker_Activity.class);
                    intent.putExtra("WAYBILL_NUMBER",result.getContents());
                    intent.putExtra("SNO",msg);
                    startActivity(intent);
                    finish();

                    Log.i("ccc",msg);
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
