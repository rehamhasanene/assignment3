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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

public class siggup extends AppCompatActivity {
    private EditText firstname_signup;
    private EditText secendname_signup;
    private EditText email_signup;
    private EditText password_signup;
    private Button btn_sign_up;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestQueue = Volley.newRequestQueue(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siggup);

        firstname_signup = findViewById(R.id.firstname_signup);
        secendname_signup = findViewById(R.id.secendname_signup);
        email_signup = findViewById(R.id.email_signup);
        password_signup = findViewById(R.id.password_signup);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String data = "{" + "\"firstName\"" + ":" + "\"" + firstname_signup.getText().toString() + "\"," +
                        "\"secondName\"" + ":" + "\"" + secendname_signup.getText().toString() + "\"," +
                        "\"email\"" + ":" + "\"" + email_signup.getText().toString() + "\"," +
                        "\"password\"" + "" + "\"" + password_signup.getText().toString() + "\"" +
                        "}";
                Send(data);
            }
        });
    }

    private void Send(String data) {

        String savedata = data;

        String URL = "https://mcc-users-api.herokuapp.com/add_new_user";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: " + requestQueue);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    Log.d("TAG", "onResponse: " + obj.toString());

                } catch (JSONException e) {

                    Log.d("TAG", "Server Error");

                }
            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);

            }
        }) {


            @Override

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                Log.d("TAG", "savedata: " + savedata);

                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);

    }

    public void registerAccount(View view) {

        Intent intent = new Intent(siggup.this, singin.class);

        startActivity(intent);


    }
}