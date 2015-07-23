package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class cariler extends Activity{// implements SimpleGestureFilter.SimpleGestureListener {

    ListView listele;
    //private SimpleGestureFilter detector;

    public static final String FIRST_COLUMN = Constant.FIRST_COLUMN;
    public static final String SECOND_COLUMN = Constant.SECOND_COLUMN;
    public static final String THIRD_COLUMN = Constant.THIRD_COLUMN;
    public static final String FOURTH_COLUMN = Constant.FOURTH_COLUMN;
    public static final String INNER_FIRST_COLUMN = Constant.INNER_FIRST_COLUMN;
    public static final String INNER_SECOND_COLUMN = Constant.INNER_SECOND_COLUMN;
    public static final String INNER_THIRD_COLUMN = Constant.INNER_THIRD_COLUMN;
    public static final String INNER_FOURTH_COLUMN = Constant.INNER_FOURTH_COLUMN;
    public static final String INNER_FIFTH_COLUMN = Constant.INNER_FIFTH_COLUMN;


    private EditText filter;
    private ListView listview;
    private ArrayAdapter<String> listviewAdapter;
    private ArrayList<HashMap> list;
    listviewAdapter3 adapter;
    HashMap hash;

    InputStream is=null;
    String result=null;
    String line=null;

    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cariler);

        filter = (EditText) findViewById(R.id.edit_cariler_filter);
        list = new ArrayList<HashMap>();
        filter.setText("");

        filter.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                key = filter.getText().toString();
                new Background_confirmation().execute();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        key = filter.getText().toString();
        new Background_confirmation().execute();

        //populateList();
        //SwipeDetector swipeDetector = new SwipeDetector();
        //listele.setOnTouchListener(swipeDetector);


        //detector = new SimpleGestureFilter(this, this);
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent me){
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }*/

    /*@Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT :
                str = "Swipe Right";
                //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                str = "Swipe Left";
                //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                str = "Swipe Down";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                break;
            case SimpleGestureFilter.SWIPE_UP :
                str = "Swipe Up";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                break;

        }
        Log.e("TOUCH", str);
    }

    @Override
    public void onDoubleTap() {
    }*/

    /*public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                // Here u can write code which is executed after the user touch on the screen break;
                Log.i("TOUCH","DOWN");

            }

            case MotionEvent.ACTION_UP: {

                // Here u can write code which is executed after the user release the touch on the screen break;
                Log.i("TOUCH","UP");

            }

            case MotionEvent.ACTION_MOVE: {

                // Here u can write code which is executed when user move the finger on the screen break;
                Log.i("TOUCH","MOVE");

            }

        }

        return true;

    }*/

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
            ListView lview = (ListView) findViewById(R.id.list_c_carilistele);
            adapter = new listviewAdapter3(cariler.this, list, 1);

            lview.setAdapter(adapter);

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    TextView tt = (TextView) view.findViewById(R.id.FirstText);
                    if(true) {
                        String kod = tt.getText().toString();
                        Log.e("TOUCH", kod);
                        LinearLayout ly = (LinearLayout)view.findViewById(R.id.linearlayout_carihesaplar_expand);
                        ViewGroup.LayoutParams params = ly.getLayoutParams();
                        if(params.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                            params.height = 0;
                        }else{
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        }
                        ly.setLayoutParams(params);
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
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/a_carihesaplar.php");
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
            for(int i = 0; i<contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);
                String json_cari_kod = c.getString("cari_kod");
                String json_cari_unvan = c.getString("cari_unvan");
                String json_cari_vdairesi = c.getString("cari_vdairesi");
                String json_cari_bakiye = c.getString("cari_bakiye");
                String json_cari_temsilci_kodu = c.getString("cari_temsilci_kodu");

                hash = new HashMap();
                hash.put(FIRST_COLUMN, json_cari_unvan);
                hash.put(SECOND_COLUMN, json_cari_kod);
                hash.put(THIRD_COLUMN, json_cari_bakiye);
                DecimalFormat formatter = new DecimalFormat("#,###.##");
                float parse = Float.parseFloat(json_cari_bakiye);
                hash.put(FOURTH_COLUMN, "Bakiye: " + String.format("%.2f", parse) + " TL");
                if(json_cari_temsilci_kodu == null){
                    json_cari_temsilci_kodu = "";
                }
                hash.put(INNER_FIRST_COLUMN,"Cari Personeli: "+  json_cari_temsilci_kodu);
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
        getMenuInflater().inflate(R.menu.menu_cariler, menu);
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
