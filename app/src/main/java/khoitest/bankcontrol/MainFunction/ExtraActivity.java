package khoitest.bankcontrol.MainFunction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import khoitest.bankcontrol.R;

public class ExtraActivity extends AppCompatActivity {

    EditText interestamount;
    EditText interestrate;
    EditText interesttime;
    TextView interestanswer;
    EditText loanamount;
    EditText loanrate;
    EditText loantime;
    TextView loananswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        interestamount = (EditText) findViewById(R.id.editTextInterestAmount);
        interestrate = (EditText) findViewById(R.id.editTextInterestRate);
        interesttime = (EditText) findViewById(R.id.editTextInterestTime);
        interestanswer = (TextView) findViewById(R.id.textViewInterestAnswer);
        loanamount = (EditText) findViewById(R.id.editTextLoanAmount);
        loanrate = (EditText) findViewById(R.id.editTextLoanRate);
        loantime = (EditText) findViewById(R.id.editTextLoanTime);
        loananswer = (TextView) findViewById(R.id.textViewLoanAnswer);

    }
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

    public void CalculateInterest(View view) {
        CheckAndCalculate(1,interestamount.getText().toString(),interestrate.getText().toString(),
                interesttime.getText().toString());
    }

    public void CalculateLoan(View view) {
        CheckAndCalculate(2,loanamount.getText().toString(),loanrate.getText().toString(),
                loantime.getText().toString());
    }

    private void CheckAndCalculate(int casecheck, String amount, String rate, String time) {
        if (amount.equals("")){
            Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
            return;
        } else if (rate.equals("")){
            Toast.makeText(this, "Please Enter Rate", Toast.LENGTH_SHORT).show();
            return;
        } else if (time.equals("")){
            Toast.makeText(this, "Please Enter Time", Toast.LENGTH_SHORT).show();
            return;
        }

        double addamount = 0;
        boolean isValidAmount;
        try {
            addamount = Double.parseDouble(amount);
            isValidAmount = true;
        } catch (NumberFormatException e) {
            isValidAmount = false;
        }

        if ( !isValidAmount) {
            Toast.makeText(this, "It is not a valid Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double addrate = 0;
        boolean isValidRate;
        try {
            addrate = Double.parseDouble(rate);
            isValidRate = true;
        } catch (NumberFormatException e) {
            isValidRate = false;
        }

        if ( !isValidRate) {
            Toast.makeText(this, "It is not a valid Rate", Toast.LENGTH_SHORT).show();
            return;
        }

        double addtime = 0;
        boolean isValidTime;
        try {
            addtime = Double.parseDouble(time);
            isValidTime = true;
        } catch (NumberFormatException e) {
            isValidTime = false;
        }

        if ( !isValidTime) {
            Toast.makeText(this, "It is not a valid Time", Toast.LENGTH_SHORT).show();
            return;
        }

        double answer = 0;
        if (casecheck == 1)             // interest rate
        {
            answer = addamount * Math.pow(1+addrate,addtime);
            answer = Math.round(answer * 100d) / 100d;
            interestanswer.setText("$" + Double.toString(answer));
        } else
        {
            answer = (addamount * addrate)/(1-Math.pow(1+addrate,addtime*(-1)));
            answer = Math.round(answer * 100d) / 100d;
            loananswer.setText("$" + Double.toString(answer));
        }
    }


}
