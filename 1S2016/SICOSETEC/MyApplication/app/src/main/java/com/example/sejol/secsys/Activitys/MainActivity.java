package com.example.sejol.secsys.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sejol.secsys.Adapters.FragmentDrawer;
import com.example.sejol.secsys.Clases.Tag;
import com.example.sejol.secsys.Clases.Usuario;
import com.example.sejol.secsys.NavigationOptions.CrearRutasFragment;
import com.example.sejol.secsys.NavigationOptions.DescargarReportesFragment;
import com.example.sejol.secsys.NavigationOptions.RealizarRutasFragment;
import com.example.sejol.secsys.R;
import com.example.sejol.secsys.Utilidades.NFC_Controller;
import com.example.sejol.secsys.Utilidades.SQLite_Controller;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    Fragment fragment = null; // Fragment de las opciones de drawer mostrandose
    boolean read = false; // Flag para indicar si el fragment es utilizado para lecturas de NFC
    private NFC_Controller nfcController; // Controlador de NFC
    int first;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        mToolbar.setTitle(TAG);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nfcController = new NFC_Controller(this, 3);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");

        if (nfcController.mNfcAdapter == null) {
            Toast.makeText(this, "Su dispositivo no soporta la tecnologia NFC", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Setting navigation drawer
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0); //Display option 0 o the navigation drawer
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcController.enableForegroundDispatchSystem();
    }

    @Override protected void onPause() {
        super.onPause();
        nfcController.disableForegroundDispatchSystem();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void displayView(int position) {
        //Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                read = true;
                first = 0;
                fragment = new RealizarRutasFragment();
                title = getString(R.string.title_adm_realizar);
                break;
            case 1:
                read = false;
                fragment = new CrearRutasFragment();
                title = getString(R.string.title_adm_crear);
                break;
            case 2:
                read = false;
                fragment = new DescargarReportesFragment();
                title = getString(R.string.title_adm_descargar);
                break;
            case 3:
                finish();
                break;
            default:
                break;
        }

        // Do fragment display transaction
        if (fragment != null) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(read) {
            RealizarRutasFragment frag = (RealizarRutasFragment)fragment;
            SQLite_Controller db = new SQLite_Controller(this);
            ArrayList<Tag> tag = db.getTagsDeRuta();
            Tag lectura = db.getTagsDeRutaPorCodigo(nfcController.leerPunto(intent));
            frag.ActualizarRonda(lectura);

            //tx = (TextView) fragment.getView().findViewById(R.id.fralgo);
            //lista.add(nfcController.leerPunto(intent));

            //setUpFragmentRealizarRutas();
            //lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista));

            //Toast.makeText(this,nfcController.leerPunto(intent),Toast.LENGTH_SHORT).show();
        }
    }

}
