package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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


public class depolistele extends Activity  {

    public String cagiran = "";

    public static final String FIRST_COLUMN = Constant.FIRST_COLUMN;
    public static final String SECOND_COLUMN = Constant.SECOND_COLUMN;
    public static final String THIRD_COLUMN = Constant.THIRD_COLUMN;
    public static final String FOURTH_COLUMN = Constant.FOURTH_COLUMN;

    private ListView listview;
    private ArrayList<HashMap> list;
    listviewAdapter4 adapter;
    HashMap hash;
    private EditText filter;

    ArrayList<String> depolar = new ArrayList<String>();

    InputStream is=null;
    String result=null;
    String line=null;

    String key = "";
    String depo_depolar = "1";
    int def_color = Color.RED;
    int sel_color = Color.parseColor("#FF4444");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolistele);

        list = new ArrayList<HashMap>();
        key = "**";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String datas = extras.getString("anahtar");
            cagiran = extras.getString("classname");
            if (datas != null) {
                depo_depolar = datas;
            }
        }

        if(depo_depolar.equals("1")){
            RelativeLayout ly = (RelativeLayout)findViewById(R.id.relativeLayoutMultiSel);
            ly.setVisibility(View.INVISIBLE);
        }

        new Background_confirmation().execute();
        //populateList();
    }

    private void populateList() {
        //list.add(hash);
    }

    public void saveEvent(View v) {
        if(depo_depolar.equals("2")){
            String depolarString = "";
            for (int i=0;i<depolar.size();i++){
                depolarString += depolar.get(i);
                if(i < depolar.size()-1){
                    depolarString += ";";
                }
            }
            ayarlar.c_depolar = depolarString;
            onBackPressed();
        }
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
            ListView lview = (ListView) findViewById(R.id.list_depo_listele);
            adapter = new listviewAdapter4(depolistele.this, list, 2);

            lview.setAdapter(adapter);

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    TextView tt = (TextView) view.findViewById(R.id.SecondText);
                    TextView tt1 = (TextView) view.findViewById(R.id.FirstText);
                    if(true) {
                        String kod = tt.getText().toString();
                        String adi = tt1.getText().toString();
                        if(cagiran.equals(ayarlar.classname))
                        {
                            if(depo_depolar.equals("1")){
                                ayarlar.c_evrak_depono = kod;
                                onBackPressed();
                            }else if(depo_depolar.equals("2")){
                                LinearLayout ly = (LinearLayout)view.findViewById(R.id.linearLayoutMultiSel1);
                                Drawable background = ly.getBackground();
                                int color = Color.TRANSPARENT;
                                if (background instanceof ColorDrawable)
                                    color = ((ColorDrawable) background).getColor();
                                if(color != sel_color){
                                    def_color = color;
                                    ly.setBackgroundColor(sel_color);
                                    depolar.add(kod);
                                }else{
                                    ly.setBackgroundColor(def_color);
                                    depolar.remove(kod);
                                }
                                /*ayarlar.c_depolar = kod;
                                onBackPressed();*/
                            }
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
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/depolistele.php");
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
            list.clear();
            hash = new HashMap();
            hash.put(FIRST_COLUMN,"GENEL");
            hash.put(SECOND_COLUMN, "0");
            hash.put(THIRD_COLUMN, "");
            hash.put(FOURTH_COLUMN, "");
            list.add(hash);
            for(int i = 0; i<contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);
                String json_dep_no = c.getString("dep_no");
                String json_dep_adi = c.getString("dep_adi");
                String json_dep_adres1 = c.getString("dep_adres1");
                String json_dep_adres2 = c.getString("dep_adres2");

                hash = new HashMap();
                hash.put(FIRST_COLUMN,json_dep_adi);
                hash.put(SECOND_COLUMN, json_dep_no);
                hash.put(THIRD_COLUMN, json_dep_adres1);
                hash.put(FOURTH_COLUMN, json_dep_adres2);
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
        getMenuInflater().inflate(R.menu.menu_depolistele, menu);
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
