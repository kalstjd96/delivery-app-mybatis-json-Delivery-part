package com.example.admin.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.admin.DeliveryList.Delivery_List_Activity;
import com.example.admin.admin.DeliveryList.barcode_Activity;
import com.example.admin.admin.LockerList.Locker_Activity;
import com.example.admin.admin.LockerList.Locker_List_Activity;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.ReservationList.Reservation_List_Activity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Menu_Activity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Toast.makeText(this,"홈 버튼 터치",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_btn2:
                Intent intent = new Intent(Menu_Activity.this, MainActivity.class);
                startActivity(intent);
                SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(Menu_Activity.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                finish();

                return true;

        }
        return (super.onOptionsItemSelected(item));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

       // getSupportActionBar().setTitle("TEST");

       // getSupportActionBar().setIcon(R.drawable.ic_icons);

       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btn_barcode = (Button) findViewById(R.id.btn_barcode);
        Button btn_List = (Button) findViewById(R.id.btn_List);
        Button btn_NFC = (Button)findViewById(R.id.btn_NFC);
       // Button btn_logout = (Button) findViewById(R.id.btn_logout);

        btn_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, barcode_Activity.class);
                startActivity(intent);
            }
        });


        btn_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Delivery_List_Activity.class);
                startActivity(intent);
            }
        });

        btn_NFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, Reservation_List_Activity.class);
                startActivity(intent);
            }
        });
      /*  btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Activity.this, MainActivity.class);
                startActivity(intent);
                SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(Menu_Activity.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });*/

    }


}
