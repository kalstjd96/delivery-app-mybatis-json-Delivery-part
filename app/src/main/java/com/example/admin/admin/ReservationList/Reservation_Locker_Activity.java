package com.example.admin.admin.ReservationList;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.admin.Menu_Activity;
import com.example.admin.admin.Model.LockerVO;
import com.example.admin.admin.Network.NetworkTask;
import com.example.admin.admin.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Reservation_Locker_Activity extends AppCompatActivity {

    TextView txt_addr2,txt_addr3,txt_no;

    String SNO;
    String WAYBILL_NUMBER;
    String COURIER_NO,ADDR;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_locker);
        setTitle("예약보관함 확인");


        Intent intent1 = getIntent(); /*데이터 수신*/

        SNO = intent1.getExtras().getString("SNO"); /*String형*/
        WAYBILL_NUMBER = intent1.getExtras().getString("WAYBILL_NUMBER");

        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);


        COURIER_NO = auto.getString("inputNO","택배기사오류오류");
        ADDR = auto.getString("inputArea","주소오류오류");

        txt_addr2 = (TextView)findViewById(R.id.txt_addr2);
        txt_addr3 = (TextView)findViewById(R.id.txt_addr3);
        txt_no = (TextView)findViewById(R.id.txt_no);


        //NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "LockerDetail");
        params.put("SNO", SNO);



        String msg = null;

        try {
            msg = networkTask.execute(params).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("흠1...", msg);

        //모델에 데이터 담기
        Gson gson = new Gson();

        LockerVO lockerVO = gson.fromJson(msg, LockerVO.class);

        txt_addr2.setText(lockerVO.getL_addr2());
        txt_addr3.setText(lockerVO.getL_addr3());
        txt_no.setText(lockerVO.getDetail_no()+"번 사물함");
    }

    @Override
    protected void onPause() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {

            byte[] tagId = tag.getId();




            Map<String, String> params = new HashMap<String, String>();
            params.put("Mapping", "admin_Reservation_nfc");
            params.put("TagID", toHexString(tagId));
            params.put("SNO", SNO);
            params.put("ADDR", ADDR);
            params.put("COURIER_NO", COURIER_NO);
            params.put("WAYBILL_NUMBER", WAYBILL_NUMBER);



            NetworkTask networkTask = new NetworkTask();
            String msg = null;

            try {
                msg = networkTask.execute(params).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Log.i("흠Locker...", msg);

            if(msg.equals("open")){
                Intent intent2 = new Intent(Reservation_Locker_Activity.this, Menu_Activity.class);
                startActivity(intent2);
                finish();
                Toast.makeText(getApplicationContext(), "열렸습니다. 상품을 확인해주세요.", Toast.LENGTH_LONG).show();
            } else if (msg.equals("close")) {
                Toast.makeText(getApplicationContext(), "해당사물함이 아닙니다. \n 다시한번 확인해주십시오.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "오류..다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }


        }
    }


    public static final String CHARS = "0123456789ABCDEF";

    public static String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            sb.append(CHARS.charAt((data[i] >> 4) & 0x0F))
                    .append(CHARS.charAt(data[i] & 0x0F));
        }
        return sb.toString();
    }
}
