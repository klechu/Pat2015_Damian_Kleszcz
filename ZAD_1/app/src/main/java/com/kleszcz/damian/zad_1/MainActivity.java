package com.kleszcz.damian.zad_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;

import android.widget.ListAdapter;
import android.widget.ListView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;

import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MainActivity extends Activity {
    private final static String BASE_SERVER_URL = "http://klechu.prv.pl/";
    private ProgressDialog pDialog;
    private static final String TAG_NAME = "array";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESC = "desc";
    private static final String TAG_URL = "url";

    private ArrayList<HashMap<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<HashMap<String, String>>();
        TextView text = (TextView) findViewById(R.id.text);



        if (isOnline())
            new GetjArray().execute();
        else
            text.setText(R.string.disconnect);

    }

    private class GetjArray extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getString(R.string.message));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler serviceHandler = new ServiceHandler();

            String jString = serviceHandler.makeServiceCall(BASE_SERVER_URL+"page_0.json");

            Log.d("Response: ", "> " + jString);

            if (jString != null) {
                try {
                    JSONObject jObject = new JSONObject(jString);
                    JSONArray jArray = jObject.getJSONArray(TAG_NAME);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        String title = oneObject.getString(TAG_TITLE);
                        String desc = oneObject.getString(TAG_DESC);
                        String url = oneObject.getString(TAG_URL);
                        HashMap<String, String> jHashMap = new HashMap<String, String>();

                        jHashMap.put(TAG_TITLE, title);
                        jHashMap.put(TAG_DESC, desc);
                        jHashMap.put(TAG_URL, url);


                        arrayList.add(jHashMap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListView listView = (ListView) findViewById(R.id.listView);

            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, arrayList,
                    R.layout.list_item, new String[] { TAG_TITLE, TAG_DESC/*, TAG_URL*/ },
                                        new int[] { R.id.title, R.id.desc/*, R.id.url*/ });

            listView.setAdapter(adapter);

        }

    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




    public void logOut(View view){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("sign",false).apply();
        Intent intent;
        intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
        MainActivity.this.finish();
    }


}
