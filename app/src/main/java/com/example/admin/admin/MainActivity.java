package com.example.admin.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.admin.DeliveryList.Delivery_Detail_Activity;
import com.example.admin.admin.LockerList.Locker_Activity;
import com.example.admin.admin.LockerList.Locker_List_Activity;
import com.example.admin.admin.Model.AdminVO;
import com.example.admin.admin.Network.NetworkTask;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private AdminVO adminVO;
    EditText id, pwd;
    Button login_btn, register_btn;
    String loginId, loginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스마트 관리자");

        id = (EditText)findViewById(R.id.login_id);
        pwd = (EditText)findViewById(R.id.login_pw);
        login_btn= (Button)findViewById(R.id.login_btn);
        //register_btn=(Button)findViewById(R.id.register_btn);
        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);

        //MainActivity로 들어왔을 때 loginId와 loginPwd값을 가져와서 null이 아니면
        //값을 가져와 id가 부르곰이고 pwd가 네이버 이면 자동적으로 액티비티 이동.
        if(loginId !=null && loginPwd != null) {

            Intent intent = new Intent(MainActivity.this, Menu_Activity.class);
            startActivity(intent);
            finish();

        }
        //id와 pwd가 null이면 Mainactivity가 보여짐.
        else if(loginId == null && loginPwd == null){
            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String User_id = id.getText().toString().trim();
                    String User_pass = pwd.getText().toString().trim();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Mapping","Admin_Login");
                    params.put("User_id", User_id );
                    params.put("User_pass", User_pass );

                    NetworkTask networkTask = new NetworkTask();
                    String msg = null;
                    try {
                        msg = networkTask.execute(params).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("흠...", msg);

                    Gson gson = new Gson();
                    adminVO = gson.fromJson(msg, AdminVO.class);


                    if (adminVO.getCourier_no().equals("fail")){
                        id.setText("");
                        pwd.setText("");
                        Toast.makeText(MainActivity.this, "회원정보가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if (User_id.equals(adminVO.getId()) && User_pass.equals(adminVO.getPass())){
                        Log.i("테스트아이디...", adminVO.getId());
                        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
                        //아이디가 '부르곰'이고 비밀번호가 '네이버'일 경우 SharedPreferences.Editor를 통해
                        //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputId", adminVO.getId());
                        autoLogin.putString("inputPwd", adminVO.getPass());
                        autoLogin.putString("inputNO", adminVO.getCourier_no());
                        autoLogin.putString("inputArea", adminVO.getArea());
                        autoLogin.putString("inputName", adminVO.getName());
                        //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ

                        autoLogin.commit();
                        Intent intent = new Intent(MainActivity.this, Menu_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        id.setText("");
                        pwd.setText("");
                        Toast.makeText(MainActivity.this, "회원정보가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    }


                }
            });


           /* register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Delivery_Detail_Activity.class);
                    startActivity(intent);//액티비티 띄우기
                    finish();
                }
            });*/
        }

    }


}
