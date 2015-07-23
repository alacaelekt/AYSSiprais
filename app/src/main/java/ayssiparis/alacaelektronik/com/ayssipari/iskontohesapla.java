package ayssiparis.alacaelektronik.com.ayssipari;

/**
 * Created by vtoprak on 8.7.2015.
 */
public class iskontohesapla {

    public static Float[] iskontovemasraflar(int iskno, Float tutar, int isk1uygulama, Float isk1yuzde, int isk2uygulama, Float isk2yuzde, int isk3uygulama, Float isk3yuzde)
    {
        Float[] result = new Float[4];

        int[] uygulama = { isk1uygulama, isk2uygulama, isk3uygulama };
        Float[] yuzde = { isk1yuzde, isk2yuzde, isk3yuzde };

        Float kalan = tutar;
        //masrafı dahil etmediğimizden dolayı 6 ya kadar yaptık.
        for (int i = 0; i < iskno; i++)
        {
            if(uygulama[i] == 0)
            {
                result[i] = tutar * yuzde[i] / (float)100;
                if (i > 5)
                {
                    kalan = kalan + result[i];
                }
                else
                {
                    kalan = kalan - result[i];
                }
            }
            else if(uygulama[i] == 1)
            {
                result[i] = kalan * yuzde[i] / (float)100;
                if (i > 5)
                {
                    kalan = kalan + result[i];
                }
                else
                {
                    kalan = kalan - result[i];
                }
            }
            else{
                result[i] = (float)0;
            }
            /*else if (uygulama[i] == 2)
            {
                result[i] = yuzde[i];
                if (i > 5)
                {
                    kalan = kalan + result[i];
                }
                else
                {
                    kalan = kalan - result[i];
                }
            }*/
        }
        result[3] = kalan;

        return result;
    }
}
