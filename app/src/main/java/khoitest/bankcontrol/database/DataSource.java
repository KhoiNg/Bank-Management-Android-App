package khoitest.bankcontrol.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khoitest.bankcontrol.Model.BankItem;

public class DataSource {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public BankItem createItem(BankItem item) {
        ContentValues values = item.toValues();
        mDatabase.insert(ItemsDatabase.TABLE_ITEMS, null, values);
        return item;
    }

//    public long getDataItemsCount() {
//        return DatabaseUtils.queryNumEntries(mDatabase, ItemsDatabase.TABLE_ITEMS);
//    }

    public void seedDatabase(List<BankItem> dataItemList) {
        mDatabase.delete(ItemsDatabase.TABLE_ITEMS, null, null);
            for (BankItem item :
                    dataItemList) {
                try {
                    createItem(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
    }

    public List<BankItem> getAllItems() {
        List<BankItem> dataItems = new ArrayList<>();
            Cursor cursor = null;
            cursor = mDatabase.query(ItemsDatabase.TABLE_ITEMS, ItemsDatabase.ALL_COLUMNS,
                    null, null, null, null, ItemsDatabase.COLUMN_ID);
            while (cursor.moveToNext()) {
                BankItem item = new BankItem();
                item.set_itemId(cursor.getString(
                        cursor.getColumnIndex(ItemsDatabase.COLUMN_ID)));
                item.set_itemDate(cursor.getString(
                        cursor.getColumnIndex(ItemsDatabase.COLUMN_DATE)));
                item.set_itemDescription(cursor.getString(
                        cursor.getColumnIndex(ItemsDatabase.COLUMN_DESCRIPTION)));
                item.set_itemAmountSpent(cursor.getDouble(
                        cursor.getColumnIndex(ItemsDatabase.COLUMN_AMOUNT)));
                item.set_itemSign(cursor.getInt(
                        cursor.getColumnIndex(ItemsDatabase.COLUMN_SIGN)) == 1);
                dataItems.add(item);
            }
            cursor.close();
            return dataItems;
    }
}
