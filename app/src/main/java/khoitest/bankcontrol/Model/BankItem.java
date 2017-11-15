package khoitest.bankcontrol.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import khoitest.bankcontrol.database.ItemsDatabase;

public class BankItem implements Parcelable {
    private String _itemId;
    private String _itemDate;           // Date will be string but will be appear as Date if possible according to design
    private String _itemDescription;
    private double _itemAmountSpent;
    private boolean _itemSign;

    public String get_itemId() {
        return _itemId;
    }

    public void set_itemId(String _itemId) {
        this._itemId = _itemId;
    }

    public String get_itemDate() {
        return _itemDate;
    }

    public void set_itemDate(String _itemDate) {
        this._itemDate = _itemDate;
    }

    public String get_itemDescription() {
        return _itemDescription;
    }

    public void set_itemDescription(String _itemDescription) {
        this._itemDescription = _itemDescription;
    }

    public double get_itemAmountSpent() {
        return _itemAmountSpent;
    }

    public void set_itemAmountSpent(double _itemAmountSpent) {
        this._itemAmountSpent = _itemAmountSpent;
    }

    public boolean get_itemSign() {
        return _itemSign;
    }

    public void set_itemSign(boolean _itemSign) {
        this._itemSign = _itemSign;
    }

    public BankItem(String _itemId, String _itemDate, String _itemDescription, double _itemAmountSpent, boolean _itemSign) {
        this._itemId = _itemId;
        this._itemDate = _itemDate;
        this._itemDescription = _itemDescription;
        this._itemAmountSpent = Math.round(_itemAmountSpent * 100d) / 100d;
        this._itemSign = _itemSign;
    }

    public BankItem() {}

    @Override
    public String toString() {
        return "BankItem{" +
                "_itemId='" + _itemId + '\'' +
                ", _itemDate='" + _itemDate + '\'' +
                ", _itemDescription='" + _itemDescription + '\'' +
                ", _itemAmountSpent=" + _itemAmountSpent +
                ", _itemSign=" + _itemSign +
                '}';
    }


    protected BankItem(Parcel in) {
        _itemId = in.readString();
        _itemDate = in.readString();
        _itemDescription = in.readString();
        _itemAmountSpent = in.readDouble();
        _itemSign = in.readByte() != 0;
    }

    public static final Creator<BankItem> CREATOR = new Creator<BankItem>() {
        @Override
        public BankItem createFromParcel(Parcel in) {
            return new BankItem(in);
        }

        @Override
        public BankItem[] newArray(int size) {
            return new BankItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_itemId);
        dest.writeString(_itemDate);
        dest.writeString(_itemDescription);
        dest.writeDouble(_itemAmountSpent);
        dest.writeByte((byte) (_itemSign ? 1 : 0));
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(5);

        values.put(ItemsDatabase.COLUMN_ID, _itemId);
        values.put(ItemsDatabase.COLUMN_DATE, _itemDate);
        values.put(ItemsDatabase.COLUMN_DESCRIPTION,_itemDescription);
        values.put(ItemsDatabase.COLUMN_AMOUNT,_itemAmountSpent);
        values.put(ItemsDatabase.COLUMN_SIGN,_itemSign == true?"1":"0");

        return values;
    }
}
