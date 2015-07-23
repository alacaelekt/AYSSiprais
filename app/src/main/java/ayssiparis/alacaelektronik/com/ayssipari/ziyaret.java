package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ziyaret extends Activity {

    String per_kod = "";
    EditText cari_kod;
    EditText adresno;
    EditText bastarih;
    EditText bassaat;
    EditText bittarih;
    EditText bitsaat;
    EditText yetkili;
    EditText aciklama;
    Spinner tamamlandi;
    TextView text_perkod;
    private Spinner spinner_tamamlandi;
    LocationManager konumYoneticisi;
    LocationListener konumDinleyicisi;
    public boolean click = false;
    public static String classname = "ziyaret";

    String firmano = "0";
    String subeno = "0";

    String carikoduTxt = "";

    String plan_id = "-1";
    String plan_degil = "2";//1 ise plandan geldi
    String plan_carikodu = "";
    String plan_cariadi = "";
    String plan_adresno = "1";

    boolean evrakmail_checked = false;
    String evrakmail_edit = "";

    public static String c_cari_kodu = null;
    public static String c_cari_unvan = null;
    public static String c_adresno = null;
    private String latitude = "";
    private String longitude = "";

    String name;
    InputStream is=null;
    String result=null;
    String line=null;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ziyaret);
        cari_kod = (EditText) findViewById(R.id.edit_carikodu);
        adresno = (EditText) findViewById(R.id.edit_adresno);
        bastarih = (EditText) findViewById(R.id.edit_bastarih);
        bassaat = (EditText) findViewById(R.id.edit_bassaat);
        bittarih = (EditText) findViewById(R.id.edit_bittarih);
        bitsaat = (EditText) findViewById(R.id.edit_bitsaat);
        yetkili = (EditText) findViewById(R.id.edit_yetkili);
        aciklama = (EditText) findViewById(R.id.edit_aciklama);
        tamamlandi = (Spinner) findViewById(R.id.spinner_tamamlandi);
        text_perkod = (TextView) findViewById(R.id.text_perkod);
        spinner_tamamlandi = (Spinner) findViewById(R.id.spinner_tamamlandi);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String datas = extras.getString("per_kod");
            firmano = extras.getString("firmano");
            subeno = extras.getString("subeno");
            if (datas != null) {
                per_kod = datas;
                text_perkod.setText("|  Personel Kodu : " + per_kod);
            }
            final String plan_id_ = extras.getString("plan_id");
            final String plan_degil_ = extras.getString("plan");
            final String plan_carikodu_ = extras.getString("carikodu");
            final String plan_cariadi_ = extras.getString("cariadi");
            final String plan_adresno_ = extras.getString("adresno");
            if (plan_degil_ != null) {
                plan_id = plan_id_;
                plan_degil = plan_degil_;
                plan_carikodu = plan_carikodu_;
                plan_cariadi = plan_cariadi_;
                plan_adresno = plan_adresno_;
            }
            final String evrakmail_checked_ = extras.getString("evrakmail_checked");
            if (evrakmail_checked_ != null) {
                evrakmail_checked = Boolean.parseBoolean(evrakmail_checked_);
            }
            final String evrakmail_edit_ = extras.getString("evrakmail_edit");
            if (evrakmail_edit_ != null) {
                evrakmail_edit = evrakmail_edit_;
            }
        }

        bastarih.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                String[] value = bastarih.getText().toString().split("-");
                int mYear=Integer.parseInt(value[0].toString());//mcurrentDate.get(Calendar.YEAR);
                int mMonth=Integer.parseInt(value[1].toString());//mcurrentDate.get(Calendar.MONTH);
                int mDay=Integer.parseInt(value[2].toString());//mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ziyaret.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        bastarih.setText(year + "-" + String.format("%02d", (monthOfYear+1)) + "-" + String.format("%02d", (dayOfMonth)));
                    }
                },mYear, (mMonth-1), mDay);
                mDatePicker.setTitle("Tarih Seç");
                mDatePicker.show();  }
        });
        bittarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                String[] value = bittarih.getText().toString().split("-");
                int mYear=Integer.parseInt(value[0].toString());//mcurrentDate.get(Calendar.YEAR);
                int mMonth=Integer.parseInt(value[1].toString());//mcurrentDate.get(Calendar.MONTH);
                int mDay=Integer.parseInt(value[2].toString());//mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ziyaret.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        bittarih.setText(year + "-" + String.format("%02d", (monthOfYear+1)) + "-" + String.format("%02d", (dayOfMonth)));
                    }
                },mYear, (mMonth-1), mDay);
                mDatePicker.setTitle("Tarih Seç");
                mDatePicker.show();  }
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
                mTimePicker = new TimePickerDialog(ziyaret.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        bassaat.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute) + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Saat Seç");
                mTimePicker.show();
            }
        });

        bitsaat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                String[] value = bitsaat.getText().toString().split(":");
                int hour = Integer.parseInt(value[0].toString());//mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = Integer.parseInt(value[1].toString());//mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ziyaret.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        bitsaat.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute) + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Saat Seç");
                mTimePicker.show();
            }
        });

        verileriSifirla();

        konumYoneticisi = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        konumDinleyicisi = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.w("Info", "GPS Açıldı.");
                if(click == true) {
                    verileriGonder();
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.w("Info", "GPS Kapatıldı.");
            }

            @Override
            public void onLocationChanged(Location loc) {
                /*loc.getLatitude();
                loc.getLongitude();

                String Text = "Bulunduğunuz konum bilgileri : \n" +"Latitud = " + loc.getLatitude() +"\nLongitud = "+ loc.getLongitude();
                Log.i("Fail 1", Text);*/
            }
        };
        konumYoneticisi.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, konumDinleyicisi);

    }

    public void verileriGonder(){
        click = false;
        Location location = konumYoneticisi.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude = location.getLatitude() + "";
        longitude = location.getLongitude() + "";
        Log.w("GPS", location.getLatitude() + "");
        Log.w("GPS", location.getLongitude() + "");

        turnGPSOff();
        new Background_confirmation().execute();
    }

    public void verileriSifirla(){
        cari_kod.setText(plan_cariadi);
        carikoduTxt = plan_carikodu;
        adresno.setText(plan_adresno);
        yetkili.setText("");
        aciklama.setText("");

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowDate = sdfDate.format(now);
        sdfDate = new SimpleDateFormat("HH:mm:ss");
        String nowTime = sdfDate.format(now);
        bastarih.setText(nowDate);
        bittarih.setText(nowDate);
        bassaat.setText(nowTime);
        bitsaat.setText(nowTime);
    }

    /*public void ziyaretEvent(View v) {
        Intent intent = new Intent(ziyaret.this, ziyaret.class);
        intent.putExtra("per_kod", per_kod);
        startActivity(intent);
    }*/

    public void carikoduEvent(View v) {
        if(!plan_degil.equals("1")){
            Intent intent = new Intent(ziyaret.this, carilistele.class);
            intent.putExtra("classname", classname);
            intent.putExtra("anahtar", "");//cari_kod.getText().toString());
            startActivity(intent);
        }
    }

    public void adresnoEvent(View v) {
        if(!plan_degil.equals("1")){
            Intent intent = new Intent(ziyaret.this, adreslistele.class);
            intent.putExtra("classname", classname);
            intent.putExtra("anahtar", carikoduTxt);
            startActivity(intent);
        }
    }

    public void saveEvent(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Ziyaret Kaydedilsin mi?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kaydetme işlemini tamamlamak için devam butonuna tıklayın!")
                .setCancelable(false)
                .setPositiveButton("Devam",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        click = true;
                        if(konumYoneticisi.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            verileriGonder();
                        }else{
                            turnGPSOn();
                        }
                    }
                })
                .setNegativeButton("İptal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(c_cari_unvan != null) {
            if(!carikoduTxt.equals(c_cari_kodu)){
                cari_kod.setText(c_cari_unvan);
                carikoduTxt = c_cari_kodu;
                adresno.setText("1");
            }
            c_cari_unvan = null;
        }
        if(c_adresno != null) {
            adresno.setText(c_adresno);
            c_adresno = null;
        }
    }
    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!konumYoneticisi.isProviderEnabled(LocationManager.GPS_PROVIDER)){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        else {
            Log.w("GPS", "GPS zaten açık.");
        }
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(konumYoneticisi.isProviderEnabled(LocationManager.GPS_PROVIDER)){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        else {
            Log.w("GPS", "GPS zaten kapalı.");
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
        }
    }

    public void select()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        String _bastarih = bastarih.getText().toString() + " " + bassaat.getText().toString();
        String _bittarih = bittarih.getText().toString() + " " + bitsaat.getText().toString();
        int _tamamlandi = 1;//spinner_tamamlandi.getSelectedItemPosition();

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowDate = sdfDate.format(now);

        nameValuePairs.add(new BasicNameValuePair("plan_id",plan_id));
        nameValuePairs.add(new BasicNameValuePair("carikodu",carikoduTxt));
        nameValuePairs.add(new BasicNameValuePair("adresno",adresno.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("perkod",per_kod));
        nameValuePairs.add(new BasicNameValuePair("yetkili",yetkili.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("aciklama",aciklama.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("kodu","1"));
        nameValuePairs.add(new BasicNameValuePair("tamamlandi",_tamamlandi+""));
        nameValuePairs.add(new BasicNameValuePair("firmano",firmano));
        nameValuePairs.add(new BasicNameValuePair("subeno",subeno));
        nameValuePairs.add(new BasicNameValuePair("kodbaslangic","alc_"));
        nameValuePairs.add(new BasicNameValuePair("nowdate",nowDate));
        nameValuePairs.add(new BasicNameValuePair("tarih", nowDate));
        nameValuePairs.add(new BasicNameValuePair("bastarih",_bastarih));
        nameValuePairs.add(new BasicNameValuePair("bittarih",_bittarih));
        nameValuePairs.add(new BasicNameValuePair("latitude",latitude));
        nameValuePairs.add(new BasicNameValuePair("longitude",longitude));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/ziyaretekle.php");
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
            Log.e("Sonuc",name);

            if(name.equals("1") || name.equals("true") || name.equals("True")) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                        if(evrakmail_checked == true){
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL, new String[]{evrakmail_edit});
                            i.putExtra(Intent.EXTRA_SUBJECT, "Ziyaret Hareketi");

                            i.putExtra(
                                    Intent.EXTRA_TEXT,
                                    Html.fromHtml(new StringBuilder()
                                            //.append("<p><b>Some Content</b></p>")
                                            //.append("<small><p>More content</p></small>")
                                            .append("<p><b>Cari Personeli Kodu </b>: " + per_kod + "</p>")
                                            .append("<p><b>Cari Kodu : </b>" + carikoduTxt + "</p>")
                                            .append("<p><b>Cari Ünvanı </b>: " + cari_kod.getText().toString() + "</p>")
                                            .append("<p><b>Adres No : </b>" + adresno.getText().toString() + "</p>")
                                            .append("<p><b>Başlangıç Tarihi </b>: " + bastarih.getText().toString() + " " + bassaat.getText().toString() + "</p>")
                                            .append("<p><b>Bitiş Tarihi : </b>" + bittarih.getText().toString() + " " + bitsaat.getText().toString() + "</p>")
                                            .append("<p><b>Yetkili Adı </b>: " + yetkili.getText().toString() + "</p>")
                                            .append("<p><b>Açıklama : </b>" + aciklama.getText().toString() + "</p>")
                                                    //.append("<br><p>Cari Personeli Kodu : " + per_kod + "</p>")
                                            .toString())
                            );
                            String body = "\nCari Personeli Kodu : " + per_kod;
                            body += "Cari Kodu : " + carikoduTxt;
                            body += "\nCari Ünvanı : " + cari_kod.getText().toString();
                            body += "\nAdres No : " + adresno.getText().toString();
                            body += "\nBaşlangıç Tarihi : " + bastarih.getText().toString() + " " + bassaat.getText().toString();
                            body += "\nBitiş Tarihi : " + bittarih.getText().toString() + " " + bitsaat.getText().toString();
                            body += "\nYetkili Adı : " + yetkili.getText().toString();
                            body += "\nAçıklama : " + aciklama.getText().toString();
                            //i.putExtra(Intent.EXTRA_TEXT, body);
                            try {
                                startActivity(Intent.createChooser(i, "Mail Gönder..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getBaseContext(), "Herhangi bir e-Posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        verileriSifirla();
                        onBackPressed();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ziyaret, menu);
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
