package ayssiparis.alacaelektronik.com.ayssipari;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vtoprak on 2.5.2015.
 */
public class Constant {
    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";
    public static final String FOURTH_COLUMN = "Fourth";
    public static final String FIFTH_COLUMN = "Fifth";
    public static final String INNER_FIRST_COLUMN = "innerFirst";
    public static final String INNER_SECOND_COLUMN = "innerSecond";
    public static final String INNER_THIRD_COLUMN = "innerThird";
    public static final String INNER_FOURTH_COLUMN = "innerFourth";
    public static final String INNER_FIFTH_COLUMN = "innerFifth";
    public static final String INNER_SIXTH_COLUMN = "innerSixth";
    public static final String INNER_SEVENTH_COLUMN = "innerSeventh";
    public static final String INNER_EIGHTH_COLUMN = "innerEighth";
    public static final String INNER_NINTH_COLUMN = "innerNinth";
    public static final String INNER_TENTH_COLUMN = "innerTenth";
    public static final String INNER_ELEVENTH_COLUMN = "innerEleventh";
    public static final String server = "10.34.1.254/alcservice";
    //public static final String server = "88.249.172.82/alcservice";
    public static final String kdvtipleri[] = {"0","0","0.01","0.08","0.18","0.26","0"};

    public static final String ong_firmano = "ong_firmano";
    public static final String ong_subeno = "ong_subeno";
    public static final String ong_depolar = "ong_depolar";
    public static final String ong_evrakdepo = "ong_evrakdepo";
    public static final String ong_sip_evrakno_seri = "ong_sip_evrakno_seri";
    public static final String ong_evrakmail_checked = "ong_evrakmail_checked";
    public static final String ong_evrakmail_edit = "ong_evrakmail_edit";

    public static void ayarlariOku(Context context){

        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }
}

