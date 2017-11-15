package khoitest.bankcontrol.Model;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import junit.framework.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import khoitest.bankcontrol.R;

public class DataViewList extends ArrayAdapter<BankItem> {

    List<BankItem> bankItems;
    LayoutInflater inflater;

    public DataViewList(Context context, List<BankItem> objects) {
        super(context, R.layout.list_item, objects);

        bankItems = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
              convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        BankItem item = bankItems.get(position);

        TextView datelist = (TextView) convertView.findViewById(R.id.textViewListDate);
        SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MMM dd, yyyy");
        Date date = null;
        try {
            date = format1.parse(item.get_itemDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datelist.setText(format2.format(date));

        TextView amountlist = (TextView) convertView.findViewById(R.id.textViewListAmount);
        if (item.get_itemSign() == false) {
            amountlist.setTextColor(Color.RED);
            amountlist.setText("-$" + Double.toString(item.get_itemAmountSpent()));
        }
        else {
            amountlist.setTextColor(Color.GREEN);
            amountlist.setText("$" + Double.toString(item.get_itemAmountSpent()));
        }

        TextView descriptionlist = (TextView) convertView.findViewById(R.id.textViewListDescription);
        descriptionlist.setText(item.get_itemDescription());
        switch (item.get_itemDescription()){
            case "Income":
                descriptionlist.setText("Income/Wage");
                break;
            case "ExtraMoney":
                descriptionlist.setText("Bonus Money");
                break;
            case "Food":
                descriptionlist.setText("Food/Grogeries");
                break;
            case "Health":
                descriptionlist.setText("Heath/Insurance");
                break;
            case "Buying":
                descriptionlist.setText("Buying things");
                break;
            default:
                descriptionlist.setText(item.get_itemDescription());
                break;
        }
        return convertView;
    }
}
