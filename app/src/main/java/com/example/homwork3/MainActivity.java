package com.example.homwork3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    String email;
    String password;
    RequestQueue requestQueue;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        getRegTokent();


    }

    private void getRegTokent() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                }else {
                    token = task.getResult();
                    Send(token);


                }
            }
        });

    }



    private void Send(String token) {
        String URL = "https://mcc-users-api.herokuapp.com/add_reg_token";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: " + requestQueue);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    Log.d("TAGMain", "onResponse: " + objres.toString());

                } catch (JSONException e) {

                    Log.d("TAGMain", "Server Error ");

                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);

            }

        }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
                map.put("reg_token", token);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}