package cr.ac.itcr.tecweather.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cr.ac.itcr.tecweather.R;
import cr.ac.itcr.tecweather.app.EndPoints;
import cr.ac.itcr.tecweather.app.MyApplication;
import cr.ac.itcr.tecweather.model.Weather;


public class TodayTab extends Fragment {
    private String TAG = Weather.class.getSimpleName();
    private SwipeRefreshLayout swipeContainer;

    ImageView today;
    TextView grados;
    TextView humedad;
    TextView luz;
    TextView velocidad;
    TextView fecha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_today_tab, container, false);
        today = (ImageView)view.findViewById(R.id.estadoDia);
        grados = (TextView)view.findViewById(R.id.gradosToday);
        humedad = (TextView)view.findViewById(R.id.humedadToday);
        luz = (TextView)view.findViewById(R.id.luzToday);
        velocidad = (TextView)view.findViewById(R.id.velocidadToday);
        fecha = (TextView)view.findViewById(R.id.fechaToday);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getBrief();
        return  view;
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        getBrief();
        swipeContainer.setRefreshing(false);

    }

    public void getBrief(){
        String endPoint = EndPoints.USER.replace("ID/LIMIT", "developmentStation/1");

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


                            String temp = Float.toString(resumen.getLong("temperatureC")) + "°C";
                            String veloc = Float.toString(resumen.getLong("windspeedmph")) + " mph";
                            String humed = Double.toString(resumen.getDouble("humidity")) + "%";
                            String lumi = Double.toString(resumen.getDouble("light")) + " cd";

                            //convertir formato de milisegundos de fecha
                            // Create a DateFormatter object for displaying date in specified format.
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                            // Create a calendar object that will convert the date and time value in milliseconds to date.
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(resumen.getLong("date"));
                            String time = formatter.format(calendar.getTime());
                            //--------------------------End Fecha-------------------------------------/

                            grados.setText(temp);
                            velocidad.setText(veloc);
                            humedad.setText(humed);
                            fecha.setText(time);
                            luz.setText(lumi);


                            Boolean isNight;
                            Calendar cal = Calendar.getInstance();
                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                            isNight = hour < 6 || hour > 18;
                            if(isNight){
                                today.setImageResource(R.drawable.noght);
                            } else {
                                today.setImageResource(R.drawable.day);
                            }


                           /* Calendar now = Calendar.getInstance();
                            if(now.get(Calendar.AM_PM) == Calendar.PM){

                            }
                            else{

                            }*/



                        }

                    } else {
                        // error in fetching chat rooms
                        Toast.makeText(getContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
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
