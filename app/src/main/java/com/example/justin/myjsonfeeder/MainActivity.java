package com.example.justin.myjsonfeeder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView output;
    String stringURL = "http://john.petra.ac.id/~justin/finger.php?s=";
    List<String> data = new ArrayList<String>();
    EditText cariUser;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            Log.d("STATE", savedInstanceState.toString());
        }
    }

    public void cariUser(View view) {
        requestQueue = Volley.newRequestQueue(this);
        cariUser = (EditText) findViewById(R.id.searchKey);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, stringURL + cariUser.getText().toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data.clear();
                        try {

                            JSONArray ja = response.getJSONArray("hasil");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);

                                // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                String login = jsonObject.getString("login");
                                String nama = jsonObject.getString("nama");

                                data.add("Login: " + login + "\nNama: " + nama + "\n");
//                                data += "Number " + (i + 1) + " \n Login= " + login + " \n Nama= " + nama + " \n\n\n\n ";
                            }

                            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.mylist, R.id.jsonData, data);
                            ListView myList = (ListView) findViewById(R.id.theList);
                            myList.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());

                    }
                }
        );
        requestQueue.add(jor);
    }

}