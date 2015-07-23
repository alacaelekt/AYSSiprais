package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class stokekle extends Activity {

    public static final String FIRST_COLUMN = Constant.FIRST_COLUMN;
    public static final String SECOND_COLUMN = Constant.SECOND_COLUMN;
    public static final String THIRD_COLUMN = Constant.THIRD_COLUMN;
    public static final String FOURTH_COLUMN = Constant.FOURTH_COLUMN;
    public static final String INNER_FIRST_COLUMN = Constant.INNER_FIRST_COLUMN;
    public static final String INNER_SECOND_COLUMN = Constant.INNER_SECOND_COLUMN;
    public static final String INNER_THIRD_COLUMN = Constant.INNER_THIRD_COLUMN;
    public static final String INNER_FOURTH_COLUMN = Constant.INNER_FOURTH_COLUMN;
    public static final String INNER_FIFTH_COLUMN = Constant.INNER_FIFTH_COLUMN;

    Spinner spinner_kdv_stokekle;

    String name;
    String birimfiyat;
    String tutar;
    String isk1;
    String isk2;
    String isk3;
    String isk1uyg;
    String isk2uyg;
    String isk3uyg;
    String kdv = "4";
    String birimadi;
    String listesirano;

    boolean clickable = false;

    private ArrayAdapter<String> listviewAdapter4;
    private ArrayList<HashMap> list;
    listviewAdapter4 adapter;
    HashMap hash;

    InputStream is=null;
    String result=null;
    String line=null;

    public String cagiran = "";
    String depolar = "";

    String key = "";

    EditText edit_stok_adi;
    EditText edit_stok_miktar;
    EditText edit_stok_isk1;
    EditText edit_stok_isk2;
    EditText edit_stok_isk3;
    EditText edit_stok_kdv;
    EditText edit_stok_birimfiyat;
    EditText edit_stok_tutar;
    ImageButton button_save;
    public static String classname = "stokekle";
    public static String c_stok_kodu = null;
    public static String c_stok_adi = null;
    public static String stok_kodu = null;

    DecimalFormat formatter = new DecimalFormat("##.##");//("#,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stokekle);

        edit_stok_adi = (EditText) findViewById(R.id.edit_urunadi_stokekle);
        edit_stok_miktar = (EditText) findViewById(R.id.edit_miktar_stokekle);
        edit_stok_miktar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*Toast.makeText(getBaseContext(),
                        ((EditText) v).getId() + " has focus - " + hasFocus,
                        Toast.LENGTH_SHORT).show();*/
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));//(formatter.format(parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                    Float birimFiyat = Float.parseFloat(edit_stok_birimfiyat.getText().toString().replace(",","."));
                    Float miktar = Float.parseFloat(edit_stok_miktar.getText().toString().replace(",","."));
                    Float tutar = birimFiyat * miktar;
                    edit_stok_tutar.setText(String.format("%.2f", tutar));//(formatter.format(tutar));
                }
            }
        });
        edit_stok_isk1 = (EditText) findViewById(R.id.edit_isk1_stokekle);
        edit_stok_isk1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));//(formatter.format(parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                }
            }
        });
        edit_stok_isk2 = (EditText) findViewById(R.id.edit_isk2_stokekle);
        edit_stok_isk2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));//(formatter.format(parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                }
            }
        });
        edit_stok_isk3 = (EditText) findViewById(R.id.edit_isk3_stokekle);
        edit_stok_isk3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));//(formatter.format(parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                }
            }
        });
        //edit_stok_kdv = (EditText) findViewById(R.id.edit_kdv_stokekle);
        edit_stok_birimfiyat = (EditText) findViewById(R.id.edit_birimfiyat_stokekle);
        edit_stok_birimfiyat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));//(formatter.format(parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                    Float birimFiyat = Float.parseFloat(edit_stok_birimfiyat.getText().toString().replace(",","."));
                    Float miktar = Float.parseFloat(edit_stok_miktar.getText().toString().replace(",","."));
                    Float tutar = birimFiyat * miktar;
                    edit_stok_tutar.setText(String.format("%.2f", tutar));//(formatter.format(tutar));
                }
            }
        });
        edit_stok_tutar = (EditText) findViewById(R.id.edit_tutar_stokekle);
        edit_stok_tutar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));//(formatter.format(parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                    Float tutar = Float.parseFloat(edit_stok_tutar.getText().toString().replace(",","."));
                    Float miktar = Float.parseFloat(edit_stok_miktar.getText().toString().replace(",","."));
                    try {
                        Float birimFiyat = tutar / miktar;
                        edit_stok_birimfiyat.setText(String.format("%.2f", birimFiyat));
                    }catch (Exception e){
                        edit_stok_birimfiyat.setText(String.format("%.2f", (float)0));
                        edit_stok_tutar.setText(String.format("%.2f", (float)0));
                    }
                }
            }
        });
        spinner_kdv_stokekle = (Spinner) findViewById(R.id.spinner_kdv_stokekle);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String datas = extras.getString("anahtar");
            cagiran = extras.getString("classname");
            depolar = extras.getString("depolar");
            if (datas != null) {
                key = datas;
            }else{
                key = "**";
            }
        }

        if(c_stok_kodu == null)
        {
            DecimalFormat formatter = new DecimalFormat("##.##");//("#,###.##");
            float parse = Float.parseFloat("0");
            String sifir = String.format("%.2f", parse);//formatter.format(parse);
            parse = Float.parseFloat("1");
            String bir = String.format("%.2f", parse);
            edit_stok_miktar.setText(bir);
            edit_stok_isk1.setText(sifir);
            edit_stok_isk2.setText(sifir);
            edit_stok_isk3.setText(sifir);
            isk1uyg="0";
            isk2uyg="0";
            isk3uyg="0";
            listesirano="1";
            //parse = Float.parseFloat("18");
            //String kdv = formatter.format(parse);
            //edit_stok_kdv.setText(kdv);
            spinner_kdv_stokekle.setSelection(4);
            edit_stok_birimfiyat.setText(sifir);
            edit_stok_tutar.setText(sifir);

            Intent intent = new Intent(stokekle.this, stoklistele.class);
            intent.putExtra("classname", classname);
            intent.putExtra("anahtar", key);
            intent.putExtra("depolar", depolar);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(c_stok_kodu != null) {
            clickable = false;
            edit_stok_adi.setText(c_stok_adi);
            stok_kodu = c_stok_kodu;
            c_stok_kodu = null;
            c_stok_adi = null;
            new Background_confirmation().execute();
        }
    }

    public void stokkoduEvent(View v) {
        Intent intent = new Intent(stokekle.this, stoklistele.class);
        intent.putExtra("classname", classname);
        intent.putExtra("anahtar", "");
        intent.putExtra("depolar", depolar);
        startActivity(intent);
    }

    public void saveEvent(View v) {
        if(clickable== true){
            /*LinearLayout ly = (LinearLayout)findViewById(R.id.linearLayoutFocusable1);
            ly.findFocus();*/
            getCurrentFocus().clearFocus();
            if(cagiran.equals(siparis.classname))
            {
                siparis.c_stok_kodu = stok_kodu;
                siparis.c_stok_adi = edit_stok_adi.getText().toString().replace(",",".");
                siparis.c_stok_miktar = edit_stok_miktar.getText().toString().replace(",",".");
                siparis.c_stok_birim1_ad = birimadi;
                siparis.c_stok_isk1 = edit_stok_isk1.getText().toString().replace(",",".");
                siparis.c_stok_isk2 = edit_stok_isk2.getText().toString().replace(",",".");
                siparis.c_stok_isk3 = edit_stok_isk3.getText().toString().replace(",",".");
                siparis.c_stok_isk1_uyg = isk1uyg;
                siparis.c_stok_isk2_uyg = isk2uyg;
                siparis.c_stok_isk3_uyg = isk3uyg;
                siparis.c_stok_kdv = String.valueOf(spinner_kdv_stokekle.getSelectedItemPosition());//edit_stok_kdv.getText().toString();
                siparis.c_stok_birimfiyat = edit_stok_birimfiyat.getText().toString().replace(",",".");
                siparis.c_stok_tutar = edit_stok_tutar.getText().toString().replace(",",".");
                siparis.c_stok_listesirano = listesirano;
                stok_kodu = null;
                onBackPressed();
            }
            clickable = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stokekle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Background_confirmation extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            select();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            edit_stok_birimfiyat.setText(birimfiyat);
            edit_stok_tutar.setText(tutar);
            edit_stok_isk1.setText(isk1);
            edit_stok_isk2.setText(isk2);
            edit_stok_isk3.setText(isk3);
            //edit_stok_kdv.setText(kdv);
            spinner_kdv_stokekle.setSelection(Integer.parseInt(kdv));
            clickable = true;
        }
    }

    public void select()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("carikodu", siparis.c_cari_kodu));
        nameValuePairs.add(new BasicNameValuePair("stokkodu", stok_kodu));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/stokbilgileri.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(getApplicationContext(), "Invalid IP Address",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1));
            name=(json_data.getString("name"));
            Log.i("Sonuc",name);

            if(name.equals("-99")) {
            }
            else{
                String sto_toptan_vergi =(json_data.getString("sto_toptan_vergi"));
                String sto_birimfiyat =(json_data.getString("sto_birimfiyat"));
                String sto_birim1_ad =(json_data.getString("sto_birim1_ad"));
                String sto_isk1 =(json_data.getString("sto_isk1"));
                String sto_isk2 =(json_data.getString("sto_isk2"));
                String sto_isk3 =(json_data.getString("sto_isk3"));
                String sto_isk1_uyg =(json_data.getString("sto_isk1_uyg"));
                String sto_isk2_uyg =(json_data.getString("sto_isk2_uyg"));
                String sto_isk3_uyg =(json_data.getString("sto_isk3_uyg"));
                String sto_listesirano =(json_data.getString("sto_listesirano"));
                String sto_kdvdahil =(json_data.getString("sto_kdvdahil"));

                if(sto_kdvdahil.equals("1")){
                    Float kdvyuzdesi = Float.parseFloat(Constant.kdvtipleri[Integer.parseInt(sto_toptan_vergi)]);
                    Float kdvsiztutar = Float.parseFloat(sto_birimfiyat) / ((float)1 + kdvyuzdesi);
                    sto_birimfiyat = String.valueOf(kdvsiztutar);
                }

                DecimalFormat formatter = new DecimalFormat("##.##");
                float parse = Float.parseFloat(sto_birimfiyat);
                float miktarparse = Float.parseFloat(edit_stok_miktar.getText().toString().replace(",","."));
                float tutarparse = parse * miktarparse;
                birimfiyat = String.format("%.2f", parse);//formatter.format(parse);
                tutar = String.format("%.2f", tutarparse);//formatter.format(tutarparse);
                parse = Float.parseFloat(sto_isk1);
                isk1 = String.format("%.2f", parse);//formatter.format(parse);
                parse = Float.parseFloat(sto_isk2);
                isk2 = String.format("%.2f", parse);//formatter.format(parse);
                parse = Float.parseFloat(sto_isk3);
                isk3 = String.format("%.2f", parse);//formatter.format(parse);
                kdv = sto_toptan_vergi;//formatter.format(Float.parseFloat(Constant.kdvtipleri[Integer.parseInt(sto_toptan_vergi)])*100);
                birimadi = sto_birim1_ad;
                isk1uyg = sto_isk1_uyg;
                isk2uyg = sto_isk2_uyg;
                isk3uyg = sto_isk3_uyg;
                listesirano = sto_listesirano;
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }

}
