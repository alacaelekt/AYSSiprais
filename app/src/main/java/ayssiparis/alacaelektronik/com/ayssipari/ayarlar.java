package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class ayarlar extends Activity {

    EditText edit_firmano;
    EditText edit_subeno;
    EditText edit_depolar;
    EditText edit_evrakdepo;
    EditText edit_sip_evrakno_seri;
    Switch switch_evrakmail;
    EditText edit_evrakmail;

    public static String c_evrak_depono = null;
    public static String c_depolar = null;

    public static String classname = "ayarlar";
    String per_kod = "";

    final Context context = this;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        edit_firmano = (EditText) findViewById(R.id.edit_firmano_ayarlar);
        edit_subeno = (EditText) findViewById(R.id.edit_subeno_ayarlar);
        edit_depolar = (EditText) findViewById(R.id.edit_depolar_ayarlar);
        edit_evrakdepo = (EditText) findViewById(R.id.edit_evrakdepo_ayarlar);
        edit_sip_evrakno_seri = (EditText) findViewById(R.id.edit_sip_evrakno_seri_ayarlar);
        switch_evrakmail = (Switch) findViewById(R.id.switch_evrakmail_ayarlar);
        edit_evrakmail = (EditText) findViewById(R.id.edit_evrakmail_ayarlar);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        edit_firmano.setText(preferences.getString(Constant.ong_firmano, "0"));
        edit_subeno.setText(preferences.getString(Constant.ong_subeno, "0"));
        edit_depolar.setText(preferences.getString(Constant.ong_depolar, "0"));
        edit_evrakdepo.setText(preferences.getString(Constant.ong_evrakdepo, "0"));
        edit_sip_evrakno_seri.setText(preferences.getString(Constant.ong_sip_evrakno_seri, ""));
        switch_evrakmail.setChecked((Boolean.parseBoolean(preferences.getString(Constant.ong_evrakmail_checked,String.valueOf(switch_evrakmail.isChecked())))));
        edit_evrakmail.setText(preferences.getString(Constant.ong_evrakmail_edit, ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(c_evrak_depono != null) {
            edit_evrakdepo.setText(c_evrak_depono);
            c_evrak_depono = null;
        }
        else if(c_depolar != null) {
            edit_depolar.setText(c_depolar);
            c_depolar = null;
        }
    }

    public void onBackPressed() {
        anaekran.c_ayarlar = "";
        this.finish();
    }

    public void depolarEvent(View v) {
        Intent intent = new Intent(ayarlar.this, depolistele.class);
        intent.putExtra("classname", classname);
        intent.putExtra("anahtar", "2");
        startActivity(intent);
    }

    public void evrakdepoEvent(View v) {
        Intent intent = new Intent(ayarlar.this, depolistele.class);
        intent.putExtra("classname", classname);
        intent.putExtra("anahtar", "1");
        startActivity(intent);
    }

    public void saveEvent(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Ayarlar Kaydedilsin mi?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kaydetme işlemini tamamlamak için devam butonuna tıklayın!")
                .setCancelable(false)
                .setPositiveButton("Devam",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try {
                            editor.putString(Constant.ong_firmano, edit_firmano.getText().toString());
                            editor.putString(Constant.ong_subeno, edit_subeno.getText().toString());
                            editor.putString(Constant.ong_depolar, edit_depolar.getText().toString());
                            editor.putString(Constant.ong_evrakdepo, edit_evrakdepo.getText().toString());
                            editor.putString(Constant.ong_sip_evrakno_seri, edit_sip_evrakno_seri.getText().toString());
                            editor.putString(Constant.ong_evrakmail_checked, String.valueOf(switch_evrakmail.isChecked()));
                            editor.putString(Constant.ong_evrakmail_edit, edit_evrakmail.getText().toString());
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "Ayarlar kaydedildi.", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Ayarlar kaydedilirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayarlar, menu);
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
