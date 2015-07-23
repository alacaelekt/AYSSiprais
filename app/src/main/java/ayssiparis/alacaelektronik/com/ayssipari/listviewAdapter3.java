package ayssiparis.alacaelektronik.com.ayssipari;

import android.app.Activity;
import android.graphics.Color;
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
public class listviewAdapter3 extends BaseAdapter
{
    public static final String FIRST_COLUMN = Constant.FIRST_COLUMN;
    public static final String SECOND_COLUMN = Constant.SECOND_COLUMN;
    public static final String THIRD_COLUMN = Constant.THIRD_COLUMN;
    public static final String FOURTH_COLUMN = Constant.FOURTH_COLUMN;
    public static final String INNER_FIRST_COLUMN = Constant.INNER_FIRST_COLUMN;
    public static final String INNER_SECOND_COLUMN = Constant.INNER_SECOND_COLUMN;
    public static final String INNER_THIRD_COLUMN = Constant.INNER_THIRD_COLUMN;
    public static final String INNER_FOURTH_COLUMN = Constant.INNER_FOURTH_COLUMN;
    public static final String INNER_FIFTH_COLUMN = Constant.INNER_FIFTH_COLUMN;

    int list_row = 1;

    public ArrayList<HashMap> list;
    Activity activity;

    public listviewAdapter3(Activity activity, ArrayList<HashMap> list, int list_row) {
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
        TextView txt_innerFirst;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_row4, null);
            if(position == 0)
            {
                //convertView.setBackgroundResource(R.color.backcolorlight);
            }
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
            holder.txt_innerFirst = (TextView) convertView.findViewById(R.id.inner_firstText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        try {
            holder.txtThird.setTextColor(Color.TRANSPARENT);
            Float parse = Float.parseFloat(map.get(THIRD_COLUMN).toString());
            if(parse <= 0)
            {
                holder.txtFourth.setTextColor(Main.resources.getColor(R.color.listred));
            }
            else {
                holder.txtFourth.setTextColor(Main.resources.getColor(R.color.listgreen));
            }
        }catch (Exception e){
        }
        holder.txtFirst.setText(map.get(FIRST_COLUMN).toString());
        holder.txtSecond.setText(map.get(SECOND_COLUMN).toString());
        holder.txtThird.setText(map.get(THIRD_COLUMN).toString());
        holder.txtFourth.setText(map.get(FOURTH_COLUMN).toString());
        holder.txt_innerFirst.setText(map.get(INNER_FIRST_COLUMN).toString());
        return convertView;
    }

}