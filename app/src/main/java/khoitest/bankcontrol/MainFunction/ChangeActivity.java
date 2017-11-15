package khoitest.bankcontrol.MainFunction;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khoitest.bankcontrol.MainActivity;
import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.R;

public class ChangeActivity extends AppCompatActivity {

    EditText idlook;

    private static List<BankItem> bankData = new ArrayList<>();
    public static final String SECRET_KEYCHANGESTRING = "key_change";

    public static final String SECRET_KEYCHANGESUBSEND = "key_change_sub";
    public static final int SECRET_KEYCHANGESUB = 1906;

    private BankItem itemFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        idlook = (EditText) findViewById(R.id.EditTextChangeIdSearch);
        bankData = getIntent().getParcelableArrayListExtra(MainActivity.SECRET_KEYCHANGESEND);
    }

    public void Delete(View view) {
        BankItem item = findId(idlook.getText().toString());
        if (item == null){
            Toast.makeText(this, "This is not a valid Id", Toast.LENGTH_SHORT).show();
            return;
        } else {
            bankData.remove(item);
            try {
                Intent output = new Intent();
                output.putParcelableArrayListExtra(SECRET_KEYCHANGESTRING,(ArrayList<? extends Parcelable>) bankData);
                setResult(RESULT_OK,output);
                Toast.makeText(this, "Delete Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Cannot insert Data", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void Change(View view) {
        itemFind = findId(idlook.getText().toString());
        if (itemFind == null){
            Toast.makeText(this, "This is not a valid Id", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent(ChangeActivity.this, ChangeActivitySub.class);
            intent.putExtra(SECRET_KEYCHANGESUBSEND,(BankItem) itemFind);
            startActivityForResult(intent,SECRET_KEYCHANGESUB);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECRET_KEYCHANGESUB && resultCode == RESULT_OK){
            BankItem bankItem = data.getExtras().getParcelable(ChangeActivitySub.SECRET_KEYCHANGESUBSTRING);
            Log.i("Test",bankItem.toString());
            if (bankItem != null)
            {
                int itemPosition = bankData.indexOf(itemFind);
                bankData.set(itemPosition, bankItem);
            }
            try {
                Intent output = new Intent();
                output.putParcelableArrayListExtra(SECRET_KEYCHANGESTRING,(ArrayList<? extends Parcelable>) bankData);
                setResult(RESULT_OK,output);
                Toast.makeText(this, "Change Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Cannot Change Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // save data
    private static final String SAVE_KEY = "save_key";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVE_KEY, (ArrayList<? extends Parcelable>) bankData);
        super.onSaveInstanceState(outState);
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
