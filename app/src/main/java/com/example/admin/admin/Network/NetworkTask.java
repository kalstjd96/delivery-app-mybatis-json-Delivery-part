package com.example.admin.admin.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.admin.Network.HttpClient;

import java.util.Map;


public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {



        @Override
        protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업


            HttpClient.Builder http = new HttpClient.Builder("POST", "http://61.105.148.89:8080/doc/" + maps[0].get("Mapping"));



// Parameter 를 전송한다.
            http.addAllParameters(maps[0]);


//Http 요청 전송
            HttpClient post = http.create();
            post.request();

// 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

// 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s ) {
            Log.d("JSON_RESULT", s);


        }
    }

