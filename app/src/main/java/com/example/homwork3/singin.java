package com.example.homwork3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class singin extends AppCompatActivity {

    RequestQueue requestQueue;
    private EditText name_signin, paswerd_signin;
    private Button btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        name_signin = findViewById(R.id.name_signin);
        paswerd_signin = findViewById(R.id.paswerd_signin);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                String data = "{" +
                        "\"email\"" + ":" + "\"" + name_signin.getText().toString() + "\", " + "\"password\""
                        + ":" + "\"" + paswerd_signin.getText().toString() + "\"" +
                        "}";

                Send(data);
            }
        });

    }
    private void Send(String data) {
        String savedata = data;

        String URL = "https://mcc-users-api.herokuapp.com/login";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: " + requestQueue);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try {

                    JSONObject objres = new JSONObject(response);

                    Log.d("TAG Sign In", "onResponse: " + objres.toString());

                    Intent intent = new Intent(singin.this, MainActivity.class);
                    intent.putExtra("email", name_signin.getText().toString());
                    intent.putExtra("password", paswerd_signin.getText().toString());
                    startActivity(intent);
                } catch (JSONException e) {

                    Log.d("TAG Sign In", "Server Error ");
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("TAG Sign In", "onErrorResponse: " + error);
            }
        }) {

            @Override

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override

            public byte[] getBody() throws AuthFailureError {

                try {

                    Log.d("TAG", "savedata: " + savedata);
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);

    }
}