package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class adreslistele extends Activity  {

    public String cagiran = "";

    public static final String FIRST_COLUMN = Constant.FIRST_COLUMN;
    public static final String SECOND_COLUMN = Constant.SECOND_COLUMN;
    public static final String THIRD_COLUMN = Constant.THIRD_COLUMN;
    public static final String FOURTH_COLUMN = Constant.FOURTH_COLUMN;

    private ListView listview;
    private ArrayAdapter<String> listviewAdapter;
    private ArrayList<HashMap> list;
    listviewAdapter4 adapter;
    HashMap hash;

    InputStream is=null;
    String result=null;
    String line=null;

    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adreslistele);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cagiran = extras.getString("classname");
            final String datas = extras.getString("anahtar");
            if (datas != null) {
                key = datas;
            }
        }
        list = new ArrayList<HashMap>();
        new Background_confirmation().execute();
        //populateList();



    }

    private void populateList() {
        //list.add(hash);
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
            ListView lview = (ListView) findViewById(R.id.list_adreslistele);
            adapter = new listviewAdapter4(adreslistele.this, list, 2);

            lview.setAdapter(adapter);

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    TextView tt = (TextView) view.findViewById(R.id.SecondText);
                    if(true) {
                        String kod = tt.getText().toString();
                        if(cagiran.equals(ziyaret.classname))
                        {
                            ziyaret.c_adresno = kod;
                            onBackPressed();
                        }
                        else if(cagiran.equals(siparis.classname))
                        {
                            siparis.c_adresno = kod;
                            onBackPressed();
                        }
                        else if(cagiran.equals(plan.classname))
                        {
                            plan.c_adresno = kod;
                            onBackPressed();
                        }
                    }
                }
            });
        }
    }

    public void select()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("id",key));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/adreslistele.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
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
            result = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);
            result = "{\"list\":" + result + "}";
            JSONObject reader = new JSONObject(result);
            JSONArray contacts = reader.getJSONArray("list");
            for(int i = 0; i<contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);
                String json_adr_cari_kod = c.getString("adr_cari_kod");
                String json_adr_adres_no = c.getString("adr_adres_no");
                String json_adr_adres1 = c.getString("adr_adres1");
                String json_adr_adres2 = c.getString("adr_adres2");

                hash = new HashMap();
                hash.put(FIRST_COLUMN,json_adr_cari_kod);
                hash.put(SECOND_COLUMN, json_adr_adres_no);
                hash.put(THIRD_COLUMN, json_adr_adres1);
                hash.put(FOURTH_COLUMN, json_adr_adres2);
                list.add(hash);
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adreslistele, menu);
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
}