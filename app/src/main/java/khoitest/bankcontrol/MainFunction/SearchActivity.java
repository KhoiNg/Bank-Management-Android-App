package khoitest.bankcontrol.MainFunction;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import khoitest.bankcontrol.MainActivity;
import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.R;

public class SearchActivity extends AppCompatActivity {
    TextView foundthings;
    CheckBox searchidcheck;
    CheckBox searchinfocheck;
    EditText searchid;
    TextView searchdate;
    Spinner searchdes;
    EditText searchamount;
    String getDate = null;
    private static List<BankItem> bankData = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchidcheck = (CheckBox) findViewById(R.id.checkBoxSearchId);
        searchinfocheck = (CheckBox) findViewById(R.id.checkBoxSearchInfo);
        searchid = (EditText) findViewById(R.id.editTextSearchId);
        searchdate = (TextView) findViewById(R.id.textViewSearchDate);
        searchdes = (Spinner) findViewById(R.id.spinnerSearchDescription);
        searchamount = (EditText) findViewById(R.id.editTextSearchAmount);

        searchid.setEnabled(false);
        searchdate.setEnabled(false);
        searchdes.setEnabled(false);
        searchamount.setEnabled(false);

        foundthings = (TextView) findViewById(R.id.textViewFoundThings);
        foundthings.setMovementMethod(new ScrollingMovementMethod());
        bankData = getIntent().getParcelableArrayListExtra(MainActivity.SECRET_KEYSEARCH);
    }

    public void chooseDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getDate = Integer.toString(dayOfMonth) + "/" + Integer.toString(month+1)
                        + "/" + Integer.toString(year);
                searchdate.setText(getDate);
            }
        },year, month, date);
        datePickerDialog.show();
    }



    @Override
    public void onBackPressed() {
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

    public void check(View view) {
        boolean checked = ((CheckBox) view).isChecked();
//        if (!checked) return;

        switch (view.getId()) {
            case R.id.checkBoxSearchId:
                if (checked) {
                    searchid.setEnabled(true);
                    searchinfocheck.setChecked(false);
                    searchdate.setEnabled(false);
                    searchdes.setEnabled(false);
                    searchamount.setEnabled(false);
                } else {
                    searchid.setEnabled(false);
                }
                break;
            case R.id.checkBoxSearchInfo:
                if (checked) {
                    searchdate.setEnabled(true);
                    searchdes.setEnabled(true);
                    searchamount.setEnabled(true);
                    searchidcheck.setChecked(false);
                    searchid.setEnabled(false);
                } else {
                    searchdate.setEnabled(false);
                    searchdes.setEnabled(false);
                    searchamount.setEnabled(false);
                }
                break;
            default:
                break;
        }
    }

    public void answer(View view) {
        if (searchidcheck.isChecked()){
            if (searchid.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter an ID", Toast.LENGTH_SHORT).show();
                return;
            }
            BankItem item = findId(searchid.getText().toString());
            if (item == null)
            {
                foundthings.setText("Search not Found");
                return;
            }
            else
            {
                foundthings.setText("Item ID = " + item.get_itemId() + "\n" +
                 "Item Date = " + item.get_itemDate() + "\n"
                + "Item Description = " + item.get_itemDescription() + "\n"
                + "Item Amount = $" + item.get_itemAmountSpent() + "\n"
                + "Item Sign = " + item.get_itemSign());
                return;
            }
        }
        else if (searchinfocheck.isChecked()){
            List<BankItem> temp = bankData;
            List<BankItem> temp2 = new ArrayList<>();

            if (getDate != null){
                for (BankItem item : temp)
                    if (item.get_itemDate().toString().equals(getDate)) {
                        temp2.add(item);
                    }
                temp = temp2;
                temp2 = new ArrayList<>();
            }


            String amountstring = searchamount.getText().toString();
            double amountsearch = 0;
            boolean isValidAmount;
            try {
                amountsearch = Double.parseDouble(amountstring);
                amountsearch = Math.round(amountsearch * 100d) / 100d;
                isValidAmount = true;
            } catch (NumberFormatException e) {
                isValidAmount = false;
            }

            if ( !isValidAmount && !amountstring.equals("")) {
                Toast.makeText(this, "It is not a valid Amount", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!amountstring.equals(""))
            {
                for (BankItem item : temp)
                    if (item.get_itemAmountSpent() == amountsearch ) temp2.add(item);
                temp = temp2;
                temp2 = new ArrayList<>();
            }


            String searchdescription ="";
            switch (searchdes.getSelectedItemPosition()){
                case 0:
                    searchdescription = "Income";
                    break;
                case 1:
                    searchdescription = "ExtraMoney";
                    break;
                case 2:
                    searchdescription = "Housing";
                    break;
                case 3:
                    searchdescription = "Food";
                    break;
                case 4:
                    searchdescription = "Transportation";
                    break;
                case 5:
                    searchdescription = "Health";
                    break;
                case 6:
                    searchdescription = "Education";
                    break;
                case 7:
                    searchdescription = "Buying";
                    break;
                case 8:
                    searchdescription = "Entertainment";
                    break;
                case 9:
                    searchdescription = "Others";
                    break;
                default:
                    break;
            }

            for (BankItem item : temp)
                if (item.get_itemDescription().equals(searchdescription) ) temp2.add(item);

            temp = temp2;
            if (temp.size() == 0){
                foundthings.setText("Search not found ");
                return;
            } else {
                String answer = "Found ID = \n";
                for (BankItem item : temp)
                    answer = answer + item.get_itemId() + "\n";
                foundthings.setText(answer);
            }

        }
    }

    BankItem findId(String id){
        for (BankItem item:
                bankData) {
            if (item.get_itemId().equals(id)){
                return item;
            }
        }
        return null;
    }

}
