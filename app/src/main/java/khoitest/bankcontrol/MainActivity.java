package khoitest.bankcontrol;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khoitest.bankcontrol.MainFunction.AccountActivity;
import khoitest.bankcontrol.MainFunction.AddActivity;
import khoitest.bankcontrol.MainFunction.ChangeActivity;
import khoitest.bankcontrol.MainFunction.ExtraActivity;
import khoitest.bankcontrol.MainFunction.InfoActivity;
import khoitest.bankcontrol.MainFunction.SearchActivity;
import khoitest.bankcontrol.MainFunction.ViewActivity;
import khoitest.bankcontrol.Model.BankItem;
import khoitest.bankcontrol.database.DataSource;
import khoitest.bankcontrol.database.ItemsDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText balanceAmounttop;
    private static List<BankItem> BankData = new ArrayList<>();;
    public static final int SECRET_KEYADD = 2505;

    public static final int SECRET_KEYCHANGE = 1998;
    public static final String SECRET_KEYCHANGESEND = "change_send";
    public static final String SECRET_KEYSEARCH = "search_send";
    public static final String SECRET_KEYVIEW = "view_send";
    public static final String SECRET_KEYACCOUNT = "account_send";

    DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // region begin
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // endregion

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);

        mDataSource = new DataSource(this);
        mDataSource.open();

        if (savedInstanceState != null) {
            BankData = savedInstanceState.getParcelableArrayList(SAVE_KEY);
        } else if (isFirstRun ) {
            // region give some initialize data
            BankData.add(new BankItem("19118440171104", "05/25/2015", "Income", 123, true));
            BankData.add(new BankItem("19118440171105", "05/24/2015", "Housing", 200, false));
            BankData.add(new BankItem("19118440171106", "05/23/2015", "ExtraMoney", 1000, true));
            BankData.add(new BankItem("19118440171107", "02/25/2012", "Food", 50, false));
            BankData.add(new BankItem("19118440171108", "02/25/2012", "Housing", 150, false));
            BankData.add(new BankItem("19118440171101", "11/11/2017", "Income", 250, true));
            BankData.add(new BankItem("19118440131101", "11/11/2017", "Income", 350, true));
            BankData.add(new BankItem("19113440131101", "11/11/2017", "Housing", 30, false));
            // endregion

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit().putBoolean("isfirstrun", false).commit();
        } else {
                BankData = mDataSource.getAllItems();
        }

        mDataSource.seedDatabase(BankData);

        //Initialize component and check for initialization
        balanceAmounttop = (EditText) findViewById(R.id.editTextBalanceMainAmount);
        balanceAmounttop.setEnabled(false);
        calculateAccountBalance();
    }

    // region Menu and Navigation selection and begin function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aboutandcontact) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_balancesum) {
            Intent intent = new Intent(this, AccountActivity.class);
            intent.putParcelableArrayListExtra(SECRET_KEYACCOUNT,(ArrayList<? extends Parcelable>) BankData);
            startActivity(intent);
        } else if (id == R.id.nav_calculate) {
            Intent intent = new Intent(this, ExtraActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            mDataSource.seedDatabase(BankData);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        mDataSource.seedDatabase(BankData);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // endregion

    //region Save Data
    private static final String SAVE_KEY = "save_key";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVE_KEY, (ArrayList<? extends Parcelable>) BankData);
        super.onSaveInstanceState(outState);
    }
    //endregion

    // region Button
    public void AddTransaction(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent,SECRET_KEYADD);
    }

    public void ChangeTransaction(View view) {
        Intent intent = new Intent(this, ChangeActivity.class);
        intent.putParcelableArrayListExtra(SECRET_KEYCHANGESEND,(ArrayList<? extends Parcelable>) BankData);
        startActivityForResult(intent,SECRET_KEYCHANGE);
    }

    public void SearchHistory(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putParcelableArrayListExtra(SECRET_KEYSEARCH,(ArrayList<? extends Parcelable>) BankData);
        startActivity(intent);
    }

    public void ViewHistory(View view) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putParcelableArrayListExtra(SECRET_KEYVIEW,(ArrayList<? extends Parcelable>) BankData);
        startActivity(intent);
    }


    // endregion

    // region manage activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECRET_KEYADD && resultCode == RESULT_OK){
            BankItem bankItem = data.getExtras().getParcelable(AddActivity.SECRET_KEYADDSTRING);
            Log.i("Test",bankItem.toString());
            if (bankItem != null)
            {
                BankData.add(bankItem);
            }
        } else if (requestCode == SECRET_KEYCHANGE && resultCode == RESULT_OK){
            BankData = data.getExtras().getParcelableArrayList(ChangeActivity.SECRET_KEYCHANGESTRING);
        }
        calculateAccountBalance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
        calculateAccountBalance();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataSource.close();
    }

    // endregion

    // region extra function

    public void calculateAccountBalance() {
        double sum = 0;
        for (BankItem item: BankData) {
            if (item.get_itemSign() == true) {
                sum += item.get_itemAmountSpent();
            } else {
                sum -= item.get_itemAmountSpent();
            }
        }

        if ( sum >= 0) {
            balanceAmounttop.setText("$" + Double.toString(sum));
        } else {
            balanceAmounttop.setText("-$" + Double.toString(Math.abs(sum)));
        }
    }

    // endregion

}
