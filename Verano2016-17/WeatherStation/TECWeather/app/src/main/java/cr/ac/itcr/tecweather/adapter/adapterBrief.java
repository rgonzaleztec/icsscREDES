package cr.ac.itcr.tecweather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cr.ac.itcr.tecweather.R;
import cr.ac.itcr.tecweather.app.EndPoints;
import cr.ac.itcr.tecweather.app.MyApplication;
import cr.ac.itcr.tecweather.model.Weather;

/**
 * Created by usuario on 22/12/2016.
 */
public class adapterBrief extends BaseAdapter {


    private String TAG = BaseAdapter.class.getSimpleName();
    LayoutInflater minflater;
    ArrayList<Weather> lista;

    public adapterBrief(Context context, ArrayList<Weather> lista) {
        minflater = LayoutInflater.from(context);
        this.lista=lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.listviewbrief_template, null);

        }
        final TextView temperatura = (TextView) convertView.findViewById(R.id.grados);
        final TextView velocidadmph = (TextView) convertView.findViewById(R.id.velocidad);
        final TextView humedad = (TextView) convertView.findViewById(R.id.humedad);
        final TextView fecha_hora = (TextView) convertView.findViewById(R.id.fecha);
        final ImageView estadoTiempo = (ImageView) convertView.findViewById(R.id.imagenEstado);

        String temp = Float.toString(lista.get(position).getTempGrados()) + "°C";
        String veloc = Float.toString(lista.get(position).getVelocidadVientomph()) + "mph";
        String humed = Float.toString(lista.get(position).getHumedad()) + "%";


        //convertir formato de milisegundos de fecha
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lista.get(position).getFecha());
        String time = formatter.format(calendar.getTime());
        //--------------------------End Fecha-------------------------------------/

        temperatura.setText(temp);
        velocidadmph.setText(veloc);
        humedad.setText(humed);
        fecha_hora.setText(time);
        estadoTiempo.setImageResource(R.drawable.rain_icon);


        return convertView;
    }
}
