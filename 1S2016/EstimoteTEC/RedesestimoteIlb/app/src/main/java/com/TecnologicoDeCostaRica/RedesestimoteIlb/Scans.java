package com.TecnologicoDeCostaRica.RedesestimoteIlb;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Scans extends AppCompatActivity {
    ArrayList<ScanDTO> scanList = new ArrayList<ScanDTO>();
    ListView lista ;

    private class  NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(tag.toString(), "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {


                for(int i=0;i<scanList.size();i++){
                    if(result.equals(scanList.get(i).getNombre())){
                        Toast.makeText(Scans.this, "tag ya registrado", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(Scans.this, "tag detectado", Toast.LENGTH_SHORT).show();
                scanList.add(new ScanDTO("" + result, "24/7/16", "6:01", false));
                leerfire(result.toString());
            }
        }
    }


    public class AdapterView extends BaseAdapter{
        LayoutInflater minflater;

        public AdapterView(Context context) {
            minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return scanList.size();
        }

        @Override
        public Object getItem(int position) {
            return scanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=minflater.inflate(R.layout.list_items,null);

            }
            final TextView name= (TextView) convertView.findViewById(R.id.Listitem_name);
            name.setText(scanList.get(position).getNombre());
            final TextView hora= (TextView) convertView.findViewById(R.id.Listitem_hora);
            hora.setText(scanList.get(position).getHora());
            final TextView fecha= (TextView) convertView.findViewById(R.id.Listitem_fecha);
            final TextView state= (TextView) convertView.findViewById(R.id.Listitem_state);
            fecha.setText(scanList.get(position).getFecha());
            final ImageView in_out =(ImageView) convertView.findViewById(R.id.Listitem_in_out);
            if(scanList.get(position).isIn_out()){
                in_out.setBackgroundResource(R.drawable.in);
                state.setText("in");
            }
            else{
                in_out.setBackgroundResource(R.drawable.out);
                state.setText("out");
            }
            return convertView;
        }
    }
    Firebase myFirebaseRef;
    NfcAdapter adapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scans);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://redes.firebaseio.com/");



        lista=(ListView) findViewById(R.id.itemScan);

        test();
        adapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };
    }





    public    void escribirfire(View view) {
       // myFirebaseRef.child(name.getText().toString()).setValue(data.getText().toString());
    }

    public    void leerfire(final String persona) {
        final String Fpersona=persona;
        myFirebaseRef.child(Fpersona).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (int i = 0; i < scanList.size(); i++) {
                    if (scanList.get(i).getNombre().equals(Fpersona)) {
                        if(snapshot.getValue().toString()=="1"){
                            scanList.get(i).setIn_out(true);
                        }else{
                            scanList.get(i).setIn_out(false);
                        }
                        AdapterView a=new AdapterView(Scans.this);
                        lista.setAdapter(a);

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                prin("lectura cancelada");
            }

        });

    }



    void prin(String text) {

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }








    Tag mytag;
    @Override
    protected void onNewIntent(Intent intent){


        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){

            mytag = (Tag)intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

                read(mytag);

                //trywrite();


        }
        if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())){

            mytag = (Tag)intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

                read(mytag);

            //    trywrite();


        }



    }
    void test(){
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "EL censor NFC esta desactivado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "en linea :D", Toast.LENGTH_SHORT).show();
        }
    }


    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {

        //create the message in according with the standard
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;

        byte[] payload = new byte[1 + langLength + textLength];
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }

    private void read(Tag tag) {

        String[] techList = tag.getTechList();
        String searchedTech = Ndef.class.getName();

        for (String tech : techList) {
            if (searchedTech.equals(tech)) {
                new NdefReaderTask().execute(tag);
                break;
            }
        }
    }

    /*void trywrite(){
        try {
            if(mytag==null){
                Toast.makeText(ctx, ctx.getString(R.string.error_detected), Toast.LENGTH_LONG).show();
            }else{
                write(message.getText().toString(),mytag);
                Toast.makeText(ctx, ctx.getString(R.string.ok_writing), Toast.LENGTH_LONG ).show();

            }
        } catch (IOException e) {
            Toast.makeText(ctx, ctx.getString(R.string.error_writing), Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        } catch (FormatException e) {
            Toast.makeText(ctx, ctx.getString(R.string.error_writing) , Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        }
    }
     private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    */
}
