package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class anaekran extends Activity {

    String per_kod = "";
    String evrak_depo = "0";
    String depolar = "";
    String firmano = "0";
    String subeno = "0";
    String sip_evrakno_seri = "";
    String evrakmail_checked = "false";
    String evrakmail_edit = "";

    public static String c_ayarlar = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anaekran);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String datas = extras.getString("per_kod");
            if (datas != null) {
                per_kod = datas;
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getBaseContext(), "'" + datas + "' kullanıcı koduyla giriş yaptınız.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        ayarlariOku();
        Main.resources = getResources();
    }

    public void ayarlariOku(){
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        firmano = preferences.getString(Constant.ong_firmano, "0");
        subeno = preferences.getString(Constant.ong_subeno, "0");
        depolar = preferences.getString(Constant.ong_depolar, "0");
        evrak_depo = preferences.getString(Constant.ong_evrakdepo, "0");
        sip_evrakno_seri = preferences.getString(Constant.ong_sip_evrakno_seri, "");
        evrakmail_checked = preferences.getString(Constant.ong_evrakmail_checked,"false");
        evrakmail_edit = preferences.getString(Constant.ong_evrakmail_edit, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(c_ayarlar != null) {
            ayarlariOku();
            c_ayarlar = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anaekran, menu);
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

    public void stokEvent(View v) {
        Intent intent = new Intent(anaekran.this, stoklar.class);
        intent.putExtra("depolar", depolar);
        startActivity(intent);
    }

    public void cariEvent(View v) {
        Intent intent = new Intent(anaekran.this, cariler.class);
        startActivity(intent);
    }

    public void ziyaretEvent(View v) {
        Intent intent = new Intent(anaekran.this, ziyaret.class);
        intent.putExtra("per_kod", per_kod);
        intent.putExtra("firmano", firmano);
        intent.putExtra("subeno", subeno);
        intent.putExtra("evrakmail_checked", evrakmail_checked);
        intent.putExtra("evrakmail_edit", evrakmail_edit);
        startActivity(intent);
    }

    public void siparisEvent(View v) {
        Intent intent = new Intent(anaekran.this, siparis.class);
        intent.putExtra("per_kod", per_kod);
        intent.putExtra("carikodu", "");
        intent.putExtra("firmano", firmano);
        intent.putExtra("subeno", subeno);
        intent.putExtra("depolar", depolar);
        intent.putExtra("evrakdepo", evrak_depo);
        intent.putExtra("sip_evrakno_seri", sip_evrakno_seri);
        intent.putExtra("evrakmail_checked", evrakmail_checked);
        intent.putExtra("evrakmail_edit", evrakmail_edit);
        startActivity(intent);
    }

    public void ayarlarEvent(View v) {
        Intent intent = new Intent(anaekran.this, ayarlar.class);
        intent.putExtra("per_kod", per_kod);
        startActivity(intent);
    }

    public void planEvent(View v) {
        Intent intent = new Intent(anaekran.this, plan.class);
        intent.putExtra("per_kod", per_kod);
        intent.putExtra("firmano", firmano);
        intent.putExtra("subeno", subeno);
        startActivity(intent);
    }

}
