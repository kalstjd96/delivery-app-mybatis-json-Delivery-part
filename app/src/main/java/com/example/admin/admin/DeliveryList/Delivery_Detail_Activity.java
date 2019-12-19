package com.example.admin.admin.DeliveryList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.admin.Menu_Activity;
import com.example.admin.admin.Model.DeliveryVO;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Delivery_Detail_Activity extends AppCompatActivity implements View.OnClickListener {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab;

    String COURIER_NO,ADDR;
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
        setContentView(R.layout.activity_delivery_detail);
        setTitle("배송상세");

        fab= (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);


        TextView text_send_name = (TextView)findViewById(R.id.text_send_name);
        TextView text_send_phone = (TextView)findViewById(R.id.text_send_phone);
        TextView text_send_addr1 = (TextView)findViewById(R.id.text_send_addr1);
        TextView text_send_addr2 = (TextView)findViewById(R.id.text_send_addr2);
        TextView text_send_addr3 = (TextView)findViewById(R.id.text_send_addr3);

        TextView text_recv_name = (TextView)findViewById(R.id.text_recv_name);
        TextView text_recv_phone = (TextView)findViewById(R.id.text_recv_phone);
        TextView text_recv_addr1 = (TextView)findViewById(R.id.text_recv_addr1);
        TextView text_recv_addr2 = (TextView)findViewById(R.id.text_recv_addr2);
        TextView text_recv_addr3 = (TextView)findViewById(R.id.text_recv_addr3);

        TextView text_product_req = (TextView)findViewById(R.id.text_product_req);
        TextView text_product_name = (TextView)findViewById(R.id.text_product_name);
        TextView text_product_price = (TextView)findViewById(R.id.text_product_price);
        TextView text_product_weight = (TextView)findViewById(R.id.text_product_weight);
        TextView text_product_fare = (TextView)findViewById(R.id.text_product_fare);
        TextView text_product_fareprice = (TextView)findViewById(R.id.text_product_fareprice);

        Button btn_start = (Button)findViewById(R.id.btn_start);

        Intent intent = getIntent();
        final String WAYBILL_NUMBER = intent.getExtras().getString("WAYBILL_NUMBER");

        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
        COURIER_NO = auto.getString("inputNO","택배기사오류오류");
        ADDR = auto.getString("inputArea","주소오류오류");

        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "DeliveryDetail");
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
        DeliveryVO deliveryVO = gson.fromJson(msg, DeliveryVO.class);

        text_send_name.setText(deliveryVO.getSe_name());
        text_send_phone.setText(deliveryVO.getSe_phone());
        text_send_addr1.setText(deliveryVO.getSe_addr1());
        text_send_addr2.setText(deliveryVO.getSe_addr2());
        text_send_addr3.setText(deliveryVO.getSe_addr3());

        text_recv_name.setText(deliveryVO.getRe_name());
        text_recv_phone.setText(deliveryVO.getRe_phone());
        text_recv_addr1.setText(deliveryVO.getRe_addr1());
        text_recv_addr2.setText(deliveryVO.getRe_addr2());
        text_recv_addr3.setText(deliveryVO.getRe_addr3());

        text_product_req.setText(deliveryVO.getSe_req());
        text_product_name.setText(deliveryVO.getProduct_name());
        text_product_price.setText(deliveryVO.getProduct_price());

        //추가및 수정
        switch (deliveryVO.getProduct_fare_price()){
            case "4000":
                text_product_weight.setText("극소 (80cm, 2kg)");
                break;
            case "6000":
                text_product_weight.setText("소 (100cm, 5kg)");
                break;
            case "7000":
                text_product_weight.setText("중 (120cm, 15kg)");
                break;
            case "8000":
                text_product_weight.setText("대 (160cm, 25kg)");
                break;
            default:
                break;
        }


        if (deliveryVO.getProduct_fare().equals("1")){
            text_product_fare.setText("선불");
        } else if (deliveryVO.getProduct_fare().equals("2")){
            text_product_fare.setText("착불");
        } else {
            text_product_fare.setText("오류");
        }
        text_product_fareprice.setText(deliveryVO.getProduct_fare_price());

        //추가및 수정
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetworkTask networkTask = new NetworkTask();
                Map<String, String> params = new HashMap<String, String>();

                params.put("Mapping", "DeliveryStrat");
                params.put("WAYBILL_NUMBER", WAYBILL_NUMBER);
                params.put("COURIER_NO", COURIER_NO);
                params.put("ADDR", ADDR);

                String msg = null;
                try {
                    msg = networkTask.execute(params).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("흠...", msg);
                Toast.makeText(getApplicationContext(), "상품 배송시작", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(Delivery_Detail_Activity.this, Delivery_List_Activity.class);
                startActivity(intent2);
                finish();
            }
        });





    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                Intent intent = new Intent(Delivery_Detail_Activity.this, barcode_Activity.class);
                startActivity(intent);
                finish();

                break;
        }
    }
}
