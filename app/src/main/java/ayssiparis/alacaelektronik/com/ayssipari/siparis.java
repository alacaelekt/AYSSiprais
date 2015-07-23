package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class siparis extends Activity {

    public static final String FIRST_COLUMN = Constant.FIRST_COLUMN;
    public static final String SECOND_COLUMN = Constant.SECOND_COLUMN;
    public static final String THIRD_COLUMN = Constant.THIRD_COLUMN;
    public static final String FOURTH_COLUMN = Constant.FOURTH_COLUMN;
    public static final String FIFTH_COLUMN = Constant.FIFTH_COLUMN;
    public static final String INNER_FIRST_COLUMN = Constant.INNER_FIRST_COLUMN;
    public static final String INNER_SECOND_COLUMN = Constant.INNER_SECOND_COLUMN;
    public static final String INNER_THIRD_COLUMN = Constant.INNER_THIRD_COLUMN;
    public static final String INNER_FOURTH_COLUMN = Constant.INNER_FOURTH_COLUMN;
    public static final String INNER_FIFTH_COLUMN = Constant.INNER_FIFTH_COLUMN;
    public static final String INNER_SIXTH_COLUMN = Constant.INNER_SIXTH_COLUMN;
    public static final String INNER_SEVENTH_COLUMN = Constant.INNER_SEVENTH_COLUMN;
    public static final String INNER_EIGHTH_COLUMN = Constant.INNER_EIGHTH_COLUMN;
    public static final String INNER_NINTH_COLUMN = Constant.INNER_NINTH_COLUMN;
    public static final String INNER_TENTH_COLUMN = Constant.INNER_TENTH_COLUMN;
    public static final String INNER_ELEVENTH_COLUMN = Constant.INNER_ELEVENTH_COLUMN;

    String name;
    String evraknouret;
    int count;

    String tablerows = "";

    //private ArrayAdapter<String> listviewAdapter5;
    private ArrayList<HashMap> list;
    listviewAdapter5 adapter;
    HashMap hash;ListView lview;

    InputStream is=null;
    String result=null;
    String line=null;

    String key = "";

    boolean kontrol = true;
    boolean sirano_getirildi = true;

    private TabHost mTabHost;
    public static String classname = "siparis";
    String per_kod = "";
    String cari_kod = "";

    boolean evrakmail_checked = false;
    String evrakmail_edit = "";

    EditText evrakno;
    EditText evrakno_sira;
    EditText belgeno;
    EditText edit_cari_kod;
    EditText adresno;
    EditText tarih;
    EditText saat;
    EditText teslimtarih;
    EditText teslimsaat;

    EditText edit_stok_kodu;

    EditText edit_aratoplam;
    EditText edit_isk1;
    EditText edit_isk2;
    EditText edit_isk3;
    EditText edit_kdv;
    EditText edit_toplam;
    EditText edit_indirimyuzde;
    EditText edit_indirimtutar;

    Float geneltoplam = (float)0;

    String depolar ="";
    String evrakdepo ="0";
    String firmano = "0";
    String subeno = "0";
    String sip_evrakno_seri = "";

    public static String c_adresno = null;

    public static String c_cari_kodu = null;
    public static String c_cari_unvan = null;
    public static String c_stok_kodu = null;
    public static String c_stok_adi = null;
    public static String c_stok_miktar = null;
    public static String c_stok_birim1_ad = null;
    public static String c_stok_isk1 = null;
    public static String c_stok_isk2 = null;
    public static String c_stok_isk3 = null;
    public static String c_stok_isk1_uyg = null;
    public static String c_stok_isk2_uyg = null;
    public static String c_stok_isk3_uyg = null;
    public static String c_stok_kdv = null;
    public static String c_stok_birimfiyat = null;
    public static String c_stok_tutar = null;
    public static String c_stok_listesirano = null;

    final Context context = this;

    DecimalFormat formatter = new DecimalFormat("#,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis);

        list = new ArrayList<HashMap>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String datas = extras.getString("per_kod");
            final String carikodu = extras.getString("carikodu");
            final String depolar_ = extras.getString("depolar");
            evrakdepo = extras.getString("evrakdepo");
            firmano = extras.getString("firmano");
            subeno = extras.getString("subeno");
            sip_evrakno_seri = extras.getString("sip_evrakno_seri");

            if (datas != null) {
                per_kod = datas;
            }
            if (carikodu != null) {
                cari_kod = carikodu;
            }
            if (depolar_ != null) {
                depolar = depolar_;
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

        evrakno = (EditText) findViewById(R.id.edit_evrakno_tab1);
        evrakno_sira = (EditText) findViewById(R.id.edit_evrakno_sira_tab1);
        belgeno = (EditText) findViewById(R.id.edit_belgeno_tab1);
        edit_cari_kod = (EditText) findViewById(R.id.edit_carikodu_tab1);
        adresno = (EditText) findViewById(R.id.edit_adresno_tab1);
        tarih = (EditText) findViewById(R.id.edit_tarih_tab1);
        saat = (EditText) findViewById(R.id.edit_saat_tab1);
        teslimtarih = (EditText) findViewById(R.id.edit_teslimtarih_tab1);
        teslimsaat = (EditText) findViewById(R.id.edit_teslimsaat_tab1);

        edit_stok_kodu = (EditText) findViewById(R.id.editText_urunadi_tab2);

        edit_aratoplam = (EditText) findViewById(R.id.edit_aratoplam_tab3);
        edit_isk1 = (EditText) findViewById(R.id.edit_isk1_tab3);
        edit_isk2 = (EditText) findViewById(R.id.edit_isk2_tab3);
        edit_isk3 = (EditText) findViewById(R.id.edit_isk3_tab3);
        edit_kdv = (EditText) findViewById(R.id.edit_kdv_tab3);
        edit_toplam = (EditText) findViewById(R.id.edit_toplam_tab3);
        edit_indirimyuzde = (EditText) findViewById(R.id.edit_indirimyuzde_tab3);
        edit_indirimtutar = (EditText) findViewById(R.id.edit_indirimtutar_tab3);
        edit_indirimyuzde.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*Toast.makeText(getBaseContext(),
                        ((EditText) v).getId() + " has focus - " + hasFocus,
                        Toast.LENGTH_SHORT).show();*/
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                    Float toplam = geneltoplam;
                    Float indirimyuzde = Float.parseFloat(edit_indirimyuzde.getText().toString().replace(",","."));
                    Float indirimtutar = (toplam * indirimyuzde) / (float)100;
                    edit_indirimtutar.setText(String.format("%.2f", indirimtutar));
                }
            }
        });
        edit_indirimtutar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*Toast.makeText(getBaseContext(),
                        ((EditText) v).getId() + " has focus - " + hasFocus,
                        Toast.LENGTH_SHORT).show();*/
                if(hasFocus == false){
                    try {
                        Float parse = Float.parseFloat(((EditText) v).getText().toString().replace(",","."));
                        ((EditText) v).setText(String.format("%.2f", parse));
                    }catch (Exception e){
                        ((EditText) v).setText(String.format("%.2f", (float)0));
                    }
                    Float toplam = geneltoplam;
                    Float indirimtutar = Float.parseFloat(edit_indirimtutar.getText().toString().replace(",","."));
                    if(toplam != 0){
                        Float indirimyuzde = (indirimtutar / toplam) * (float)100;
                        edit_indirimyuzde.setText(String.format("%.2f", indirimyuzde));
                    }else{
                        edit_indirimyuzde.setText(String.format("%.2f", (float)0));
                        edit_indirimtutar.setText(String.format("%.2f", (float)0));
                    }
                }
            }
        });

        tarih.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                String[] value = tarih.getText().toString().split("-");
                int mYear=Integer.parseInt(value[0].toString());//mcurrentDate.get(Calendar.YEAR);
                int mMonth=Integer.parseInt(value[1].toString());//mcurrentDate.get(Calendar.MONTH);
                int mDay=Integer.parseInt(value[2].toString());//mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(siparis.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tarih.setText(year + "-" + String.format("%02d", (monthOfYear+1)) + "-" + String.format("%02d", (dayOfMonth)));
                    }
                },mYear, (mMonth-1), mDay);
                mDatePicker.setTitle("Tarih Seç");
                mDatePicker.show();  }
        });

        saat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                String[] value = saat.getText().toString().split(":");
                int hour = Integer.parseInt(value[0].toString());//mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = Integer.parseInt(value[1].toString());//mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(siparis.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        saat.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute) + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Saat Seç");
                mTimePicker.show();
            }
        });

        teslimtarih.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                String[] value = teslimtarih.getText().toString().split("-");
                int mYear=Integer.parseInt(value[0].toString());//mcurrentDate.get(Calendar.YEAR);
                int mMonth=Integer.parseInt(value[1].toString());//mcurrentDate.get(Calendar.MONTH);
                int mDay=Integer.parseInt(value[2].toString());//mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(siparis.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        teslimtarih.setText(year + "-" + String.format("%02d", (monthOfYear+1)) + "-" + String.format("%02d", (dayOfMonth)));
                    }
                },mYear, (mMonth-1), mDay);
                mDatePicker.setTitle("Tarih Seç");
                mDatePicker.show();  }
        });

        teslimsaat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                String[] value = teslimsaat.getText().toString().split(":");
                int hour = Integer.parseInt(value[0].toString());//mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = Integer.parseInt(value[1].toString());//mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(siparis.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        teslimsaat.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute) + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Saat Seç");
                mTimePicker.show();
            }
        });

        lview = (ListView) findViewById(R.id.list_tab2_urunlistele);
        adapter = new listviewAdapter5(siparis.this, list, 1);
        registerForContextMenu(lview);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                TextView tt = (TextView) view.findViewById(R.id.FirstText);
                    /*if(true) {
                        String kod = tt.getText().toString();
                        Log.e("TOUCH", kod);
                        LinearLayout ly = (LinearLayout)view.findViewById(R.id.linearlayout_z_stoklar_expand55);
                        ViewGroup.LayoutParams params = ly.getLayoutParams();
                        if(params.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                            params.height = 0;
                        }else{
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        }
                        ly.setLayoutParams(params);
                    }*/
            }
        });

        /*evrakno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    new Background_confirmation_evrakno().execute();
                }
            }
        });*/

        evrakno.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                sirano_getirildi = false;
                new Background_confirmation_evrakno().execute();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        verileriSifirla();

        mTabHost = (TabHost) findViewById(R.id.tabHostSiparis);
        mTabHost.setup();

        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("GENEL").setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("STOKLAR").setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("TOPLAMLAR").setContent(R.id.tab3));

        mTabHost.setCurrentTab(0);

        if(cari_kod.equals(""))
        {
            Intent intent = new Intent(siparis.this, carilistele.class);
            intent.putExtra("classname", classname);
            intent.putExtra("anahtar", "");
            startActivity(intent);
        }
    }

    public void verileriSifirla(){
        evrakno.setText(sip_evrakno_seri);
        new Background_confirmation_evrakno().execute();
        belgeno.setText("");
        edit_cari_kod.setText("");
        adresno.setText("1");

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowDate = sdfDate.format(now);
        sdfDate = new SimpleDateFormat("HH:mm:ss");
        String nowTime = sdfDate.format(now);
        tarih.setText(nowDate);
        saat.setText(nowTime);
        teslimtarih.setText(nowDate);
        teslimsaat.setText(nowTime);

        list.clear();
        lview.setAdapter(adapter);

        edit_aratoplam.setText(String.format("%.2f", (float)0));
        edit_isk1.setText(String.format("%.2f", (float)0));
        edit_isk2.setText(String.format("%.2f", (float)0));
        edit_isk3.setText(String.format("%.2f", (float)0));
        edit_kdv.setText(String.format("%.2f", (float)0));
        edit_toplam.setText(String.format("%.2f", (float)0));
        edit_indirimyuzde.setText(String.format("%.2f", (float)0));
        edit_indirimtutar.setText(String.format("%.2f", (float)0));
    }

    public void toplamHesapla()
    {
        Float aratoplam_ = (float)0;
        Float isk1_ = (float)0;
        Float isk2_ = (float)0;
        Float isk3_ = (float)0;
        Float kdv_ = (float)0;
        Float toplam_ = (float)0;
        Float indirim_ = (float)0;
        Float textgeneltoplam = (float)0;
        for(int i=0;i<list.size(); i++){
            HashMap hash_ = new HashMap();
            hash_ = list.get(i);
            Float tutar_ = Float.parseFloat(hash_.get(INNER_SEVENTH_COLUMN).toString());
            aratoplam_ += tutar_;

            Float isk1_yuzde = Float.parseFloat(hash_.get(INNER_THIRD_COLUMN).toString());
            Float isk2_yuzde = Float.parseFloat(hash_.get(INNER_FOURTH_COLUMN).toString());
            Float isk3_yuzde = Float.parseFloat(hash_.get(INNER_FIFTH_COLUMN).toString());
            int isk1_uyg = Integer.parseInt(hash_.get(INNER_EIGHTH_COLUMN).toString());
            int isk2_uyg = Integer.parseInt(hash_.get(INNER_NINTH_COLUMN).toString());
            int isk3_uyg = Integer.parseInt(hash_.get(INNER_TENTH_COLUMN).toString());
            int listesirano = Integer.parseInt(hash_.get(INNER_ELEVENTH_COLUMN).toString());
            //Float[] SK = new Float[4];
            Float[] iskontovetutar = iskontohesapla.iskontovemasraflar(3, tutar_, isk1_uyg, isk1_yuzde, isk2_uyg, isk2_yuzde, isk3_uyg, isk3_yuzde);
            Float isk1_tutar = iskontovetutar[0];
            Float isk2_tutar = iskontovetutar[1];
            Float isk3_tutar = iskontovetutar[2];
            Float kalan = iskontovetutar[3];
            if (isk1_tutar > tutar_)
            {
                isk1_tutar = tutar_;
                kalan = (float)0;
            }
            if (isk2_tutar > (tutar_ - isk1_tutar))
            {
                isk2_tutar = tutar_ - isk1_tutar;
                kalan = (float)0;
            }
            if (isk3_tutar > (tutar_ - isk1_tutar - isk2_tutar))
            {
                isk3_tutar = tutar_ - isk1_tutar - isk2_tutar;
                kalan = (float)0;
            }

            Float indirimtutar =  Float.parseFloat(edit_indirimtutar.getText().toString().replace(",","."));
            Float indirimyuzde =  Float.parseFloat(edit_indirimyuzde.getText().toString().replace(",","."));
            Float stokindirimtutar = (kalan * indirimyuzde / (float)100);
            isk1_ += isk1_tutar + stokindirimtutar;
            isk2_ += isk2_tutar;
            isk3_ += isk3_tutar;
            int vergipntr = Integer.parseInt(hash_.get(INNER_SIXTH_COLUMN).toString());
            Float kdvyuzdesi = Float.parseFloat(Constant.kdvtipleri[vergipntr]);
            Float kdvtutari = kalan * kdvyuzdesi;
            Float kdvtutariindirimli = (kalan-stokindirimtutar) * kdvyuzdesi;
            kdv_ += kdvtutariindirimli;
            toplam_ += kalan + kdvtutari;
            textgeneltoplam += (kalan-stokindirimtutar) + kdvtutariindirimli;
            Log.i("TAG",hash_.get(INNER_SEVENTH_COLUMN).toString());
        }
        edit_aratoplam.setText(String.format("%.2f", aratoplam_));
        edit_isk1.setText(String.format("%.2f", isk1_));
        edit_isk2.setText(String.format("%.2f", isk2_));
        edit_isk3.setText(String.format("%.2f", isk3_));
        edit_kdv.setText(String.format("%.2f", kdv_));
        edit_toplam.setText(String.format("%.2f", textgeneltoplam));
        geneltoplam = toplam_;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(c_cari_unvan != null) {
            //c_cari_kodu = null;
            if(!cari_kod.equals(c_cari_kodu)){
                edit_cari_kod.setText(c_cari_unvan);
                cari_kod = c_cari_kodu;
                adresno.setText("1");
                list.clear();
                lview.setAdapter(adapter);
                toplamHesapla();
            }
            c_cari_unvan = null;
            Log.i("TAG", c_cari_kodu);
        }
        if(c_adresno != null) {
            adresno.setText(c_adresno);
            c_adresno = null;
        }
        if(c_stok_kodu != null) {

            hash = new HashMap();
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            hash.put(FIRST_COLUMN, c_stok_adi);
            hash.put(SECOND_COLUMN, c_stok_kodu);
            float parse = Float.parseFloat(c_stok_birimfiyat);
            hash.put(THIRD_COLUMN, "Birim Fiyat: "+ String.format("%.2f", parse) + " TL");
            parse = Float.parseFloat(c_stok_miktar);
            hash.put(FOURTH_COLUMN, String.format("%.2f", parse) + " " + c_stok_birim1_ad);
            parse = Float.parseFloat(c_stok_tutar);
            hash.put(FIFTH_COLUMN, "Tutar: "+ String.format("%.2f", parse) + " TL");

            hash.put(INNER_FIRST_COLUMN,c_stok_kodu);
            hash.put(INNER_SECOND_COLUMN,c_stok_miktar);
            hash.put(INNER_THIRD_COLUMN,c_stok_isk1);
            hash.put(INNER_FOURTH_COLUMN,c_stok_isk2);
            hash.put(INNER_FIFTH_COLUMN,c_stok_isk3);
            hash.put(INNER_SIXTH_COLUMN,c_stok_kdv);
            hash.put(INNER_SEVENTH_COLUMN,c_stok_tutar);
            hash.put(INNER_EIGHTH_COLUMN,c_stok_isk1_uyg);
            hash.put(INNER_NINTH_COLUMN,c_stok_isk2_uyg);
            hash.put(INNER_TENTH_COLUMN,c_stok_isk3_uyg);
            hash.put(INNER_ELEVENTH_COLUMN,c_stok_listesirano);
            list.add(hash);
            toplamHesapla();

            Log.i("TAG",c_stok_kodu + "-" + c_stok_adi + "-" + c_stok_miktar + "-" + c_stok_isk1 + "-" + c_stok_isk2 + "-" + c_stok_isk3 + "-" + c_stok_kdv + "-" + c_stok_birimfiyat + "-" + c_stok_tutar);

            c_stok_kodu = null;
            c_stok_adi = null;
            c_stok_miktar = null;
            c_stok_birim1_ad = null;
            c_stok_isk1 = null;
            c_stok_isk2 = null;
            c_stok_isk3 = null;
            c_stok_kdv = null;
            c_stok_birimfiyat = null;
            c_stok_tutar = null;
            c_stok_isk1_uyg = null;
            c_stok_isk2_uyg = null;
            c_stok_isk3_uyg = null;
            c_stok_listesirano = null;
            //stokekle.stok_kodu = null;

            lview.setAdapter(adapter);
        }
    }

    @Override
     public void onCreateContextMenu(ContextMenu menu, View v,
                                     ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list_tab2_urunlistele) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            HashMap hash_ = list.get(info.position);
            menu.setHeaderTitle(hash_.get(FIRST_COLUMN).toString());
            String[] menuItems = getResources().getStringArray(R.array.listmenu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
     public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        /*String[] menuItems = getResources().getStringArray(R.array.listmenu);
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = Countries[info.position];*/
        if(menuItemIndex == 0){
            list.remove(info.position);
            lview.setAdapter(adapter);
            toplamHesapla();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_siparis, menu);
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

    public void iskuygulaEvent(View v) {
        getCurrentFocus().clearFocus();
        toplamHesapla();
    }

    public void saveEvent(View v) {
        //getCurrentFocus().clearFocus();
        if(sirano_getirildi == true){
            if(cari_kod != "" && list.size() > 0 && !evrakno_sira.getText().toString().equals("")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Sipariş Kaydedilsin mi?");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Kaydetme işlemini tamamlamak için devam butonuna tıklayın!")
                        .setCancelable(false)
                        .setPositiveButton("Devam",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                new Background_confirmation_siparisvarmi().execute();
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
            else{
                String alertMessage = "Eksik ya da hatalı bilgi var. Bu şekilde işleme devam edemezsiniz.";
                if(cari_kod.equals("")){
                    alertMessage = "Cari hesap seçmeniz gerekmektedir!";
                }else if(list.size() == 0){
                    alertMessage = "Ürün eklemeniz gerekmektedir!";
                }else if(evrakno_sira.getText().toString().equals("")){
                    alertMessage = "Evrak no girilmesi gerekmektedir!";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(alertMessage)
                        .setCancelable(false)
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                TextView messageView = (TextView)alert.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);
            }
        }
        Log.i("Click", "save");
    }

    public void cancelEvent(View v) {
        /*Intent intent = new Intent(siparis.this, carilistele.class);
        intent.putExtra("classname", classname);
        startActivity(intent);*/


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Siparişten çıkılsın mı?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Siparişten çıkmak için devam butonuna tıklayın!")
                .setCancelable(false)
                .setPositiveButton("Devam",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        onBackPressed();
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
        Log.i("Click", "cancel");
    }

    public void carikoduEvent(View v) {
        if(list.size() > 0){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("Cari değiştirilsin mi?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Yeni bir cari hesap seçerseniz eklenen stoklar silinecektir. İşlemi sürdürmek için devam butonuna tıklayın!")
                    .setCancelable(false)
                    .setPositiveButton("Devam",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            Intent intent = new Intent(siparis.this, carilistele.class);
                            intent.putExtra("classname", classname);
                            startActivity(intent);
                            Log.i("Click", "cari seç");
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
        else{
            Intent intent = new Intent(siparis.this, carilistele.class);
            intent.putExtra("classname", classname);
            startActivity(intent);
            Log.i("Click", "cari seç");
        }
    }

    public void adresnoEvent(View v) {
        Intent intent = new Intent(siparis.this, adreslistele.class);
        intent.putExtra("classname", classname);
        intent.putExtra("anahtar", cari_kod);
        startActivity(intent);
    }

    public void stokkoduEvent(View v) {
        if(!cari_kod.equals("")){
            Intent intent = new Intent(siparis.this, stokekle.class);
            intent.putExtra("classname", classname);
            intent.putExtra("anahtar", edit_stok_kodu.getText().toString());
            intent.putExtra("depolar", depolar);
            startActivity(intent);
            Log.i("Click", "stok seç");
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Önce cari hesap seçmeniz gerekmektedir!")
                    .setCancelable(false)
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            TextView messageView = (TextView)alert.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
        }
    }



    public class Background_confirmation extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            tablerows = "";
            //tablerows += "<tr><td>Stok Kodu</td><td>Miktar</td><td>Birim Fiyat</td><td>Tutar</td><td>İsk1 Tutar</td><td>İsk2 Tutar</td><td>İsk3. Tutar</td><td>KDV Tipi</td><td>KDV Tutarı</td><td>Fiyat Liste No</td></tr>";
            for(int i=0;i<list.size();i++){
                select(i);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(kontrol == true){
                Toast.makeText(getBaseContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                if(evrakmail_checked == true){
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{evrakmail_edit});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Sipariş Evrağı");

                    i.putExtra(
                            Intent.EXTRA_TEXT,
                            Html.fromHtml(new StringBuilder()
                                    //.append("<p><b>Some Content</b></p>")
                                    //.append("<small><p>More content</p></small>")
                                    .append("<p><b>Cari Personeli Kodu </b>: " + per_kod + "</p>")
                                    .append("<p><b>Cari Kodu : </b>" + cari_kod + "</p>")
                                    .append("<p><b>Cari Ünvanı </b>: " +  edit_cari_kod.getText().toString() + "</p>")
                                    .append("<p><b>Adres No : </b>" + adresno.getText().toString() + "</p>")
                                    .append("<p><b>Belge No : </b>" + belgeno.getText().toString() + "</p>")
                                    .append("<p><b>Tarih </b>: " + tarih.getText().toString() + " " + saat.getText().toString() + "</p>")
                                    .append("<p><b>Teslim Tarihi : </b>" + teslimtarih.getText().toString() + " " + teslimsaat.getText().toString() + "</p>")
                                    //.append("<br><p><table>" + tablerows + "</table></p>")
                                    .append("<br><p>" + tablerows + "</p>")
                                    //.append("<br><p>Cari Personeli Kodu : " + per_kod + "</p>")
                                    .toString())
                    );
                    String body = "\nCari Personeli Kodu : " + per_kod;
                    body += "\nCari Kodu : " + cari_kod;
                    body += "\nCari Ünvanı : " + edit_cari_kod.getText().toString();
                    body += "\nAdres No : " + adresno.getText().toString();
                    body += "\nBelge No : " + belgeno.getText().toString();
                    body += "\nTarih : " + tarih.getText().toString() + " " + saat.getText().toString();
                    body += "\nTeslim Tarihi : " + teslimtarih.getText().toString() + " " + teslimsaat.getText().toString();
                    for (int k=0;k<1;k++){
                        //body += "\n< table border="+"1"+">< tr>< td>row 1, cell 1< /td>"+ "< td>row 1, cell 2"+ "< /tr>"+ "< tr>"+ "< td>row 2, cell 1< /td>"+ "< td>row 2, cell 2< /td>"+ "< /tr>"+ "< /table>";
                    }
                    //i.putExtra(Intent.EXTRA_TEXT, body);
                    try {
                        startActivity(Intent.createChooser(i, "Mail Gönder..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getBaseContext(), "Herhangi bir e-Posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                    }
                }
                verileriSifirla();
                onBackPressed();
            }else{
                Toast.makeText(getBaseContext(), "Kayıt başarısız oldu. Lütfen değerleri kontrol ediniz.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void select(int satirno)
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        String _tarih = tarih.getText().toString() + " " + saat.getText().toString();
        String _teslimtarih = teslimtarih.getText().toString() + " " + teslimsaat.getText().toString();

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowDate = sdfDate.format(now);

        nameValuePairs.add(new BasicNameValuePair("firmano",firmano));
        nameValuePairs.add(new BasicNameValuePair("subeno",subeno));
        nameValuePairs.add(new BasicNameValuePair("nowdate",nowDate));
        nameValuePairs.add(new BasicNameValuePair("tarih",_tarih));
        nameValuePairs.add(new BasicNameValuePair("teslimtarih",_teslimtarih));
        nameValuePairs.add(new BasicNameValuePair("evrakno_seri",evrakno.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("evrakno_sira",evrakno_sira.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("satirno",""+satirno));
        nameValuePairs.add(new BasicNameValuePair("belgeno",belgeno.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("perkod",per_kod));
        nameValuePairs.add(new BasicNameValuePair("carikodu",cari_kod));
        nameValuePairs.add(new BasicNameValuePair("adresno",adresno.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("depono",evrakdepo));

        HashMap hash_ = new HashMap();
        hash_ = list.get(satirno);
        String _stokkodu = hash_.get(INNER_FIRST_COLUMN).toString();
        Float _stokmiktar = Float.parseFloat(hash_.get(INNER_SECOND_COLUMN).toString());
        Float _stokisk1yuzde = Float.parseFloat(hash_.get(INNER_THIRD_COLUMN).toString());
        Float _stokisk2yuzde = Float.parseFloat(hash_.get(INNER_FOURTH_COLUMN).toString());
        Float _stokisk3yuzde = Float.parseFloat(hash_.get(INNER_FIFTH_COLUMN).toString());
        int _stokkdv = Integer.parseInt(hash_.get(INNER_SIXTH_COLUMN).toString());
        Float _stoktutar = Float.parseFloat(hash_.get(INNER_SEVENTH_COLUMN).toString());
        int _stokisk1uyg = Integer.parseInt(hash_.get(INNER_EIGHTH_COLUMN).toString());
        int _stokisk2uyg = Integer.parseInt(hash_.get(INNER_NINTH_COLUMN).toString());
        int _stokisk3uyg = Integer.parseInt(hash_.get(INNER_TENTH_COLUMN).toString());
        int _listesirano = Integer.parseInt(hash_.get(INNER_ELEVENTH_COLUMN).toString());

        Float _stokbirimfiyat = _stoktutar;
        try {
            _stokbirimfiyat = _stoktutar / _stokmiktar;
        }catch (Exception e){
        }
        Float[] iskontovetutar = iskontohesapla.iskontovemasraflar(3, _stoktutar, _stokisk1uyg, _stokisk1yuzde, _stokisk2uyg, _stokisk2yuzde, _stokisk3uyg, _stokisk3yuzde);
        Float isk1_tutar = iskontovetutar[0];
        Float isk2_tutar = iskontovetutar[1];
        Float isk3_tutar = iskontovetutar[2];
        Float kalan = iskontovetutar[3];
        if (isk1_tutar > _stoktutar)
        {
            isk1_tutar = _stoktutar;
            kalan = (float)0;
        }
        if (isk2_tutar > (_stoktutar - isk1_tutar))
        {
            isk2_tutar = _stoktutar - isk1_tutar;
            kalan = (float)0;
        }
        if (isk3_tutar > (_stoktutar - isk1_tutar - isk2_tutar))
        {
            isk3_tutar = _stoktutar - isk1_tutar - isk2_tutar;
            kalan = (float)0;
        }

        Float indirimyuzde =  Float.parseFloat(edit_indirimyuzde.getText().toString().replace(",","."));
        Float stokindirimtutar = (kalan * indirimyuzde / (float)100);
        isk1_tutar = isk1_tutar + stokindirimtutar;

        Float kdvyuzdesi = Float.parseFloat(Constant.kdvtipleri[_stokkdv]);
        Float kdvtutari = (kalan-stokindirimtutar) * kdvyuzdesi;

        nameValuePairs.add(new BasicNameValuePair("stokkodu",_stokkodu));
        nameValuePairs.add(new BasicNameValuePair("birimfiyat",""+_stokbirimfiyat));
        nameValuePairs.add(new BasicNameValuePair("miktar",""+_stokmiktar));
        nameValuePairs.add(new BasicNameValuePair("birimpntr",""+1));
        nameValuePairs.add(new BasicNameValuePair("teslimmiktar",""+0));
        nameValuePairs.add(new BasicNameValuePair("tutar",""+_stoktutar));
        nameValuePairs.add(new BasicNameValuePair("isk1tutar",""+isk1_tutar));
        nameValuePairs.add(new BasicNameValuePair("isk2tutar",""+isk2_tutar));
        nameValuePairs.add(new BasicNameValuePair("isk3tutar",""+isk3_tutar));
        nameValuePairs.add(new BasicNameValuePair("vergipntr",""+_stokkdv));
        nameValuePairs.add(new BasicNameValuePair("vergitutar",""+kdvtutari));
        nameValuePairs.add(new BasicNameValuePair("isk1uyg",""+_stokisk1uyg));
        nameValuePairs.add(new BasicNameValuePair("isk2uyg",""+_stokisk2uyg));
        nameValuePairs.add(new BasicNameValuePair("isk3uyg",""+_stokisk3uyg));
        nameValuePairs.add(new BasicNameValuePair("listesirano",""+_listesirano));

        //tablerows += "<tr><td>"+_stokkodu+"</td><td>"+_stokmiktar+"</td><td>"+_stokbirimfiyat+"</td><td>"+_stoktutar+"</td><td>"+isk1_tutar+"</td><td>"+isk2_tutar+"</td><td>"+isk3_tutar+"</td><td>"+"%"+(kdvyuzdesi*100)+"</td><td>"+kdvtutari+"</td><td>"+_listesirano+"</td></tr>";
        tablerows += "<b>Stok Kodu : </b>"+_stokkodu+"";
        tablerows += "<br><b>Miktar : </b>"+_stokmiktar+"";
        tablerows += "<br><b>Birim Fiyat </b>: "+_stokbirimfiyat+"";
        tablerows += "<br><b>Tutar : </b>"+_stoktutar+"";
        tablerows += "<br><b>İsk1 Tutar : </b>"+isk1_tutar+"";
        tablerows += "<br><b>İsk2 Tutar : </b>"+isk2_tutar+"";
        tablerows += "<br><b>İsk3 Tutar : </b>"+isk3_tutar+"";
        tablerows += "<br><b>KDV Oranı : </b>"+"%"+(kdvyuzdesi*100)+"";
        tablerows += "<br><b>KDV Tutarı : </b>"+kdvtutari+"";
        tablerows += "<br><b>Fiyat No : </b>"+_listesirano+"<br><br>";

        //nameValuePairs.add(new BasicNameValuePair("tarih", nowDate));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://"+Constant.server+"/siparisekle.php");
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
                        /*Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"veyseltoprak23@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "konu");
                        i.putExtra(Intent.EXTRA_TEXT   , "gövde");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getBaseContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });
            }
            else{
                runOnUiThread(new Runnable() {
                    public void run() {
                        kontrol = false;
                    }
                });
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }

    public class Background_confirmation_evrakno extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("id",evrakno.getText().toString()));

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://"+Constant.server+"/siparis_evraknouret.php");
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
                evraknouret=(json_data.getString("name"));
                Log.e("Sonuc",evraknouret);
            }
            catch(Exception e)
            {
                Log.e("Fail 3", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            evrakno_sira.setText(evraknouret);
            sirano_getirildi = true;
        }
    }

    public class Background_confirmation_siparisvarmi extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("id",evrakno.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("sirano",evrakno_sira.getText().toString()));

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://"+Constant.server+"/siparis_varmi.php");
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
                String json_count_ =(json_data.getString("name"));
                count = Integer.parseInt(json_count_);
                Log.e("Sonuc",count+"");
            }
            catch(Exception e)
            {
                Log.e("Fail 3", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(count == 0){
                new Background_confirmation().execute();
            }else{
                Toast.makeText(getApplicationContext(),
                        "Bu evrak numarasına ait bir kayıt zaten var. Evrak numarasını değiştirip tekrar deneyin.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
