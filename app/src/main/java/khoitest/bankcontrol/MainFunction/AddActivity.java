package khoitest.bankcontrol.MainFunction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import khoitest.bankcontrol.MainActivity;
import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.R;

public class AddActivity extends AppCompatActivity {

    private BankItem itemInsert = null;

    TextView addDate;
    Spinner addDes;
    EditText addAmount;
    String getDate = null;
    public static final String SECRET_KEYADDSTRING = "key_add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addDate = (TextView) findViewById(R.id.textViewChooseDate);
        addDes = (Spinner) findViewById(R.id.spinnerAddDescription);
        addAmount = (EditText) findViewById(R.id.editTextAddAmount);

    }

    public void AddItem(View view) {
         // check if it is valid

        // for amount
        String addamountstring = addAmount.getText().toString();
        double addamount = 0;
        boolean isValidAmount;
        try {
            addamount = Double.parseDouble(addamountstring);
            addamount = Math.round(addamount * 100d) / 100d;
            isValidAmount = true;
        } catch (NumberFormatException e) {
            isValidAmount = false;
        }

        // check if valid for Date and Amount  - Description is always chosen, so that we do not have to check
        if (getDate == null){
            Toast.makeText(this, "Please enter a Date", Toast.LENGTH_SHORT).show();
            return;
        } else if (addamountstring.equals("")) {
            Toast.makeText(this, "Please enter an Amount ", Toast.LENGTH_SHORT).show();
            return;
        } else if ( !isValidAmount) {
            Toast.makeText(this, "It is not a valid Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        // for Description and Sign
        String adddescription ="";
        boolean addsign = false;
        switch (addDes.getSelectedItemPosition()){
            case 0:
                adddescription = "Income";
                addsign = true;
                break;
            case 1:
                adddescription = "ExtraMoney";
                addsign = true;
                break;
            case 2:
                adddescription = "Housing";
                addsign = false;
                break;
            case 3:
                adddescription = "Food";
                addsign = false;
                break;
            case 4:
                adddescription = "Transportation";
                addsign = false;
                break;
            case 5:
                adddescription = "Health";
                addsign = false;
                break;
            case 6:
                adddescription = "Education";
                addsign = false;
                break;
            case 7:
                adddescription = "Buying";
                addsign = false;
                break;
            case 8:
                adddescription = "Entertainment";
                addsign = false;
                break;
            case 9:
                adddescription = "Others";
                addsign = false;
                break;
            default:
                break;
        }

        // generate ID
        String temp = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
        String stringcode = Integer.toString(addDes.getSelectedItemPosition() + 1) +
                temp.charAt(10) + temp.charAt(9) + temp.charAt(12)
                + temp.charAt(13) + temp.charAt(15) + temp.charAt(16)
                + temp.charAt(1) + temp.charAt(2) + temp.charAt(3)
                + temp.charAt(4) + temp.charAt(5) + temp.charAt(6)
                + temp.charAt(7);

        try {
            itemInsert = new BankItem(stringcode, getDate, adddescription, addamount, addsign);
            Intent output = new Intent();
            output.putExtra(SECRET_KEYADDSTRING,itemInsert);
            setResult(RESULT_OK,output);
            Toast.makeText(this, "Add Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Cannot insert Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void chooseDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getDate = Integer.toString(dayOfMonth) + "/" + Integer.toString(month+1)
                      + "/" + Integer.toString(year);
                addDate.setText(getDate);
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
}
