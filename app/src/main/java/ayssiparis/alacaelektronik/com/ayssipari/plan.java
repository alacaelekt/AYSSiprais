package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mustafaferhan.MFCalendarView;
import com.mustafaferhan.onMFCalendarViewListener;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ZCan on 1.07.2015.
 */
public class plan extends Activity {





    List<planModel> _list;
    List<planModel> _listTek;

    String name;

    private planModel pm;
    private Switch sw;
    private Button btn;
    private Button PlanEkle;

    private Button cariSec;
    private Button adresSec;

    private TextView _txtTarih;
    private TextView _txtCari;
    private TextView _txtAdres;
    private TextView _txtAciklama;
    private TextView _txtYetkili;

    private MFCalendarView mf;

    private EditText filter;
    private TextView text_perkod;
    private ListView listview;
    private ArrayAdapter<String> listviewAdapter;
    private ArrayList<HashMap> list;
    listviewAdapter3 adapter;
    HashMap hash;

    private Button planZiyareteGit;

    InputStream is=null;
    String result=null;
    String line=null;

    String per_kod="";
    String firmano="0";
    String subeno="0";

    public static String c_cari_kodu = null;
    public static String c_cari_unvan = null;
    public static String c_adresno = null;
    String carikoduTxt = "";
    EditText cari_kod;
    EditText adresno;
    public static String classname = "plan";

