package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vtoprak
 */
public class listviewAdapter5 extends BaseAdapter
{
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

    int list_row = 1;

    public ArrayList<HashMap> list;
    Activity activity;

    public listviewAdapter5(Activity activity, ArrayList<HashMap> list, int list_row) {
        super();
        this.activity = activity;
        this.list = list;
        this.list_row = list_row;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
        TextView txtFifth;
        TextView txtInnerFirst;
        TextView txtInnerSecond;
        TextView txtInnerThird;
        TextView txtInnerFourth;
        TextView txtInnerFifth;
        TextView txtInnerSixth;
        TextView txtInnerSeventh;
        TextView txtInnerEighth;
        TextView txtInnerNinth;
        TextView txtInnerTenth;
        TextView txtInnerEleventh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            if(list_row == 1)
            {
                convertView = inflater.inflate(R.layout.listview_row5, null);
            }

            if(position == 0)
            {
                //convertView.setBackgroundResource(R.color.backcolorlight);
            }
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
            holder.txtFifth = (TextView) convertView.findViewById(R.id.FifthText);
            holder.txtInnerFirst = (TextView) convertView.findViewById(R.id.inner_firstText);
            holder.txtInnerSecond = (TextView) convertView.findViewById(R.id.inner_secondText);
            holder.txtInnerThird = (TextView) convertView.findViewById(R.id.inner_thirdText);
            holder.txtInnerFourth = (TextView) convertView.findViewById(R.id.inner_fourthText);
            holder.txtInnerFifth = (TextView) convertView.findViewById(R.id.inner_fifthText);
            holder.txtInnerSixth = (TextView) convertView.findViewById(R.id.inner_sixthText);
            holder.txtInnerSeventh = (TextView) convertView.findViewById(R.id.inner_seventhText);
            holder.txtInnerEighth = (TextView) convertView.findViewById(R.id.inner_eighthText);
            holder.txtInnerNinth = (TextView) convertView.findViewById(R.id.inner_ninthText);
            holder.txtInnerTenth = (TextView) convertView.findViewById(R.id.inner_tenthText);
            holder.txtInnerEleventh = (TextView) convertView.findViewById(R.id.inner_eleventhText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN).toString());
        holder.txtSecond.setText(map.get(SECOND_COLUMN).toString());
        holder.txtThird.setText(map.get(THIRD_COLUMN).toString());
        holder.txtFourth.setText(map.get(FOURTH_COLUMN).toString());
        holder.txtFifth.setText(map.get(FIFTH_COLUMN).toString());
        holder.txtInnerFirst.setText(map.get(INNER_FIRST_COLUMN).toString());
        holder.txtInnerSecond.setText(map.get(INNER_SECOND_COLUMN).toString());
        holder.txtInnerThird.setText(map.get(INNER_THIRD_COLUMN).toString());
        holder.txtInnerFourth.setText(map.get(INNER_FOURTH_COLUMN).toString());
        holder.txtInnerFifth.setText(map.get(INNER_FIFTH_COLUMN).toString());
        holder.txtInnerSixth.setText(map.get(INNER_SIXTH_COLUMN).toString());
        holder.txtInnerSeventh.setText(map.get(INNER_SEVENTH_COLUMN).toString());
        holder.txtInnerEighth.setText(map.get(INNER_EIGHTH_COLUMN).toString());
        holder.txtInnerNinth.setText(map.get(INNER_NINTH_COLUMN).toString());
        holder.txtInnerTenth.setText(map.get(INNER_TENTH_COLUMN).toString());
        holder.txtInnerEleventh.setText(map.get(INNER_ELEVENTH_COLUMN).toString());
        return convertView;
    }

}