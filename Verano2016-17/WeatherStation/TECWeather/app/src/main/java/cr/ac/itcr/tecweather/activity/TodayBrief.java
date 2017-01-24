package cr.ac.itcr.tecweather.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cr.ac.itcr.tecweather.R;
import cr.ac.itcr.tecweather.adapter.adapterBrief;
import cr.ac.itcr.tecweather.app.EndPoints;
import cr.ac.itcr.tecweather.app.MyApplication;
import cr.ac.itcr.tecweather.model.User;
import cr.ac.itcr.tecweather.model.Weather;

public class TodayBrief extends Fragment {


    private String TAG = Weather.class.getSimpleName();
    private ArrayList<Weather> WeatherArrayList;
    private adapterBrief wAdapter;
    ListView lvlWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_today_brief, container, false);


        WeatherArrayList = new ArrayList<>();

        ListView listV = (ListView)view.findViewById(R.id.lvlBriefToday);

        getBrief();

        wAdapter = new adapterBrief(getContext(),WeatherArrayList);
        listV.setAdapter(wAdapter);


        return view;
    }



    public void getBrief(){
        String endPoint = EndPoints.USER.replace("ID/LIMIT", "developmentStation/5");

        StringRequest strReq = new StringRequest(Request.Method.GET,
                endPoint, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getJSONArray("data") != null) {
                        JSONArray eventoArraylist = obj.getJSONArray("data");
                        for (int i = 0; i < eventoArraylist.length(); i++) {
                            JSONObject resumen = (JSONObject) eventoArraylist.get(i);
                            Weather cr = new Weather();
                            cr.setTempGrados(resumen.getLong("temperatureC"));
                            cr.setHumedad(resumen.getLong("humidity"));
                            cr.setVelocidadVientomph(resumen.getLong("windspeedmph"));
                            cr.setFecha(resumen.getLong("date"));

                            WeatherArrayList.add(cr);
                        }

                    } else {
                        // error in fetching chat rooms
                        Toast.makeText(getContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                wAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "VolleyAdmin error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getContext(), "VolleyAdmin error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

}