    public boolean onresume_tf = false;
    public int plan_id = 0;
    String key = "";
    String url = "http://" + Constant.server + "/ziyaretplanlistele.php?per_kod=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            per_kod = extras.getString("per_kod");
            firmano= extras.getString("firmano");
            subeno= extras.getString("subeno");
            url=url+per_kod;
        }

        /*
        _txtAciklama =(TextView) findViewById(R.id.plantxtAciklama);
        _txtTarih =(TextView) findViewById(R.id.plantxtTarih);
        _txtCari =(TextView) findViewById(R.id.plantxtCari);
        _txtAdres =(TextView) findViewById(R.id.plantxtAdres);
        _txtYetkili =(TextView) findViewById(R.id.plantxtYetkili);
        */
        //cari_kod = (EditText) findViewById(R.id.edit_carikodu);
        //adresno = (EditText) findViewById(R.id.edit_adresno);

        text_perkod = (TextView) findViewById(R.id.text_perkod_plan);
        text_perkod.setText("|  Personel Kodu : " + per_kod);

        PlanEkle = (Button) findViewById(R.id.planbtnPlanekle);


        PlanEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                planDialog();



            }
        });

        //sw = (Switch) findViewById(R.id.planswitchbutton);


        btn = (Button) findViewById(R.id.planbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ziyareteGonder(pm);


            }
        });


        mf = (MFCalendarView) findViewById(R.id.mFCalendarView);

        mf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mf.setOnCalendarViewListener(new onMFCalendarViewListener() {
            @Override
            public void onDateChanged(String date) {


               dateWork(date);


            }

            @Override
            public void onDisplayedMonthChanged(int month, int year, String monthStr) {


                if (_list.size() != 0) {


                    ArrayList<String> eventDays = new ArrayList<String>();

                    for (int i = 0; i < _list.size(); i++) {
                        eventDays.add(_list.get(i).datetime.substring(0, 10));
                    }

                    mf.setEvents(eventDays);

                }


            }
        });

        readData(false);





    }

    private void ziyareteGonder(planModel _pm_) {
        if(_pm_.carikod != null){
            Intent intent = new Intent(plan.this,ziyaret.class);
            intent.putExtra("per_kod",""+per_kod);
            intent.putExtra("plan_id",""+_pm_.id);

            intent.putExtra("carikodu",""+_pm_.carikod);
            intent.putExtra("cariadi",""+_pm_.cariunvan);
            intent.putExtra("adresno",""+_pm_.adresno);
            intent.putExtra("firmano", firmano);
            intent.putExtra("subeno", subeno);
            intent.putExtra("plan","1");
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(c_cari_unvan != null) {
            if(!carikoduTxt.equals(c_cari_kodu)){
                carikoduTxt = c_cari_kodu;
                _editCariKodu.setText(c_cari_unvan);
                _editAdresNo.setText("1");
                c_cari_unvan = null;
            }

        }
        if(c_adresno != null) {
            _editAdresNo.setText(c_adresno);
            c_adresno = null;
        }
        readData(true);
    }

    Dialog dialog2;
    EditText _editCariKodu =null;
    EditText _editAdresNo =null;

    EditText bastarih;
    EditText bassaat;


    EditText txtyetkili;
    EditText txtaciklama;

    public void planDialog()
    {
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog2.setContentView(R.layout.planlamaplanekle);

        _editCariKodu = (EditText)dialog2.findViewById(R.id.edit_carikodu);
        _editAdresNo = (EditText)dialog2.findViewById(R.id.edit_adresno);
        _editAdresNo.setText("1");

        txtyetkili = (EditText)dialog2.findViewById(R.id.edit_yetkili);
        txtaciklama = (EditText)dialog2.findViewById(R.id.edit_aciklama);


        ImageButton _savebutton = (ImageButton) dialog2.findViewById(R.id.imageButton);
        _savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveEvent();

            }
        });
        LinearLayout _layoutbutton = (LinearLayout) dialog2.findViewById(R.id.linearSaveButton);
        _layoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveEvent();

            }
        });


        adresSec =(Button) dialog2.findViewById(R.id.button_adresno);
        adresSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(plan.this, adreslistele.class);
                intent.putExtra("per_kod", per_kod);
                intent.putExtra("classname", classname);
                intent.putExtra("anahtar", carikoduTxt);
                startActivity(intent);

            }
        });

        cariSec = (Button) dialog2.findViewById(R.id.button_carikodu);

        cariSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(plan.this, carilistele.class);
                intent.putExtra("per_kod", per_kod);
                intent.putExtra("classname", classname);
                startActivity(intent);


            }
        });



        bastarih = (EditText) dialog2.findViewById(R.id.edit_bastarih);
        bassaat = (EditText) dialog2.findViewById(R.id.edit_bassaat);


        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowDate = sdfDate.format(now);
        sdfDate = new SimpleDateFormat("HH:mm:ss");
        String nowTime = sdfDate.format(now);
        bastarih.setText(nowDate);

        bassaat.setText(nowTime);



        bastarih.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                String[] value = bastarih.getText().toString().split("-");
                int mYear = Integer.parseInt(value[0].toString());//mcurrentDate.get(Calendar.YEAR);
                int mMonth = Integer.parseInt(value[1].toString());//mcurrentDate.get(Calendar.MONTH);
                int mDay = Integer.parseInt(value[2].toString());//mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(plan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        bastarih.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", (dayOfMonth)));
                    }
                }, mYear, (mMonth - 1), mDay);
                mDatePicker.setTitle("Tarih Seç");
                mDatePicker.show();
            }
        });


        bassaat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                String[] value = bassaat.getText().toString().split(":");
                int hour = Integer.parseInt(value[0].toString());//mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = Integer.parseInt(value[1].toString());//mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(plan.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        bassaat.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute) + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Saat Seç");
                mTimePicker.show();
            }
        });



        dialog2.show();

    }

        public void dateWork(String date)
      {
          _listTek = new ArrayList<planModel>();
          // _txtAciklama.setText("");
        int sayac=0;
        int index = -1;
        for (int i = 0; i < _list.size(); i++) {
            String _tarih = _list.get(i).datetime.substring(0, 10);

            if (date.equals(_tarih)) {
                _listTek.add(_list.get(i));
                sayac++;
                index = i;

            }
        }
          showDialog();

       if(sayac ==0)
       {
           /*
            _txtAciklama.setText("");
            _txtAdres.setText("");
            _txtCari.setText("");
            _txtTarih.setText("");
            _txtYetkili.setText("");
            sw.setChecked(false);
*/
        }
    }


    public void saveEvent() {
        final AlertDialog alert;

        AlertDialog.Builder dialog2 = new AlertDialog.Builder(plan.this);
        alert = dialog2.create();
        alert.setTitle("Ziyaret Planı Kaydedilsin mi?");
        alert.setMessage("Kaydetme işlemini tamamlamak için Devam butonuna tıklayın!");

        alert.setButton("Devam", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                new Background_confirmation().execute();
            }
        });

        alert.setButton2("İptal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                alert.dismiss();
            }
        });

        alert.show();
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
        }
    }

    public void select()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        String _bastarih = bastarih.getText().toString() + " " + bassaat.getText().toString();

        int _tamamlandi = 0;//spinner_tamamlandi.getSelectedItemPosition();

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowDate = sdfDate.format(now);


        nameValuePairs.add(new BasicNameValuePair("pl_tarih",_bastarih));
        nameValuePairs.add(new BasicNameValuePair("pl_adresno",_editAdresNo.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("pl_durum",_tamamlandi+""));
        nameValuePairs.add(new BasicNameValuePair("pl_aciklama",txtaciklama.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("pl_yetkili",txtyetkili.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("pl_carikod",carikoduTxt));
        nameValuePairs.add(new BasicNameValuePair("pl_latitude",pm.lat));
        nameValuePairs.add(new BasicNameValuePair("pl_longitude",pm.longi));
        nameValuePairs.add(new BasicNameValuePair("pl_perkod",per_kod));



        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/ziyaretplanekle.php");
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
            //Log.e("Sonuc",name);

            if(name.equals("1") || name.equals("true") || name.equals("True")) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getBaseContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                        dialog2.dismiss();
                        readData(false);

                    }
                });
            }
            else{
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getBaseContext(), "Kayıt başarısız oldu. Lütfen değerleri kontrol ediniz.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }


    public void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.planlamalist);

        LayoutInflater _li = LayoutInflater.from(dialog.getContext());
        final View btnView = _li.inflate(R.layout.perline,null);



        try {
            planZiyareteGit = (Button) btnView.findViewById(R.id.plan_ziyaretegit);
            planZiyareteGit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(dialog.getContext(),"11111" ,Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception ex)
        {
            Log.e("qwerty",ex.toString());

        }
            Button btnDial = (Button) dialog.findViewById(R.id.btnClose);

            btnDial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();

                }
            });

            ListView lv = (ListView) dialog.findViewById(R.id.listSecim);
            planAdapter adap = new planAdapter(this,_listTek);

            lv.setAdapter(adap);
            for(int i=0;i<_listTek.size();i++)
            {
                if(_listTek.get(i).durum == 1)
                {



                }

            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (_listTek.size() != 0)
                    {
                        pm = _listTek.get(i);
                      //  Log.i("SONUC :::",String.valueOf(pm.id));
                     /*   _txtAciklama.setText(pm.aciklama);
                        _txtAdres.setText(pm.adres);
                        _txtCari.setText(pm.cariunvan);
                        _txtTarih.setText(String.valueOf(pm.datetime));
                        _txtYetkili.setText(pm.yetkili); */


                        plan_id = pm.id; /*
                        if(pm.durum == 0)
                        {sw.setChecked(false);  btn.setVisibility(View.VISIBLE); }
                        else {
                            sw.setChecked(true);
                            btn.setVisibility(View.GONE);
                        } */

                    }

                 //   dialog.dismiss();
                }
            });

            dialog.show();



        }

    private class downloadAsync extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls)
        {


            //url = urls[0];
           ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

           nameValuePairs.add(new BasicNameValuePair("per_kod", per_kod));

           pm = new planModel();
           _list = new ArrayList<planModel>();

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://"+Constant.server+"/ziyaretplanlistele.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getApplicationContext(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();
            }

            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();


            } catch (Exception e) {
                Log.e("Fail 2", e.toString());

            }

            String state="";

            try {
                result = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);

                result = "{\"list\":" + result + "}";

                JSONObject reader = new JSONObject(result);

                JSONArray contacts = reader.getJSONArray("list");


                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String jsonId= c.getString("pl_id");
                    String jsonTarih = c.getString("pl_tarih");
                    String jsonAdres = c.getString("pl_adres");
                    String jsonAdresno = c.getString("pl_adresno");
                    String jsonAciklama = c.getString("pl_aciklama");
                    String jsonYetkili= c.getString("pl_yetkili");
                    String jsonCarikod= c.getString("pl_carikod");
                    String jsonCariunvan=c.getString("cari_unvan1");
                    String jsonLatitude= c.getString("pl_lat");
                    String jsonLongitude= c.getString("pl_lon");
                    String jsonDurum= c.getString("pl_durum");

                    pm = new planModel();
                    pm.aciklama = jsonAciklama;
                    pm.adres = jsonAdres;
                    pm.adresno = jsonAdresno;
                    pm.id = Integer.parseInt(jsonId);
                    pm.datetime = jsonTarih;
                    pm.yetkili = jsonYetkili;
                    pm.carikod = jsonCarikod;
                    pm.cariunvan = jsonCariunvan;
                    pm.lat = jsonLatitude;
                    pm.longi = jsonLongitude;
                    pm.durum = Integer.parseInt(jsonDurum);

                    _list.add(pm);

                   // Log.i("id sonuc",jsonId);

                }
            } catch (Exception e) {
                Log.e("Fail 3"+state, e.toString());

            }

            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {
            if(_list.size() != 0)
            {
                ArrayList<String> eventDays = new ArrayList<String>();
                for(int i=0;i<_list.size();i++)
                {
                    eventDays.add(_list.get(i).datetime.substring(0,10));
                }
                mf.setEvents(eventDays);
            }

            if(onresume_tf != false && _list != null){
                for(int k=0;k<_list.size();k++){
                    if(_list.get(k).id == plan_id){
                        pm = _list.get(k);
/*
                        if(pm.durum == 0)
                        {sw.setChecked(false);  btn.setVisibility(View.VISIBLE); }
                        else {
                            sw.setChecked(true);
                            btn.setVisibility(View.GONE);
                        }
                        */
                        break;
                    }
                }
            }
        }
    }

    public void readData(boolean onresume_)
    {
        onresume_tf = onresume_;
        new downloadAsync().execute();
    }

    public class planModel
    {

        public int id;
        public String datetime;
        public String adres;
        public String adresno;
        public int durum;
        public String aciklama;
        public String yetkili;
        public String carikod;
        public String cariunvan;
        public String lat;
        public String longi;
    }

    public class planAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater;
        private List<planModel> mPlanList;

        public planAdapter(Activity activity,List<planModel> model)
        {
            mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mPlanList = model;

        }
        @Override
        public int getCount()
        {
            return mPlanList.size();

        }

        @Override
        public planModel getItem(int position)
        {
            return mPlanList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }
