package khoitest.bankcontrol.MainFunction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.R;

public class ChangeActivitySub extends AppCompatActivity {
    TextView subdate;
    Spinner subdes;
    EditText subamount;
    private static BankItem bankItem;
    String getDate = null;
    public static final String SECRET_KEYCHANGESUBSTRING = "key_add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sub);

        subdate = (TextView) findViewById(R.id.textViewChangeSubDate);
        subdes = (Spinner) findViewById(R.id.spinnerChangeSubDescription);
        subamount = (EditText) findViewById(R.id.editTextChangeSubAmount);

        bankItem = getIntent().getParcelableExtra(ChangeActivity.SECRET_KEYCHANGESUBSEND);
        subdate.setText(bankItem.get_itemDate());
        getDate = bankItem.get_itemDate();
        switch (bankItem.get_itemDescription())
        {
            case "Income":
                subdes.setSelection(0);
                break;
            case "ExtraMoney":
                subdes.setSelection(1);
                break;
            case "Housing":
                subdes.setSelection(2);
                break;
            case "Food":
                subdes.setSelection(3);
                break;
            case "Transportation":
                subdes.setSelection(4);
                break;
            case "Health":
                subdes.setSelection(5);
                break;
            case "Education":
                subdes.setSelection(6);
                break;
            case "Buying":
                subdes.setSelection(7);
                break;
            case "Entertainment":
                subdes.setSelection(8);
                break;
            case "Others":
                subdes.setSelection(9);
                break;
            default:
                break;
        }
        subamount.setText(Double.toString(bankItem.get_itemAmountSpent()));
    }

    public void chooseDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ChangeActivitySub.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getDate = Integer.toString(dayOfMonth) + "/" + Integer.toString(month+1)
                        + "/" + Integer.toString(year);
                subdate.setText(getDate);
            }
        },year, month, date);
        datePickerDialog.show();
    }

    public void ChangeItemReturn(View view) {
        // check if it is valid

        // for amount
        String addamountstring = subamount.getText().toString();
        double subamount = 0;
        boolean isValidAmount;
        try {
            subamount = Double.parseDouble(addamountstring);
            subamount = Math.round(subamount * 100d) / 100d;
            isValidAmount = true;
        } catch (NumberFormatException e) {
            isValidAmount = false;
        }

        // check if valid for Date and Amount  - Description is always chosen, so that we do not have to check
//        if (getDate == null){
//            Toast.makeText(this, "Please enter a Date", Toast.LENGTH_SHORT).show();
//            return;
//        } else
        if (addamountstring.equals("")) {
            Toast.makeText(this, "Please enter an Amount ", Toast.LENGTH_SHORT).show();
            return;
        } else if ( !isValidAmount) {
            Toast.makeText(this, "It is not a valid Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        // for Description and Sign
        String subdescription ="";
        boolean subsign = false;
        switch (subdes.getSelectedItemPosition()){
            case 0:
                subdescription = "Income";
                subsign = true;
                break;
            case 1:
                subdescription = "ExtraMoney";
                subsign = true;
                break;
            case 2:
                subdescription = "Housing";
                subsign = false;
                break;
            case 3:
                subdescription = "Food";
                subsign = false;
                break;
            case 4:
                subdescription = "Transportation";
                subsign = false;
                break;
            case 5:
                subdescription = "Health";
                subsign = false;
                break;
            case 6:
                subdescription = "Education";
                subsign = false;
                break;
            case 7:
                subdescription = "Buying";
                subsign = false;
                break;
            case 8:
                subdescription = "Entertainment";
                subsign = false;
                break;
            case 9:
                subdescription = "Others";
                subsign = false;
                break;
            default:
                break;
        }

        try {
            bankItem = new BankItem(bankItem.get_itemId(), getDate, subdescription, subamount, subsign);
            Intent output = new Intent();
            output.putExtra(SECRET_KEYCHANGESUBSTRING,bankItem );
            setResult(RESULT_OK,output);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Cannot change Data", Toast.LENGTH_SHORT).show();
        }
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
