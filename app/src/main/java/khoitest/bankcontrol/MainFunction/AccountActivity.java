package khoitest.bankcontrol.MainFunction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import khoitest.bankcontrol.MainActivity;
import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.R;

public class AccountActivity extends AppCompatActivity {


    private static List<BankItem> bankData = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bankData = getIntent().getParcelableArrayListExtra(MainActivity.SECRET_KEYACCOUNT);
        double income = 0;
        double bonus = 0;
        double housing = 0;
        double food = 0;
        double transportation = 0;
        double health = 0;
        double edu = 0;
        double buy = 0;
        double entertainment = 0;
        double others = 0;
        for (BankItem item: bankData)
        {
            switch (item.get_itemDescription()){
                case "Income":
                    income += item.get_itemAmountSpent();
                    break;
                case "ExtraMoney":
                    bonus += item.get_itemAmountSpent();
                    break;
                case "Housing":
                    housing += item.get_itemAmountSpent();
                    break;
                case "Food":
                    food += item.get_itemAmountSpent();
                    break;
                case "Transportation":
                    transportation += item.get_itemAmountSpent();
                    break;
                case "Health":
                    health += item.get_itemAmountSpent();
                    break;
                case "Education":
                    edu += item.get_itemAmountSpent();
                    break;
                case "Buying":
                    buy += item.get_itemAmountSpent();
                    break;
                case "Entertainment":
                    entertainment += item.get_itemAmountSpent();
                    break;
                case "Others":
                    others += item.get_itemAmountSpent();
                    break;
                default:
                    break;
            }
        }
        amounttext(income,R.id.textViewIncomeAmount);
        amounttext(bonus,R.id.textViewBonusAmount);
        amounttext(housing,R.id.textViewHousingAmount);
        amounttext(food,R.id.textViewFoodAmount);
        amounttext(transportation,R.id.textViewTransportationAmount);
        amounttext(health,R.id.textViewHealthAmount);
        amounttext(edu,R.id.textViewEducationAmount);
        amounttext(buy,R.id.textViewBuyingAmount);
        amounttext(entertainment,R.id.textViewEntertainmentAmount);
        amounttext(others,R.id.textViewOthersAmount);
        amounttext(income+bonus
                ,R.id.textViewReceiveAmount);
        amounttext(housing+food+transportation+health+edu+buy+entertainment+others
                ,R.id.textViewSpendAmount);
        amounttext(income+bonus-(housing+food+transportation+health+edu+buy+entertainment+others)
                ,R.id.textViewTotalAmount);

        percentext(income,income+bonus,R.id.textViewIncomePercentage);
        percentext(bonus,income+bonus,R.id.textViewBonusPercentage);
        percentext(housing,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewHousingPercentage);
        percentext(food,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewFoodPercentage);
        percentext(transportation,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewTransportationPercentage);
        percentext(health,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewHealthPercentage);
        percentext(edu,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewEducationPercentage);
        percentext(buy,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewBuyingPercentage);
        percentext(entertainment,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewEntertainmentPercentage);
        percentext(others,housing+food+transportation+health+edu+buy+entertainment+others,R.id.textViewOthersPercentage);
    }

    private void amounttext(double in,int inid){
        TextView into = (TextView) findViewById(inid);
        into.setText("$"+Double.toString(Math.round(in * 100d) / 100d));
    }

    private void percentext(double in,double total,int inid){
        TextView into = (TextView) findViewById(inid);
        into.setText(Double.toString(Math.round((in/total*100) * 100d) / 100d) +"%");
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
}