planModel _pm_ =null;
        @Override
        public View getView(int position, View convertView,ViewGroup parent)
        {


            View satirView;

            satirView = mInflater.inflate(R.layout.perline,null);
            TextView textCari = (TextView) satirView.findViewById(R.id.cariKod);
            TextView textAdres = (TextView) satirView.findViewById(R.id.adres);
            TextView textYetkili = (TextView) satirView.findViewById(R.id.yetkili);
            TextView textTarih = (TextView) satirView.findViewById(R.id.cariTarih);
            final Button __btn =(Button) satirView.findViewById(R.id.plan_ziyaretegit);

            final planModel pm = mPlanList.get(position);
            __btn.setTag(pm);



            textCari.setText(pm.cariunvan);
            textAdres.setText(pm.adres);
            textYetkili.setText(pm.yetkili);
            textTarih.setText(pm.datetime);

            if(pm.durum == 0) __btn.setVisibility(View.VISIBLE );
            else __btn.setVisibility(View.GONE);
final int _pos = position;
            __btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                planModel gonder = (planModel)__btn.getTag();
                    ziyareteGonder(gonder);
                    //Toast.makeText(getApplicationContext(), String.valueOf(_pos), Toast.LENGTH_SHORT).show();

                }
            });


            return satirView;
        }

    }
}

