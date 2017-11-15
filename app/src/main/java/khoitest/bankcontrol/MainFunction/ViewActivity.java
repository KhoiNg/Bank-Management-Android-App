package khoitest.bankcontrol.MainFunction;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import khoitest.bankcontrol.MainActivity;
import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.Model.DataViewList;
import khoitest.bankcontrol.R;

public class ViewActivity extends AppCompatActivity {

    private static List<BankItem> bankData = new ArrayList<>();
    ListView listView;
    DataViewList dataViewList;
    Spinner date;
    Spinner des;
    Spinner amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        bankData = getIntent().getParcelableArrayListExtra(MainActivity.SECRET_KEYVIEW);
        listView = (ListView) findViewById(R.id.listitemview);
        dataViewList = new DataViewList(this,bankData);
        listView.setAdapter(dataViewList);

        date = (Spinner) findViewById(R.id.spinnerViewDate);
        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterdata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterdata();
            }
        });
        des = (Spinner) findViewById(R.id.spinnerViewDescription);
        des.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterdata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterdata();
            }
        });
        amount = (Spinner) findViewById(R.id.spinnerViewAmount);
        amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterdata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterdata();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dataViewList.clear();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void filterdata() {
        List<BankItem> temp = bankData;
        switch (date.getSelectedItemPosition()){
            case 1:
                Collections.sort(temp, new Comparator<BankItem>() {
                    @Override
                    public int compare(BankItem r1, BankItem r2) {
                        return r1.get_itemDate().compareTo(r2.get_itemDate());
                    }
                });
                break;
            case 2:
                Collections.sort(temp, new Comparator<BankItem>() {
                    @Override
                    public int compare(BankItem r1, BankItem r2) {
                        return r2.get_itemDate().compareTo(r1.get_itemDate());
                    }
                });
                break;
            default:
                break;
        }

        switch (des.getSelectedItemPosition()){
            case 1:
                temp = removedes(temp,"Income");
                break;
            case 2:
                temp = removedes(temp,"ExtraMoney");
                break;
            case 3:
                temp = removedes(temp,"Housing");
                break;
            case 4:
                temp = removedes(temp,"Food");
                break;
            case 5:
                temp = removedes(temp,"Transportation");
                break;
            case 6:
                temp = removedes(temp,"Health");
                break;
            case 7:
                temp = removedes(temp,"Education");
                break;
            case 8:
                temp = removedes(temp,"Buying");
                break;
            case 9:
                temp = removedes(temp,"Entertainment");
                break;
            case 10:
                temp = removedes(temp,"Others");
                break;
            default:
                break;
        }

        switch (amount.getSelectedItemPosition())
        {
            case 1:
                temp = removeamount(temp,1);
                break;
            case 2:
                temp = removeamount(temp,2);
                break;
            case 3:
                temp = removeamount(temp,3);
                break;
            case 4:
                temp = removeamount(temp,4);
                break;
            default:
                break;
        }

        dataViewList = new DataViewList(this,temp);
        listView.setAdapter(dataViewList);
    }

    private List<BankItem> removedes(List<BankItem> bankItems,String in){
        List<BankItem> list = new ArrayList<>();
        for (BankItem item: bankItems) {
            if (item.get_itemDescription().equals(in)) list.add(item);
        }
        return list;
    }
    private List<BankItem> removeamount(List<BankItem> bankItems,int c){
        List<BankItem> list = new ArrayList<>();
        for (BankItem item: bankItems) {
            double temp = item.get_itemAmountSpent();
            switch (c){
                case 1:
                    if ( temp < 100) list.add(item);
                    break;
                case 2:
                    if ( temp >= 100 && temp < 500) list.add(item);
                    break;
                case 3:
                    if ( temp >= 500 && temp < 1000) list.add(item);
                    break;
                case 4:
                    if ( temp >= 1000) list.add(item);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

}
